package com.zendo.backend.utility

import com.zendo.backend.utility.impl.DateTimeHelperImpl
import spock.lang.Specification

import java.time.OffsetDateTime
import java.time.ZoneOffset

class ListLast24HoursTimesPerHourSpec extends Specification {

    DateTimeHelper target

    def setup() {
        target = new DateTimeHelperImpl()
    }

    def "Should return a list of the last 24 hours times per hour"() {
        given:
        def now = OffsetDateTime.of(2025, 1, 1, 15, 12, 23, 9, ZoneOffset.UTC)

        when:
        def result = target.listLast24HoursTimesPerHour(now)

        then:
        result.size() == 24
        result.get(23) == OffsetDateTime.of(2025, 1, 1, 15, 0, 0, 0, ZoneOffset.UTC)
        result.get(22) == OffsetDateTime.of(2025, 1, 1, 14, 0, 0, 0, ZoneOffset.UTC)
        result.get(21) == OffsetDateTime.of(2025, 1, 1, 13, 0, 0, 0, ZoneOffset.UTC)
        result.get(20) == OffsetDateTime.of(2025, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC)
        result.get(19) == OffsetDateTime.of(2025, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC)
        result.get(18) == OffsetDateTime.of(2025, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC)
        result.get(17) == OffsetDateTime.of(2025, 1, 1, 9, 0, 0, 0, ZoneOffset.UTC)
        result.get(16) == OffsetDateTime.of(2025, 1, 1, 8, 0, 0, 0, ZoneOffset.UTC)
        result.get(15) == OffsetDateTime.of(2025, 1, 1, 7, 0, 0, 0, ZoneOffset.UTC)
        result.get(14) == OffsetDateTime.of(2025, 1, 1, 6, 0, 0, 0, ZoneOffset.UTC)
        result.get(13) == OffsetDateTime.of(2025, 1, 1, 5, 0, 0, 0, ZoneOffset.UTC)
        result.get(12) == OffsetDateTime.of(2025, 1, 1, 4, 0, 0, 0, ZoneOffset.UTC)
        result.get(11) == OffsetDateTime.of(2025, 1, 1, 3, 0, 0, 0, ZoneOffset.UTC)
        result.get(10) == OffsetDateTime.of(2025, 1, 1, 2, 0, 0, 0, ZoneOffset.UTC)
        result.get(9) == OffsetDateTime.of(2025, 1, 1, 1, 0, 0, 0, ZoneOffset.UTC)
        result.get(8) == OffsetDateTime.of(2025, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
        result.get(7) == OffsetDateTime.of(2024, 12, 31, 23, 0, 0, 0, ZoneOffset.UTC)
        result.get(6) == OffsetDateTime.of(2024, 12, 31, 22, 0, 0, 0, ZoneOffset.UTC)
        result.get(5) == OffsetDateTime.of(2024, 12, 31, 21, 0, 0, 0, ZoneOffset.UTC)
        result.get(4) == OffsetDateTime.of(2024, 12, 31, 20, 0, 0, 0, ZoneOffset.UTC)
        result.get(3) == OffsetDateTime.of(2024, 12, 31, 19, 0, 0, 0, ZoneOffset.UTC)
        result.get(2) == OffsetDateTime.of(2024, 12, 31, 18, 0, 0, 0, ZoneOffset.UTC)
        result.get(1) == OffsetDateTime.of(2024, 12, 31, 17, 0, 0, 0, ZoneOffset.UTC)
        result.get(0) == OffsetDateTime.of(2024, 12, 31, 16, 0, 0, 0, ZoneOffset.UTC)
    }
}
