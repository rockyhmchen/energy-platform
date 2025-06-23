# Energy Data API

This is a mock energy data API server.

## Get latest energy consumption data

Use the following API endpoint to get the random consumption data generated every hour.

Endpoint:
```shell
GET /consumption/latest
```

Response:
```json
{
  "timestamp": "2025-06-23T01:00:00.000+0000",
  "totalConsumptionKwh": 1.3
}
```

curl:
```shell
curl http://localhost:8080/consumption/latest
```


## Get latest energy production data

Use the following API endpoint to get the random production data generated 5 minutes

Endpoint:
```shell
GET /production/latest
```

Response:
```json
{
  "timestamp": "2025-06-23T01:10:00.000+0000",
  "detail": {
    "solarProductionKwh": 7.3,
    "windProductionKwh": 4.9
  },
  "totalProductionKwh": 12.2
}
```

curl:
```shell
curl http://localhost:8080/production/latest
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

curl:
```shell
curl http://localhost:8080/health
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
  docker build -t zendoenergy.com/energy-data-api:0.0.1 .
  ```

* Run the docker image
  ```shell
  docker run --rm -it -p 8080:8080 zendoenergy.com/energy-data-api:0.0.1
  ```
