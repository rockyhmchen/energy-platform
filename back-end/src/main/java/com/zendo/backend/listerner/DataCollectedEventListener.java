package com.zendo.backend.listerner;

import com.zendo.backend.event.DataCollectedEvent;
import com.zendo.backend.service.EnergyDetailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for {@link DataCollectedEvent} that updates the last 24 hours of energy detail data when data is collected.
 * <p>
 * This listener is triggered when a {@link DataCollectedEvent} is published, and it updates the energy detail data
 * for the last 24 hours based on the collecting timestamp provided in the event.
 * </p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataCollectedEventListener {

    private final EnergyDetailService energyDetailService;

    /**
     * Handles the {@link DataCollectedEvent} by updating the last 24 hours of energy detail data.
     * <p>
     * This method is annotated with {@link EventListener} to indicate that it should be called when a
     * {@link DataCollectedEvent} is published. It is also annotated with {@link Transactional} to ensure that
     * the update operation is executed within a transactional context.
     * </p>
     *
     * @param event the {@link DataCollectedEvent} that triggered this handler
     */
    @EventListener
    @Transactional
    public void handleUserRegistration(final DataCollectedEvent event) {
        logger.info("Receiving a data collected event. Type: {}. Collecting at: {}",
                    event.getIncomingDataType(),
                    event.getCollectingAt());

        var updatedPeriods = energyDetailService.updateLast24HoursData(event.getCollectingAt());
        logger.info("The last 24 hours energy detail updated. From: {} to {}",
                    updatedPeriods.getFirst().getTimestamp(),
                    updatedPeriods.getLast().getTimestamp());
    }
}
