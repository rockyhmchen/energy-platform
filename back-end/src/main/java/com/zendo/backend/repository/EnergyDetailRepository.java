package com.zendo.backend.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.zendo.backend.entity.EnergyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for managing {@link EnergyDetail} entities.
 * Provides methods for retrieving energy detail records based on specific criteria.
 */
public interface EnergyDetailRepository
        extends JpaRepository<EnergyDetail, UUID>, JpaSpecificationExecutor<EnergyDetail> {

    /**
     * Retrieves an {@link EnergyDetail} record by its timestamp.
     *
     * @param timestamp the timestamp to search for
     * @return an {@link Optional} containing the energy detail record if found, or an empty {@link Optional} otherwise
     */
    Optional<EnergyDetail> findByTimestamp(OffsetDateTime timestamp);

    /**
     * Retrieves a list of {@link EnergyDetail} records within a specified timestamp range.
     *
     * @param start the start of the timestamp range (inclusive)
     * @param end the end of the timestamp range (inclusive)
     * @return a list of energy detail records within the specified timestamp range
     */
    List<EnergyDetail> findByTimestampBetween(OffsetDateTime start, OffsetDateTime end);
}
