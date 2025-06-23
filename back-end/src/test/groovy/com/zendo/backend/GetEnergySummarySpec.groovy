package com.zendo.backend

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.zendo.backend.entity.EnergyDetail
import com.zendo.backend.repository.EnergyDetailRepository
import org.springframework.beans.factory.annotation.Autowired

import java.time.OffsetDateTime

import static com.github.tomakehurst.wiremock.client.WireMock.okJson
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetEnergySummarySpec extends ComponentTestSpec {

    WireMockServer mockWeatherApi;
    WireMockServer mockEnergyDataApi;

    def setup() {
        this.mockWeatherApi = new WireMockServer(30030)
        this.mockEnergyDataApi = new WireMockServer(30031)
        mockWeatherApi.start()
        mockEnergyDataApi.start()
    }

    def cleanup() {
        mockWeatherApi.stop()
        mockEnergyDataApi.stop()
    }

    @Autowired
    EnergyDetailRepository energyDetailRepository;

    def "Should return energy summary data"() {
        given:
        energyDetailRepository.save(EnergyDetail.builder()
                .timestamp(OffsetDateTime.now())
                .consumptionKwh(4.2)
                .productionKwh(6.8)
                .solarProductionKwh(5.1)
                .solarIrradianceWm2(25.0)
                .unIndex(1.0)
                .netBalanceKwh(3.6)
                .cloudCoverDecimal(1.38)
                .temperatureCelsius(24.7)
                .windSpeedInSecond(12.2)
                .solarIrradianceVsSolarProduction(2.4)
                .temperatureVsEnergyConsumption(-1.2)
                .build())
        energyDetailRepository.save(EnergyDetail.builder()
                .timestamp(OffsetDateTime.now())
                .consumptionKwh(3.2)
                .productionKwh(5.8)
                .solarProductionKwh(4.1)
                .solarIrradianceWm2(250.0)
                .unIndex(10.0)
                .netBalanceKwh(2.6)
                .cloudCoverDecimal(0.38)
                .temperatureCelsius(23.7)
                .windSpeedInSecond(11.2)
                .solarIrradianceVsSolarProduction(-2.4)
                .temperatureVsEnergyConsumption(1.2)
                .build())

        and:
        mockWeatherApi.stubFor(WireMock.get(urlPathEqualTo("/data/3.0/onecall"))
                .willReturn(okJson("""{
    "lat": 51.5085,
    "lon": -0.1257,
    "timezone": "Europe/London",
    "timezone_offset": 3600,
    "current": {
        "dt": 1750589431,
        "sunrise": 1750563800,
        "sunset": 1750623697,
        "temp": 24.62,
        "feels_like": 24.47,
        "pressure": 1013,
        "humidity": 51,
        "dew_point": 13.82,
        "uvi": 7,
        "clouds": 11,
        "visibility": 10000,
        "wind_speed": 6.69,
        "wind_deg": 250,
        "weather": [
            {
                "id": 801,
                "main": "Clouds",
                "description": "few clouds",
                "icon": "02d"
            }
        ]
    }
}""")))

        when:
        def response = webTester.perform(get("/energy-summary")
                .contentType(APPLICATION_JSON))

        then:
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.totalProductionKwh').value(12.6))
                .andExpect(jsonPath('$.totalConsumptionKwh').value(7.4))
                .andExpect(jsonPath('$.netBalanceKwh').value(5.2))
                .andExpect(jsonPath('$.weather.temperatureCelsius').value(24.62))
                .andExpect(jsonPath('$.weather.cloudCoverDecimal').value(11))
                .andExpect(jsonPath('$.weather.windSpeedInSecond').value(6.69))
                .andExpect(jsonPath('$.weather.solarIrradianceWm2').value(175.0))
                .andExpect(jsonPath('$.correlation.solarIrradianceVsSolarProduction').value(-1))
                .andExpect(jsonPath('$.correlation.temperatureVsEnergyConsumption').value(1))
    }
}
