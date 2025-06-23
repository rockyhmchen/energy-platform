package com.zendo.backend.apiclient;

import com.zendo.backend.apiclient.model.WeatherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 * The WeatherApiClient interface provides a method for retrieving the current weather data from the OpenWeatherMap API.
 * <p>
 * This interface is annotated with Spring's WebFlux annotations to enable RESTful API calls.
 */
public interface WeatherApiClient {

    /**
     * Retrieves the current weather data for a given location.
     * <p>
     * The response is deserialized into a {@link WeatherResponse} object, which contains the current weather details.
     *
     * @param latitude  the latitude of the location (default: 51.5085)
     * @param longitude the longitude of the location (default: -0.1257)
     * @param exclude   the parts of the response to exclude (default: "hourly,daily,minutely,alerts")
     * @param units     the units to use for the response (default: "metric")
     * @param appKey    the API key to use for authentication
     * @return a {@link ResponseEntity} containing the {@link WeatherResponse} object
     */
    @GetExchange("/data/3.0/onecall")
    ResponseEntity<WeatherResponse> getCurrentWeather(@RequestParam(name = "lat", defaultValue = "51.5085") double latitude,
                                                      @RequestParam(name = "lon", defaultValue = "-0.1257") double longitude,
                                                      @RequestParam(name = "exclude", defaultValue = "hourly,daily,minutely,"
                                                              + "alerts") String exclude,
                                                      @RequestParam(name = "units", defaultValue = "metric") String units,
                                                      @RequestParam(name = "appid") String appKey);
}
