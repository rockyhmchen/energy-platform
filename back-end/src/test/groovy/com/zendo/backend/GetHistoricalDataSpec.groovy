package com.zendo.backend

import com.zendo.backend.entity.EnergyDetail
import com.zendo.backend.repository.EnergyDetailRepository
import com.zendo.backend.utility.DateTimeHelper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired

import java.time.OffsetDateTime
import java.time.ZoneOffset

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetHistoricalDataSpec extends ComponentTestSpec {

    @Autowired
    EnergyDetailRepository energyDetailRepository

    @SpringBean
    DateTimeHelper dateTimeHelperMock = Mock()

    def cleanup() {
        energyDetailRepository.deleteAll()
    }

    def "Should return historical data"() {
        given:
        def now = OffsetDateTime.of(2025, 1, 2, 23, 0, 0, 0, ZoneOffset.UTC)
        dateTimeHelperMock.now() >> now

        energyDetailRepository.save(EnergyDetail.builder()
                .timestamp(OffsetDateTime.of(2025, 1, 2, 12, 8, 10, 0, ZoneOffset.UTC))
                .consumptionKwh(4.2)
                .productionKwh(6.8)
                .solarProductionKwh(5.1)
                .solarIrradianceWm2(25.0)
                .uvIndex(1.0)
                .netBalanceKwh(3.6)
                .cloudCoverDecimal(1.38)
                .temperatureCelsius(24.7)
                .windSpeedInSecond(12.2)
                .solarIrradianceVsSolarProduction(2.4)
                .temperatureVsEnergyConsumption(-1.2)
                .build())
        energyDetailRepository.save(EnergyDetail.builder()
                .timestamp(OffsetDateTime.of(2025, 1, 2, 22, 18, 20, 0, ZoneOffset.UTC))
                .consumptionKwh(3.2)
                .productionKwh(5.8)
                .solarProductionKwh(4.1)
                .solarIrradianceWm2(250.0)
                .uvIndex(10.0)
                .netBalanceKwh(2.6)
                .cloudCoverDecimal(0.38)
                .temperatureCelsius(23.7)
                .windSpeedInSecond(11.2)
                .solarIrradianceVsSolarProduction(-2.4)
                .temperatureVsEnergyConsumption(1.2)
                .build())

        when:
        def response = webTester.perform(get("/historical-data")
                .contentType(APPLICATION_JSON))

        then:
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('[0].timestamp').value("2025-01-02T12:08:10.000+0000"))
                .andExpect(jsonPath('[0].consumptionKwh').value(4.2))
                .andExpect(jsonPath('[0].productionKwh').value(6.8))
                .andExpect(jsonPath('[0].netBalanceKwh').value(3.6))
                .andExpect(jsonPath('[0].temperatureCelsius').value(24.7))
                .andExpect(jsonPath('[0].cloudCoverDecimal').value(1.38))
                .andExpect(jsonPath('[0].windSpeedInSecond').value(12.2))
                .andExpect(jsonPath('[0].solarIrradianceWm2').value(25.0))
                .andExpect(jsonPath('[0].solarIrradianceVsSolarProduction').value(2.4))
                .andExpect(jsonPath('[0].temperatureVsEnergyConsumption').value(-1.2))
                .andExpect(jsonPath('[1].timestamp').value("2025-01-02T22:18:20.000+0000"))
                .andExpect(jsonPath('[1].consumptionKwh').value(3.2))
                .andExpect(jsonPath('[1].productionKwh').value(5.8))
                .andExpect(jsonPath('[1].netBalanceKwh').value(2.6))
                .andExpect(jsonPath('[1].temperatureCelsius').value(23.7))
                .andExpect(jsonPath('[1].cloudCoverDecimal').value(0.38))
                .andExpect(jsonPath('[1].windSpeedInSecond').value(11.2))
                .andExpect(jsonPath('[1].solarIrradianceWm2').value(250.0))
                .andExpect(jsonPath('[1].solarIrradianceVsSolarProduction').value(-2.4))
                .andExpect(jsonPath('[1].temperatureVsEnergyConsumption').value(1.2))
    }
}
