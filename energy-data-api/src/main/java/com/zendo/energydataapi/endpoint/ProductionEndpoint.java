package com.zendo.energydataapi.endpoint;

import com.zendo.energydataapi.model.Production;
import com.zendo.energydataapi.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The ProductionEndpoint class provides a REST endpoint for retrieving the latest production data.
 * <p>
 * This class handles HTTP requests to the "/production" endpoint and returns a Production object
 * representing the latest production data.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/production")
public class ProductionEndpoint {

    private final ProductionService productionService;

    /**
     * Handles GET requests to the "/latest" endpoint and returns the latest Production object.
     * <p>
     * The returned Production object represents the latest production data, including the total
     * production and breakdown by source (solar and wind).
     *
     * @return a ResponseEntity containing the latest Production object with a 200 OK status code
     */
    @GetMapping("/latest")
    public ResponseEntity<Production> latest() {
        return ResponseEntity.ok(productionService.generate());
    }
}
