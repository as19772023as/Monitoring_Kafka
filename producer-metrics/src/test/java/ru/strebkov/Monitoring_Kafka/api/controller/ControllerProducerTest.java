package ru.strebkov.Monitoring_Kafka.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;
import ru.strebkov.Monitoring_Kafka.service.ServiceProducer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerProducer.class)
@DisplayName("Тесты ProducerController")
class ControllerProducerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceProducer producerService;

    private final MetricRequest metricRequest = MetricRequest.builder()
            .name("имя метрики")
            .description("описание метрики")
            .baseUnit("единицы измерения")
            .measurements(null)
            .build();

    @Test
    @DisplayName("Проверка метода sendMetrics() при успешном выполнении")
    public void testSendMetrics() throws Exception {
        when(producerService.sendMetrics("эндпоинт метрики")).thenReturn(metricRequest);

        mockMvc.perform(post("/api/metrics")
                        .param("metricName", "эндпоинт метрики")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("имя метрики"))
                .andExpect(jsonPath("$.description").value("описание метрики"))
                .andExpect(jsonPath("$.baseUnit").value("единицы измерения"));
    }
}