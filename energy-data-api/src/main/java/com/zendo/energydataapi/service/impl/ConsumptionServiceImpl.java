package com.zendo.energydataapi.service.impl;

import java.time.ZoneOffset;

import com.zendo.energydataapi.model.Consumption;
import com.zendo.energydataapi.service.ConsumptionService;
import com.zendo.energydataapi.service.KwhGenerator;
import com.zendo.energydataapi.utility.DateTimeHelper;
import com.zendo.energydataapi.utility.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The ConsumptionServiceImpl class provides an implementation of the {@link ConsumptionService} interface.
 * It generates a {@link Consumption} object based on the current date and time, and the total consumption
 * calculated by the {@link KwhGenerator}.
 *
 * @see ConsumptionService
 * @see Consumption
 */
@RequiredArgsConstructor
@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    private final DateTimeHelper dateTimeHelper;
    private final KwhGenerator kwhGenerator;

    /**
     * Generates a {@link Consumption} object based on the current date and time, and the total consumption
     * calculated by the {@link KwhGenerator}.
     *
     * @return a {@link Consumption} object representing the current consumption
     */
    @Override
    public Consumption generate() {
        var now = dateTimeHelper.now();
        var generatingTime = TimeUtil.getEveryXHourTime(now, 1);

        return Consumption.builder()
                .timestamp(generatingTime.atOffset(ZoneOffset.UTC))
                .totalConsumptionKwh(kwhGenerator.totalConsumption(generatingTime))
                .build();
    }
}
