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

class GetConsumptionSpec extends ComponentTestSpec {

    @SpringBean
    DateTimeHelper dateTimeHelperMock = Mock()

    @SpringBean
    KwhGenerator kwhGeneratorMock = Mock()

    def "Should return the last 1 hour consumption data"() {
        given:
        def now = LocalDateTime.of(2025, 1, 1, hour, 0, 0)
        dateTimeHelperMock.now() >> now

        kwhGeneratorMock.totalConsumption(_) >> 3.6

        when:
        def response = webTester.perform(get("/consumption/latest")
                .contentType(APPLICATION_JSON))

        then:
        def expectedTimestamp = "2025-01-01T$expectedHour:00:00.000+0000" as String
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.timestamp').value(expectedTimestamp))
                .andExpect(jsonPath('$.totalConsumptionKwh').value(3.6))

        where:
        hour | expectedHour
        1      | "01"
        5      | "05"
        9      | "09"
        23     | "23"
        0      | "00"
    }
}
