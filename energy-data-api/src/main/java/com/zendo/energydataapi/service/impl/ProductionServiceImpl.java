package com.zendo.energydataapi.service.impl;

import java.math.BigDecimal;
import java.time.ZoneOffset;

import com.zendo.energydataapi.model.Production;
import com.zendo.energydataapi.service.KwhGenerator;
import com.zendo.energydataapi.service.ProductionService;
import com.zendo.energydataapi.utility.DateTimeHelper;
import com.zendo.energydataapi.utility.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The ProductionServiceImpl class provides an implementation of the ProductionService interface.
 * It generates a new Production object based on the current date and time, and the production data
 * retrieved from the KwhGenerator.
 */
@RequiredArgsConstructor
@Service
public class ProductionServiceImpl implements ProductionService {

    private final DateTimeHelper dateTimeHelper;
    private final KwhGenerator kwhGenerator;

    /**
     * Generates a new Production object based on the current date and time, and the production data.
     * The timestamp of the Production object is set to the current date and time rounded down to the nearest 5 minutes,
     * and the production data is retrieved from the KwhGenerator for that timestamp.
     *
     * @return a newly generated Production object
     */
    @Override
    public Production generate() {
        var now = dateTimeHelper.now();
        var generatingTime = TimeUtil.getEveryXMinuteTime(now, 5);
        var solarProductionKwh = BigDecimal.valueOf(kwhGenerator.solarProduction(generatingTime));
        var windProductionKwh = BigDecimal.valueOf(kwhGenerator.windProduction(generatingTime));
        var totalProductionKwh = solarProductionKwh.add(windProductionKwh);

        return Production.builder()
                .timestamp(generatingTime.atOffset(ZoneOffset.UTC))
                .detail(Production.Detail.builder()
                                .solarProductionKwh(solarProductionKwh)
                                .windProductionKwh(windProductionKwh)
                                .build())
                .totalProductionKwh(totalProductionKwh)
                .build();
    }
}
