package com.zendo.backend.service;

import java.time.OffsetDateTime;

import com.zendo.backend.entity.Weather;

public interface WeatherApiService {

    Weather fetchAndStore(OffsetDateTime collectingAt);

    Weather getCurrentWeather(OffsetDateTime now);
}
