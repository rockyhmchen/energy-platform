package com.zendo.backend


import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetHistoricalDataSpec extends ComponentTestSpec {

    def "Should return energy summary data"() {
        given:

        when:
        def response = webTester.perform(get("/historical-data")
                .contentType(APPLICATION_JSON))

        then:
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('[0].timestamp').value("2025-01-01T00:00:00.000+0000"))
                .andExpect(jsonPath('[0].productionKwh').value(5.8))
                .andExpect(jsonPath('[0].consumptionKwh').value(4.8))
                .andExpect(jsonPath('[0].netBalanceKwh').value(1.0))
                .andExpect(jsonPath('[0].weather.temperatureCelsius').value(24.62))
                .andExpect(jsonPath('[0].weather.cloudCoverDecimal').value(11))
                .andExpect(jsonPath('[0].weather.windSpeedInSecond').value(6.69))
                .andExpect(jsonPath('[0].weather.solarIrradianceWm2').value(6.69))
                .andExpect(jsonPath('[0].correlationMetric.solarIrradianceVsSolarProduction').value(38))
                .andExpect(jsonPath('[0].correlationMetric.temperatureVsEnergyConsumption').value(38))
    }
}
