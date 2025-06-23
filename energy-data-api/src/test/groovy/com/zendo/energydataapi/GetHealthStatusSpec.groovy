package com.zendo.energydataapi


import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetHealthStatusSpec extends ComponentTestSpec {

    def "Should return the status code 200 when calling the health endpoint"() {
        when:
        def response = webTester.perform(get("/health")
                .contentType(APPLICATION_JSON))

        then:
        response.andExpect(status().isOk())
    }
}
