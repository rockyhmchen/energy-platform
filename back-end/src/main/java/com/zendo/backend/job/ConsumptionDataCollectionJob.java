package com.zendo.backend.job;

import com.zendo.backend.event.DataCollectedEvent;
import com.zendo.backend.event.IncomingDataType;
import com.zendo.backend.service.ConsumptionApiService;
import com.zendo.backend.utility.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The ConsumptionDataCollectionJob class is responsible for periodically fetching consumption data
 * from an external API and publishing a {@link DataCollectedEvent} upon successful data collection.
 *
 * This job is scheduled to run at a configurable interval defined by the
 * {@code jobs.schedule.consumptionData} property.
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumptionDataCollectionJob {

    private final ConsumptionApiService consumptionApiService;
    private final DateTimeHelper dateTimeHelper;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the consumption data collection job.
     *
     * This method is scheduled to run asynchronously at a configurable interval.
     * It fetches consumption data using the {@link ConsumptionApiService} and publishes a
     * {@link DataCollectedEvent} upon successful data collection.
     */
    @Scheduled(cron = "${jobs.schedule.consumptionData}")
    @Async
    public void execute() {
        var collectingAt = dateTimeHelper.now();
        try {
            logger.info("Start fetching consumption data...");

            consumptionApiService.fetchAndStore();
            eventPublisher.publishEvent(new DataCollectedEvent(this, IncomingDataType.CONSUMPTION, collectingAt));

            logger.info("Consumption data fetched");
        }
        catch (Exception ex) {
            logger.error("Failed to fetch consumption data. Collecting at: {}", collectingAt);
        }
    }
}
