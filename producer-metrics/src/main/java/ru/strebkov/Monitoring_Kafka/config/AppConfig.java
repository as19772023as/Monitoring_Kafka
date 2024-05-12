package ru.strebkov.Monitoring_Kafka.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    //RestTemplate это специальный клиент в Spring для отправки http-запросов.
    // Он предоставляет удобные API для легкого вызова конечных точек REST’а в одну строку.
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        //удобные методы для регистрации конвертеров, обработчиков ошибок и UriTemplateHandlers.
        return restTemplateBuilder.build();
    }
}
