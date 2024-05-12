package ru.strebkov.Monitoring_Kafka.api.cotroller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.strebkov.Monitoring_Kafka.dto.MetricResponse;
import ru.strebkov.Monitoring_Kafka.service.MetricService;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsumerController.class)
@DisplayName("Тесты ConsumerController")
class ConsumerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricService metricService;

    private MetricResponse metric;

    @BeforeEach
    public void setUp() {

        metric = MetricResponse.builder()
                .id(1L)
                .name("имя метрики")
                .description("описание метрики")
                .baseUnit("bytes")
                .date(new Date())
                .measurements(null)
                .build();
    }

    @Test
    @DisplayName("Получение списка всех метрик")
    public void testGetAllMetrics() throws Exception {

        // Arrange
        when(metricService.getAllMetrics()).thenReturn(Collections.singletonList(metric));
        // Act & Assert
        mockMvc.perform(get("/api/metrics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("имя метрики"))
                .andExpect(jsonPath("$[0].description").value("описание метрики"))
                .andExpect(jsonPath("$[0].baseUnit").value("bytes"))
                .andExpect(jsonPath("$[0].date").exists());
    }


    @Test
    @DisplayName("Получение метрики по id")
    public void testGetMetricById() throws Exception {

        when(metricService.getMetricsById(1L)).thenReturn(metric);

        // Act & Assert
        mockMvc.perform(get("/api/metrics/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("имя метрики"))
                .andExpect(jsonPath("$.description").value("описание метрики"))
                .andExpect(jsonPath("$.baseUnit").value("bytes"))
                .andExpect(jsonPath("$.date").exists());
    }
}