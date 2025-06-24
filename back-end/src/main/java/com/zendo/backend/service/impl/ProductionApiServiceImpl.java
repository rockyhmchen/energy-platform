package com.zendo.backend.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.zendo.backend.apiclient.EnergyDataApiClient;
import com.zendo.backend.entity.EnergySourceType;
import com.zendo.backend.entity.Production;
import com.zendo.backend.entity.ProductionDetail;
import com.zendo.backend.repository.ProductionRepository;
import com.zendo.backend.service.ProductionApiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.zendo.backend.constant.GeographicCoordinate.LONDON_LATITUDE;
import static com.zendo.backend.constant.GeographicCoordinate.LONDON_LONGITUDE;

/**
 * Implementation of the {@link ProductionApiService} interface.
 * This class is responsible for fetching the latest production data from an external API and storing it in the database.
 * <p>
 * It uses the {@link EnergyDataApiClient} to retrieve the latest production data and the {@link ProductionRepository} to manage production records in the database.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductionApiServiceImpl implements ProductionApiService {

    private final EnergyDataApiClient energyDataApiClient;
    private final ProductionRepository productionRepository;

    /**
     * Fetches the latest production data from the API and stores it in the database if it is not already present.
     * <p>
     * This method is transactional, ensuring that the data is stored atomically.
     * <p>
     * If the API request is unsuccessful or the response data is invalid, an {@link IllegalStateException} is thrown.
     * <p>
     * If the production data is already present in the database, the existing record is returned without modification.
     *
     * @return the stored production record
     */
    @Override
    @Transactional
    public Production fetchAndStore() {
        var response = energyDataApiClient.getLatestProduction(LONDON_LATITUDE, LONDON_LONGITUDE);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException(String.format("Failed to fetch the latest production data. Http status code: %d",
                                                          response.getStatusCode().value()));
        }

        var data = response.getBody();
        if (Objects.isNull(data.getTimestamp())) {
            throw new IllegalStateException(String.format("Response data doesn't contain the timestamp. Response data: %s",
                                                          data));
        }

        var found = productionRepository.findByTimestamp(data.getTimestamp());
        if (found.isPresent()) {
            logger.debug("Do nothing for the incoming production data since it has been already collected. Timestamp: {}",
                         data.getTimestamp());
            return found.get();
        }

        var entity = Production.builder()
                .timestamp(data.getTimestamp())
                .totalProductionKwh(BigDecimal.valueOf(data.getTotalProductionKwh().doubleValue()))
                .build();

        var solar = ProductionDetail.builder()
                .sourceType(EnergySourceType.SOLAR)
                .parent(entity)
                .productionKwh(BigDecimal.valueOf(data.getDetail().getSolarProductionKwh().doubleValue()))
                .build();

        var wind = ProductionDetail.builder()
                .sourceType(EnergySourceType.WIND)
                .parent(entity)
                .productionKwh(BigDecimal.valueOf(data.getDetail().getWindProductionKwh().doubleValue()))
                .build();

        entity.setDetails(List.of(solar, wind));
        var newData = productionRepository.save(entity);
        logger.debug("New production data collected. Timestamp: {}", data.getTimestamp());

        return newData;
    }
}
