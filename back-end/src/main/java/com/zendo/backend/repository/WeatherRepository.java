package com.zendo.backend.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.zendo.backend.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * The WeatherRepository interface provides data access operations for {@link Weather} entities.
 * <p>
 * It extends {@link JpaRepository} and {@link JpaSpecificationExecutor} to leverage Spring Data JPA's
 * functionality for CRUD operations and complex queries.
 *
 * @see Weather
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 */
public interface WeatherRepository
        extends JpaRepository<Weather, UUID>, JpaSpecificationExecutor<Weather> {

    /**
     * Retrieves a {@link Weather} record by its timestamp.
     *
     * @param timestamp the timestamp of the weather record
     * @return an {@link Optional} containing the weather record if found, or an empty {@link Optional} otherwise
     */
    Optional<Weather> findByTimestamp(OffsetDateTime timestamp);

    /**
     * Retrieves a list of {@link Weather} records within a specified timestamp range.
     *
     * @param start the start of the timestamp range (inclusive)
     * @param end   the end of the timestamp range (inclusive)
     * @return a list of weather records within the specified timestamp range
     */
    List<Weather> findByTimestampBetween(OffsetDateTime start, OffsetDateTime end);
}
