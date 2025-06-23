package com.zendo.energydataapi

import com.zendo.energydataapi.service.KwhGenerator
import com.zendo.energydataapi.utility.DateTimeHelper
import org.spockframework.spring.SpringBean

import java.time.LocalDateTime

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetProductionSpec extends ComponentTestSpec {

    @SpringBean
    DateTimeHelper dateTimeHelperMock = Mock()

    @SpringBean
    KwhGenerator kwhGeneratorMock = Mock()

    def "Should return the last 5 minutes production data"() {
        given:
        def now = LocalDateTime.of(2025, 1, 1, 0, minute, 0)
        dateTimeHelperMock.now() >> now

        kwhGeneratorMock.solarProduction(_) >> 4.2
        kwhGeneratorMock.windProduction(_) >> 1.6

        when:
        def response = webTester.perform(get("/production/latest")
                .contentType(APPLICATION_JSON))

        then:
        def expectedTimestamp = "2025-01-01T00:$expectedMinute:00.000+0000" as String
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.timestamp').value(expectedTimestamp))
                .andExpect(jsonPath('$.totalProductionKwh').value(5.8))
                .andExpect(jsonPath('$.detail.solarProductionKwh').value(4.2))
                .andExpect(jsonPath('$.detail.windProductionKwh').value(1.6))

        where:
        minute | expectedMinute
        1      | "00"
        5      | "05"
        47     | "45"
        59     | "55"
        0      | "00"
    }
}
