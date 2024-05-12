package ru.strebkov.Monitoring_Kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;
import ru.strebkov.Monitoring_Kafka.exception.NotFoundException;
import ru.strebkov.Monitoring_Kafka.model.MainMetrics;
import ru.strebkov.Monitoring_Kafka.service.ServiceProducer;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceProducerImpl implements ServiceProducer {

    private final KafkaTemplate<Object, Object> template;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${some.endpoint:http://localhost:8082/actuator/metrics/}")
    private String endpoint;

    @Override
    public MetricRequest sendMetrics(String metricName) {
        MetricRequest metric = getMetrics(metricName);

        log.info("ControllerProducer send: {}", metric.name());
        template.send("metrics-topic", metric);

        return metric;
    }

    private MetricRequest getMetrics(String metricName) {
//      String endpointUrl = "http://localhost:8082/actuator/metrics/" + metricName;
        String endpointUrl = endpoint + metricName;

        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(endpointUrl, String.class);
        } catch (HttpClientErrorException e) {
            throw new NotFoundException("Метрики с заданным именем не существует, " +
                    "для просмотра доступных метрик используйте /actuator/metrics");
        }
        MetricRequest metricRequest;

        try {
            metricRequest = objectMapper.readValue(responseEntity.getBody(), MetricRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }

        return metricRequest;
    }

    @Scheduled(fixedRate = 5000L) // запускается в фоновом режиме, не нужно руками вводить в Postman
    private void sendRandomMainMetric() {
        Random random = new Random();
        MainMetrics[] metrics = MainMetrics.values();

        MainMetrics randomMetric = metrics[random.nextInt(metrics.length)];
        sendMetrics(randomMetric.getAddress());
    }
}
