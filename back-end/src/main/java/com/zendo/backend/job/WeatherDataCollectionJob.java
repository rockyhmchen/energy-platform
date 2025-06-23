package com.zendo.backend.job;

import com.zendo.backend.event.DataCollectedEvent;
import com.zendo.backend.event.IncomingDataType;
import com.zendo.backend.service.WeatherApiService;
import com.zendo.backend.utility.DateTimeHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The WeatherDataCollectionJob class is responsible for periodically collecting weather data
 * from an external API and storing it. It is designed to be executed as a scheduled job.
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherDataCollectionJob {

    private final WeatherApiService weatherApiService;
    private final DateTimeHelper dateTimeHelper;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the weather data collection job. This method is scheduled to run at
     * intervals defined by the 'jobs.schedule.weatherData' cron expression.
     *
     * It fetches the current weather data, stores it, and publishes a DataCollectedEvent
     * upon successful completion. Any exceptions encountered during execution are
     * logged, but do not prevent the job from completing.
     */
    @Scheduled(cron = "${jobs.schedule.weatherData}")
    @Async
    public void execute() {
        var collectingAt = dateTimeHelper.now();
        try {
            logger.info("Start fetching the current weather data...");

            weatherApiService.fetchAndStore(collectingAt);
            eventPublisher.publishEvent(new DataCollectedEvent(this, IncomingDataType.WEATHER, collectingAt));

            logger.info("Current weather data fetched");
        }
        catch (Exception ex) {
            logger.error("Failed to fetch the current weather data. Collecting at: {}", collectingAt);
        }
    }
}
