package com.zendo.backend.apiclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Weather data response payload
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private Number lat;
    private Number lon;
    private String timezone;
    private Number timezone_offset;

    @JsonProperty("current")
    private Detail detail;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Detail {
        @JsonProperty("dt")
        private Number currentTimeInUnix;
        private Number sunrise;
        private Number sunset;

        @JsonProperty("temp")
        private Number temperature;

        @JsonProperty("feels_like")
        private Number feelsLike;
        private Number pressure;
        private Number humidity;

        @JsonProperty("dew_point")
        private Number dewPoint;
        private Number uvi;
        private Number clouds;
        private Number visibility;

        @JsonProperty("wind_speed")
        private Number windSpeed;

        @JsonProperty("wind_deg")
        private Number windDeg;
    }
}
