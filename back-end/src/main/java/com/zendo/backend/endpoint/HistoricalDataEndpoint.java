package com.zendo.backend.endpoint;

import java.util.List;

import com.zendo.backend.endpoint.payload.HistoricalData;
import com.zendo.backend.entity.EnergyDetail;
import com.zendo.backend.service.EnergyDetailService;
import com.zendo.backend.utility.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The HistoricalDataEndpoint class provides a REST endpoint for retrieving historical energy data.
 * It exposes a single GET endpoint that returns the last 24 hours of historical data.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/historical-data")
public class HistoricalDataEndpoint {

    private final EnergyDetailService energyDetailService;
    private final DateTimeHelper dateTimeHelper;

    /**
     * Returns the last 24 hours of historical energy data.
     *
     * @return a ResponseEntity containing a list of HistoricalData objects representing the last 24 hours of energy data
     */
    @GetMapping("/")
    public ResponseEntity<List<HistoricalData>> get() {
        var now = dateTimeHelper.now();
        var energyDetails = energyDetailService.getLast24HoursData(now);
        var historyData = energyDetails.stream()
                .map(this::toHistoricalData)
                .toList();

        return ResponseEntity.ok(historyData);
    }

    /**
     * Converts an EnergyDetail object to a HistoricalData object.
     *
     * @param energyDetail the EnergyDetail object to convert
     * @return a HistoricalData object representing the energy detail
     */
    private HistoricalData toHistoricalData(final EnergyDetail energyDetail) {
        return HistoricalData.builder()
                .timestamp(energyDetail.getTimestamp())
                .consumptionKwh(energyDetail.getConsumptionKwh())
                .productionKwh(energyDetail.getConsumptionKwh())
                .netBalanceKwh(energyDetail.getNetBalanceKwh())
                .solarProduction(energyDetail.getSolarProductionKwh())
                .solarIrradianceWm2(energyDetail.getSolarIrradianceWm2())
                .temperatureCelsius(energyDetail.getTemperatureCelsius())
                .build();
    }
}
