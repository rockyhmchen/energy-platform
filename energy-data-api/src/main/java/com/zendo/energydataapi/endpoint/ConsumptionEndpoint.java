package com.zendo.energydataapi.endpoint;

import com.zendo.energydataapi.model.Consumption;
import com.zendo.energydataapi.service.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides a REST endpoint for retrieving the latest consumption record.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/consumption")
public class ConsumptionEndpoint {

    private final ConsumptionService consumptionService;

    /**
     * Retrieves the latest consumption record.
     *
     * @return a ResponseEntity containing the latest Consumption record, or an HTTP status indicating an error.
     */
    @GetMapping("/latest")
    public ResponseEntity<Consumption> latest() {
        return ResponseEntity.ok(consumptionService.generate());
    }
}
