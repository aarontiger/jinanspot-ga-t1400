package com.bingoyes.gat1400.config;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Configuration
@PropertySource(value = {"file:/opt/gat1400/conf/gat1400.yml"})
@ConfigurationProperties(prefix = "remotegat")
public class RestTemplateConfig {

     @Value("${hz-hostname}")
    private String hostname;
    @Value("${hz-port}")
    private int port;
    //@Value("${realm}")
    private String realm;


    @Value("${hz-user}")
    private String username;

    @Value("${hz-password}")
    private String password;

    @Value("${deviceId}")
    private String deviceId;


    @Bean
    public RestTemplate restTemplate() {
        HttpClient httpclient = HttpClientBuilder.create().build();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpclient));
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }


    @Bean
    public RestTemplate restTemplateDigest() {
      /*  String realm ="haorealm";
        String host ="localhost";
        int port = 9110;
        String username="javaboy";
        String password="123";*/
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //credentialsProvider.setCredentials(new AuthScope(hostname,port,realm,AuthScope.ANY_SCHEME),new UsernamePasswordCredentials(username,password));
        credentialsProvider.setCredentials(new AuthScope(hostname,port),new UsernamePasswordCredentials(username,password));
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build();
        HttpClient client = HttpClientBuilder.create().build();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        //支持中文
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        return restTemplate;
    }


}
