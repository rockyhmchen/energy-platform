package com.zendo.backend;

import com.zendo.backend.apiclient.EnergyDataApiClient;
import com.zendo.backend.apiclient.WeatherApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * HTTP client configuration
 */
@Configuration
public class HttpClientConfig {

    @Bean
    public EnergyDataApiClient energyDataApiClient(WebClient.Builder webClientBuilder,
                                                   @Value("${thirdPartyApi.energyDataApi.baseUrl}") String baseUrl,
                                                   @Value("${thirdPartyApi.energyDataApi.key}") String apiKey) {
        var webClient = webClientBuilder.baseUrl(baseUrl)
                .defaultHeader("X-API-KEY", apiKey)
                .build();
        var adapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builder()
                .exchangeAdapter(adapter)
                .build()
                .createClient(EnergyDataApiClient.class);
    }

    @Bean
    public WeatherApiClient weatherApiClient(WebClient.Builder webClientBuilder,
                                             @Value("${thirdPartyApi.weatherApi.baseUrl}") String baseUrl) {
        var webClient = webClientBuilder.baseUrl(baseUrl)
                .build();
        var adapter = WebClientAdapter.create(webClient);

        return HttpServiceProxyFactory.builder()
                .exchangeAdapter(adapter)
                .build()
                .createClient(WeatherApiClient.class);
    }
}
