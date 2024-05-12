package ru.strebkov.Monitoring_Kafka.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;
import ru.strebkov.Monitoring_Kafka.exception.NotFoundException;
import ru.strebkov.Monitoring_Kafka.service.ServiceProducer;


@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты ProducerService")
class ServiceProducerImplTest {

    @Mock
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ServiceProducerImpl serviceProducer;



    @Test
    @DisplayName("Проверка метода sendMetrics() при успешном выполнении")
    public void testSendMetrics_whenMetricExist_sendMetric() throws Exception {
        // Arrange
        String metricName = "endpoint";
        MetricRequest metricRequest = new MetricRequest("name", "description", "unit", null);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"name\":\"name\"," +
                "\"description\":\"description\",\"baseUnit\":\"unit\"}", HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readValue(responseEntity.getBody(), MetricRequest.class)).thenReturn(metricRequest);

        // Act
        MetricRequest result = serviceProducer.sendMetrics(metricName);

        // Assert
        assertEquals(metricRequest, result);
        verify(kafkaTemplate, times(1)).send(anyString(), eq(metricRequest));
    }

//    @Test
//    @DisplayName("Проверка метода sendMetrics() при отсутствии метрики")
//    public void testSendMetrics_whenMetricNotFound_throwNotFoundException() {
//        // Arrange
//        String metricName = "неверный эндпоинт метрики";
//        String endpointUrl = "http://localhost:8082/actuator/metrics/" + metricName;
//        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND);
//
//        when(restTemplate.getForEntity(endpointUrl, String.class)).thenThrow(exception);
//
//        // Act Assert
//        assertThrows(NotFoundException.class, () -> serviceProducer.sendMetrics(metricName));
//        verify(kafkaTemplate, times(0)).send(anyString(), any());
//    }
}