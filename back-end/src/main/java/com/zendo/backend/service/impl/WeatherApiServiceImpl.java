package com.zendo.backend.service.impl;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.zendo.backend.apiclient.WeatherApiClient;
import com.zendo.backend.entity.Weather;
import com.zendo.backend.repository.WeatherRepository;
import com.zendo.backend.service.WeatherApiService;
import com.zendo.backend.utility.BigDecimalUtility;
import com.zendo.backend.utility.SolarIrradianceCalculator;
import com.zendo.backend.utility.TypeConvertorUtility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.zendo.backend.constant.GeographicCoordinate.LONDON_LATITUDE;
import static com.zendo.backend.constant.GeographicCoordinate.LONDON_LONGITUDE;

/**
 * The WeatherApiServiceImpl class provides an implementation of the {@link WeatherApiService} interface.
 * <p>
 * It encapsulates the business logic for fetching and storing weather data from the OpenWeatherMap API.
 * <p>
 * This class is annotated with Spring's {@link Service} annotation to enable autodetection and registration as a Spring bean.
 *
 * @see WeatherApiService
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherApiServiceImpl implements WeatherApiService {

    /**
     * The parts of the response to exclude from the OpenWeatherMap API.
     */
    private static final String EXCLUDE = "hourly,daily,minutely,alerts";

    /**
     * The units to use for the response from the OpenWeatherMap API.
     */
    private static final String UNITS = "metric";

    private final WeatherApiClient weatherApiClient;
    private final WeatherRepository weatherRepository;

    @Value("${thirdPartyApi.weatherApi.apiKey}")
    private String apiKey;

    /**
     * Fetches the current weather data for the specified timestamp and stores it in the database if it doesn't already exist.
     *
     * @param collectingAt the timestamp for which to fetch the weather data
     * @return the stored weather data
     * @throws IllegalArgumentException if the input timestamp is null
     */
    @Override
    @Transactional
    public Weather fetchAndStore(final OffsetDateTime collectingAt) {
        if (Objects.isNull(collectingAt)) {
            throw new IllegalArgumentException("Input param 'collectingAt' must not be null");
        }

        var found = weatherRepository.findByTimestamp(collectingAt);
        if (found.isPresent()) {
            logger.debug("Do nothing for the incoming weather data since it has been already collected. Timestamp: {}",
                         collectingAt);
            return found.get();
        }

        var entity = getCurrentWeather(collectingAt);
        var newData = weatherRepository.save(entity);
        logger.debug("New weather data collected. Timestamp: {}", collectingAt);

        return newData;
    }

    /**
     * Retrieves the current weather data for the specified timestamp.
     *
     * @param now the timestamp for which to retrieve the weather data
     * @return the current weather data
     * @throws IllegalArgumentException if the input timestamp is null
     * @throws IllegalStateException    if the API call fails or the response is invalid
     */
    @Override
    public Weather getCurrentWeather(final OffsetDateTime now) {
        if (Objects.isNull(now)) {
            throw new IllegalArgumentException("Input param 'now' must not be null");
        }

        var response = weatherApiClient.getCurrentWeather(LONDON_LATITUDE, LONDON_LONGITUDE, EXCLUDE, UNITS, apiKey);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException(String.format("Failed to fetch the current weather data. Http status code: %d",
                                                          response.getStatusCode().value()));
        }

        var data = response.getBody();
        if (Objects.isNull(data.getDetail())) {
            throw new IllegalStateException(
                    String.format("The incoming weather data doesn't contain the weather details. Weather data: %s", data));
        }

        var entity = Weather.builder()
                .timestamp(now)
                .temperatureCelsius(BigDecimalUtility.numberToBigDecimal(data.getDetail().getTemperature()))
                .cloudCoverDecimal(BigDecimalUtility.numberToBigDecimal(data.getDetail().getClouds()))
                .windSpeedInSecond(BigDecimalUtility.numberToBigDecimal(data.getDetail().getWindSpeed()))
                .uvIndex(BigDecimalUtility.numberToBigDecimal(data.getDetail().getUvi()))
                .atmosphericPressureHpa(BigDecimalUtility.numberToBigDecimal(data.getDetail().getPressure()))
                .averageVisibility(BigDecimalUtility.numberToBigDecimal(data.getDetail().getVisibility()))
                .sunriseTime(TypeConvertorUtility.epochToOffsetDateTime(data.getDetail().getSunrise()))
                .sunsetTime(TypeConvertorUtility.epochToOffsetDateTime(data.getDetail().getSunset()))
                .build();
        entity.setSolarIrradianceWm2(SolarIrradianceCalculator.estimate(entity.getUvIndex()));

        return entity;
    }
}
