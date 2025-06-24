# Back-end API

This is the backend component.

## Get energy summery

Use the following API endpoint to get energy summery data.

Endpoint:
```shell
GET /energy-summary
```

Response:
```json
{
    "totalProductionKwh": 3.8,
    "totalConsumptionKwh": 2.4,
    "netBalanceKwh": 1.2,
    "weather": {
        "temperatureCelsius": 23.7,
        "cloudCoverDecimal": 11,
        "windSpeedInSecond": 25,
        "solarIrradianceWm2": 6.4
    },
    "correlation": {
        "solarIrradianceVsSolarProduction": -2.8,
        "temperatureVsEnergyConsumption": 3.2
    }
}
```


## Get historical data

Use the following API endpoint to get the 24 hours historical data

Endpoint:
```shell
GET /historical-data
```

Response:
```json
[
    {
        "timestamp": "2025-01-01T00:00:00.000+0000",
        "productionKwh": 3.8,
        "consumptionKwh": 2.4,
        "netBalanceKwh": 1.2,
        "solarProduction": 2.1,
        "temperatureCelsius": 23.7,
        "solarIrradianceWm2": 6.4
    },
    {
        "timestamp": "2025-01-01T01:00:00.000+0000",
        "productionKwh": 1.8,
        "consumptionKwh": 3.4,
        "netBalanceKwh": 5.2,
        "solarProduction": 6.1,
        "temperatureCelsius": 24.1,
        "solarIrradianceWm2": 6.5
    }
]
```

## Get health of the service

Use the following API endpoint to check whether the service is up and running.

Endpoint:
```shell
GET /health
```

Response:
```json
{
    "status": "UP"
}
```

<!-- GETTING STARTED -->

## Getting Started

This section describes how to set up the project locally and to get a local copy up and running.

### Prerequisites

* JDK v21
* Docker

### Running the application locally

* Test
  ```sh
  ./gradlew clean test
  ```

* Run
  ```sh
  ./gradlew bootRun
  ```

* Build a docker image
  ```shell
  ./gradlew clean test build unpack
  docker build -t zendoenergy.com/back-end:0.0.1 .
  ```

* Run the docker image
  ```shell
  docker run --rm -it -p 8080:8080 \
  -e WEATHER_API_KEY='${The API key to access the weather API}' \
  zendoenergy.com/back-end:0.0.1
  ```

### Environment variables

- `ENERGY_DATA_API_BASE_URL`: The API base URL of the energy data API.  Default value: `http://localhost:8888`
- `ENERGY_DATA_API_KEY`: The API key to access the API endpoints of the energy data API.  Default value: `test-api-key` 
- `WEATHER_API_BASE_URL`: The weather API base URL.  Default value: `https://api.openweathermap.org` 
- `WEATHER_API_KEY`: The API key to access the weather API.  Default value:
- `WEATHER_DATA_JOB_CRON`: The interval for weather data fetching in the cron expression format. Default value: `0 0 */1 * * *` - every one hour
- `CONSUMPTION_DATA_JOB_CRON`: The interval for consumption data fetching in the cron expression format. Default value: `0 */5 * * * *` - every one hour
- `PRODUCTION_DATA_JOB_CRON`: The interval for production data fetching in the cron expression format. Default value: `0 0 */1 * * *` - every 5 minutes


<!-- REPOSITORY STRUCTURE -->

### Repository Structure

* `gradle/`: Gradle wrapper
* `src/main/`: Source code
* `src/test/`: Test source code
* `build.gradle`: Gradle configuration

### Packages
* `com.zendo.backend.apiclient`: For API clients.
  * WeatherApiClient: The API client of OpenWeather. API key is configurable via the environment variable `WEATHER_API_KEY`
  * EnergyDataApiClient: The API client of Energy data API (energy-data-api)
* `com.zendo.backend.endpoint`: For the back-end API endpoints
  * EnergySummaryEndpoint: Get energy summary endpoint
  * HistoricalDataEndpoint: Get historical data endpoint
* `com.zendo.backend.entity`: For data entities
  * Auditable: Base entity for auditing
  * Consumption: Consumption entity, maps to the data table: `consumptions`. For storing the consumption data fetched from the Energy data API
  * Production: Production entity, maps to the data table: `productions`. For storing the production data fetched from the Energy data API
  * ProductionDetail: Production detail entity, maps to the data table: `production_details`. For storing the production data of each source fetched from the Energy data API
  * Weather: Weather entity, maps to the data table: `weather`. For storing the weather data fetched from the weather API
  * EnergyDetail: Energy detail entity, maps to the data table: `energy_details`. For storing hourly calculated energy details. The details are calculated based on the consumption, production and weather data.
* `com.zendo.backend.event`: For application events
  * DataCollectedEvent: An event DTO. When each API data (consumption, production and weather) collected, the application will produce an event using the DTO.
* `com.zendo.backend.job`: For scheduling jobs (cron jobs).
  * ConsumptionDataCollectionJob: consumption data collection job. By default, it is triggered every 1 hour (configurable via the environment variable `CONSUMPTION_DATA_JOB_CRON`). The consumption data is retrieved from the energy data API.
  * ProductionDataCollectionJob: production data collection job. By default, it is triggered every 5 minutes (configurable via the environment variable `PRODUCTION_DATA_JOB_CRON`). The production data is retrieved from the energy data API.
  * WeatherDataCollectionJob: weather data collection job. By default, it is triggered every 1 hour (configurable via the environment variable `WEATHER_DATA_JOB_CRON`). The weather data is retrieved from the weather API.
* `com.zendo.backend.listerner`: For event listeners
  * DataCollectedEventListener: Data collected event listener. Listen to the data collected event. Once the event has been caught, it will call EnergyDetailService to create or update the energy detail for the past 24-hour data. 
* `com.zendo.backend.repository`: For the data repositories
* `com.zendo.backend.service`: For the application services
  * WeatherApiService: The weather API service. 
    * The service offers fetching and storing the weather data in **London** into the `weather` table.
    * The service offers fetching the current weather data in **London**.
  * ProductionApiService: The production API service. 
    * The service offers fetching and storing the production data into the `productions` and `production_details` tables.
  * ConsumptionApiService: The consumption API service.
      * The service offers fetching and storing the consumption data into the `consumptions` table.
  * EnergyDetailService: The energy detail service
      * The service offers creating and updating the last 24-hours energy data into the `energy_details` table.
      * The service offers getting the last 24-hours energy data for the historical data API response data.
      * The service offers getting all energy data for the energy summary API response data.
  * CorrelationCalculator: The correlation calculating service
      * The service offers calculating correlation between the solar irradiance vs the solar production.
      * The service offers calculating correlation between the temperature vs the energy consumption.
* `com.zendo.backend.utility`: For the application utilities
  * SolarIrradianceCalculator: Solar irradiance calculator.

