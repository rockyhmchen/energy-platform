# energy-platform

## Run for demo

* Run the entire platform (front-end dashboard, back-end service and energy-data-api)

```shell
docker-compose -f docker-compose.yaml up
```

* Then, visit `http://localhost:3000` with a browser

* Shutdown the entire platform

```shell
docker-compose -f docker-compose.yaml down
```

## Configuration

In `/docker-compose.yaml`,

- `NEXT_PUBLIC_TO_CALL_BACK_END`: is to set up whether fetching the data from the actual back-end service, or directly showing the mock data.
  - `true`: fetching the data from the actual back-end service.
  - `false`: directly showing the mock data.

    ```yaml
    services:
      front-end:
        environment:
          NEXT_PUBLIC_TO_CALL_BACK_END: <tru/false>
    ```

- `WEATHER_API_KEY`: set your real API key of OpenWeather to fetch the real-time weather data
- 
    ```yaml
    services:
      back-end:
        environment:
          WEATHER_API_KEY: <the API key of OpenWeather>
    ```

