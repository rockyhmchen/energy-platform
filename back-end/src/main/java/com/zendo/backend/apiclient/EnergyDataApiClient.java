package com.zendo.backend.apiclient;

import com.zendo.backend.apiclient.model.ConsumptionResponse;
import com.zendo.backend.apiclient.model.ProductionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 * The EnergyDataApiClient interface provides methods for retrieving energy data from an API.
 * <p>
 * This interface defines the contract for interacting with the energy data API, allowing clients to
 * fetch the latest consumption and production data.
 */
public interface EnergyDataApiClient {

    /**
     * Retrieves the latest energy consumption data from the API.
     *
     * @return a ResponseEntity containing the latest ConsumptionResponse data, or an HTTP status code
     *         indicating the result of the request.
     */
    @GetExchange("/consumption/latest")
    ResponseEntity<ConsumptionResponse> getLatestConsumption(@RequestParam(name = "lat", defaultValue = "51.5085") double latitude,
                                                             @RequestParam(name = "lon", defaultValue = "-0.1257") double longitude);

    /**
     * Retrieves the latest energy production data from the API.
     *
     * @return a ResponseEntity containing the latest ProductionResponse data, or an HTTP status code
     *         indicating the result of the request.
     */
    @GetExchange("/production/latest")
    ResponseEntity<ProductionResponse> getLatestProduction(@RequestParam(name = "lat", defaultValue = "51.5085") double latitude,
                                                           @RequestParam(name = "lon", defaultValue = "-0.1257") double longitude);
}
