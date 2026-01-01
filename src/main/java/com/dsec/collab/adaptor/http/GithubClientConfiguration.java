package com.dsec.collab.adaptor.http;

import com.dsec.collab.adaptor.http.GithubApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.nio.charset.StandardCharsets;



@Configuration
public class GithubClientConfiguration {

    private final ILogger logger;

    public GithubClientConfiguration(ILogger logger) {
        this.logger = logger;
    }

    @Bean
    public GithubApiClient githubApiClient() {

        var factory = new BufferingClientHttpRequestFactory(new JdkClientHttpRequestFactory());

        RestClient restClient = RestClient
                .builder()
                .requestFactory(factory)
                .defaultHeader("Accept", "application/json")
                .requestInterceptor((request, bytes, execution) ->{
                    logger.logRequest(request, bytes);
                    var response = execution.execute(request, bytes);
                    return logger.logResponse(response);
                })
                .build();

        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();

        return proxyFactory.createClient(GithubApiClient.class);
    }



}