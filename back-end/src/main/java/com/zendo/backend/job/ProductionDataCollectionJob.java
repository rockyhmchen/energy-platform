package com.zendo.backend.job;

import com.zendo.backend.event.DataCollectedEvent;
import com.zendo.backend.event.IncomingDataType;
import com.zendo.backend.service.ProductionApiService;
import com.zendo.backend.utility.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The ProductionDataCollectionJob class is responsible for periodically fetching production data
 * from an external API and publishing an event upon successful data collection.
 *
 * This job is scheduled to run at a configurable interval defined by the
 * {@code jobs.schedule.productionData} property.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ProductionDataCollectionJob {

    private final ProductionApiService productionApiService;
    private final DateTimeHelper dateTimeHelper;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the production data collection job asynchronously.
     *
     * This method is scheduled to run at a configurable interval and performs the following steps:
     * 1. Retrieves the current date and time.
     * 2. Fetches production data from the external API using the {@link ProductionApiService}.
     * 3. Publishes a {@link DataCollectedEvent} upon successful data collection.
     *
     * If an exception occurs during data collection, it is logged and the event is not published.
     */
    @Scheduled(cron = "${jobs.schedule.productionData}")
    @Async
    public void execute() {
        var collectingAt = dateTimeHelper.now();
        try {
            logger.info("Start fetching production data...");

            productionApiService.fetchAndStore();
            eventPublisher.publishEvent(new DataCollectedEvent(this, IncomingDataType.PRODUCTION, collectingAt));

            logger.info("Production data fetched");
        }
        catch (Exception ex) {
            logger.error("Failed to fetch production data. Collecting at: {}", collectingAt);
        }
    }
}
