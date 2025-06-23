package com.zendo.backend


import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetEnergySummarySpec extends ComponentTestSpec {

    def "Should return energy summary data"() {
        given:

        when:
        def response = webTester.perform(get("/energy-summary")
                .contentType(APPLICATION_JSON))

        then:
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.totalProductionKwh').value(5.8))
                .andExpect(jsonPath('$.totalConsumptionKwh').value(4.8))
                .andExpect(jsonPath('$.netBalanceKwh').value(1.0))
                .andExpect(jsonPath('$.currentWeather.temperatureCelsius').value(24.62))
                .andExpect(jsonPath('$.currentWeather.cloudCoverDecimal').value(11))
                .andExpect(jsonPath('$.currentWeather.windSpeedInSecond').value(6.69))
                .andExpect(jsonPath('$.currentWeather.solarIrradianceWm2').value(6.69))
                .andExpect(jsonPath('$.correlationMetric.solarIrradianceVsSolarProduction').value(38))
                .andExpect(jsonPath('$.correlationMetric.temperatureVsEnergyConsumption').value(38))
    }
}
