package com.zendo.backend.service.impl;

import java.math.BigDecimal;
import java.util.Objects;

import com.zendo.backend.apiclient.EnergyDataApiClient;
import com.zendo.backend.entity.Consumption;
import com.zendo.backend.repository.ConsumptionRepository;
import com.zendo.backend.service.ConsumptionApiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zendo.backend.constant.GeographicCoordinate.LONDON_LATITUDE;
import static com.zendo.backend.constant.GeographicCoordinate.LONDON_LONGITUDE;

/**
 * The ConsumptionApiServiceImpl class provides an implementation of the {@link ConsumptionApiService}
 * interface, responsible for fetching the latest energy consumption data from an external API and
 * storing it in the database.
 * <p>
 * This class uses the {@link EnergyDataApiClient} to retrieve the latest consumption data and the
 * {@link ConsumptionRepository} to interact with the database.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumptionApiServiceImpl implements ConsumptionApiService {

    private final EnergyDataApiClient energyDataApiClient;
    private final ConsumptionRepository consumptionRepository;

    /**
     * Fetches the latest energy consumption data from the external API and stores it in the database.
     * <p>
     * If the data is already present in the database (i.e., a record with the same timestamp exists),
     * this method returns the existing record without storing a new one.
     *
     * @return the stored or existing {@link Consumption} record
     * @throws IllegalStateException if the API request fails or the response data is invalid
     */
    @Override
    @Transactional
    public Consumption fetchAndStore() {
        var response = energyDataApiClient.getLatestConsumption(LONDON_LATITUDE, LONDON_LONGITUDE);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException(String.format("Failed to fetch the latest consumption data. Http status code: %d",
                                                          response.getStatusCode().value()));
        }

        var data = response.getBody();
        if (Objects.isNull(data.getTimestamp())) {
            throw new IllegalStateException(String.format("Response data doesn't contain the timestamp. Response data: %s",
                                                          data));
        }

        var found = consumptionRepository.findByTimestamp(data.getTimestamp());
        if (found.isPresent()) {
            logger.debug("Do nothing for the incoming consumption data since it has been already collected. Timestamp: {}",
                         data.getTimestamp());
            return found.get();
        }

        var entity = Consumption.builder()
                .timestamp(data.getTimestamp())
                .totalConsumptionKwh(BigDecimal.valueOf(data.getTotalConsumptionKwh().doubleValue()))
                .build();
        var newData = consumptionRepository.save(entity);
        logger.debug("New consumption data collected. Timestamp: {}", data.getTimestamp());

        return newData;
    }
}
