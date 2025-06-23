package com.zendo.backend.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.zendo.backend.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for managing {@link Production} entities.
 * Provides methods for CRUD operations and querying productions based on specific criteria.
 */
public interface ProductionRepository
        extends JpaRepository<Production, UUID>, JpaSpecificationExecutor<Production> {

    /**
     * Retrieves a production record by its timestamp.
     *
     * @param timestamp the timestamp of the production record to retrieve
     * @return an {@link Optional} containing the production record if found, or an empty {@link Optional} otherwise
     */
    Optional<Production> findByTimestamp(OffsetDateTime timestamp);

    /**
     * Retrieves a list of production records within a specified timestamp range.
     *
     * @param start the start of the timestamp range (inclusive)
     * @param end the end of the timestamp range (inclusive)
     * @return a list of production records within the specified timestamp range
     */
    List<Production> findByTimestampBetween(OffsetDateTime start, OffsetDateTime end);
}
