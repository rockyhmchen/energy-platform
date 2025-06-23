package com.zendo.backend.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.zendo.backend.entity.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The ConsumptionRepository interface provides data access operations for {@link Consumption} entities.
 * It extends {@link JpaRepository} and {@link JpaSpecificationExecutor} to leverage Spring Data JPA's
 * features for CRUD operations and dynamic query execution.
 */
public interface ConsumptionRepository
        extends JpaRepository<Consumption, UUID>, JpaSpecificationExecutor<Consumption> {

    /**
     * Retrieves a {@link Consumption} record by its timestamp.
     *
     * @param timestamp the timestamp of the consumption record
     * @return an {@link Optional} containing the consumption record if found, or an empty {@link Optional} otherwise
     */
    Optional<Consumption> findByTimestamp(OffsetDateTime timestamp);

    /**
     * Retrieves a list of {@link Consumption} records within a specified timestamp range.
     *
     * @param start the start of the timestamp range (inclusive)
     * @param end   the end of the timestamp range (inclusive)
     * @return a list of consumption records within the specified timestamp range
     */
    List<Consumption> findByTimestampBetween(OffsetDateTime start, OffsetDateTime end);
}
