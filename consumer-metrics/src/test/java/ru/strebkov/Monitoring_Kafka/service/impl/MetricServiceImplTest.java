package ru.strebkov.Monitoring_Kafka.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strebkov.Monitoring_Kafka.dto.MeasurementDTO;
import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;
import ru.strebkov.Monitoring_Kafka.dto.MetricResponse;
import ru.strebkov.Monitoring_Kafka.exception.NotFoundException;
import ru.strebkov.Monitoring_Kafka.model.*;
import ru.strebkov.Monitoring_Kafka.repository.BaseUnitRepository;
import ru.strebkov.Monitoring_Kafka.repository.MetricNameRepository;
import ru.strebkov.Monitoring_Kafka.repository.MetricRepository;
import ru.strebkov.Monitoring_Kafka.service.MeasurementService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты MetricService")
class MetricServiceImplTest {
    @Mock
    private MeasurementService measurementService;
    @Mock
    private MetricRepository metricRepository;
    @Mock
    private MetricNameRepository metricNameRepository;
    @Mock
    private BaseUnitRepository baseUnitRepository;

    @InjectMocks
    private MetricServiceImpl metricService;

    private Metric metric;
    private MetricResponse metricResponse;

    @BeforeEach
    public void setUp() {

        MetricName metricName = MetricName.builder()
                .id(1L)
                .name("имя метрики")
                .build();

        BaseUnit baseUnit = BaseUnit.builder()
                .id(1L)
                .name("bytes")
                .build();

        metric = Metric.builder()
                .id(1L)
                .name(metricName)
                .description("описание метрики")
                .baseUnit(baseUnit)
                .date(new Date())
                .measurements(new ArrayList<>())
                .build();

        metricResponse = MetricResponse.builder()
                .id(1L)
                .name("имя метрики")
                .description("описание метрики")
                .baseUnit("bytes")
                .date(new Date())
                .measurements(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Проверка метода getAllMetrics() при успешном выполнении")
    public void testGetAllMetrics_ReturnAllMetrics() {
        // Arrange
        var metrics = List.of(metric);
        when(metricRepository.findAll()).thenReturn(metrics);

        // Act
        List<MetricResponse> result = metricService.getAllMetrics();

        // Assert
        assertNotNull(result);
        assertEquals(metrics.size(), result.size());
        assertNotNull(result);
        assertEquals(result.get(0).id(), metricResponse.id());
        assertEquals(result.get(0).name(), metricResponse.name());
        assertEquals(result.get(0).description(), metricResponse.description());
        assertEquals(result.get(0).baseUnit(), metricResponse.baseUnit());
        assertNotNull(result.get(0).date());
        assertEquals(result.get(0).measurements(), metricResponse.measurements());

        verify(metricRepository).findAll();
    }

    @Test
    @DisplayName("Проверка метода getMetricById() при успешном выполнении")
    public void testGetMetricsById_WhenMetricExists_ReturnMetric() {
        // Arrange
        when(metricRepository.findById(1L)).thenReturn(Optional.of(metric));

        // Act
        MetricResponse result = metricService.getMetricsById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(result.id(), metricResponse.id());
        assertEquals(result.name(), metricResponse.name());
        assertEquals(result.description(), metricResponse.description());
        assertEquals(result.baseUnit(), metricResponse.baseUnit());
        assertNotNull(result.date());
        assertEquals(result.measurements(), metricResponse.measurements());

        verify(metricRepository).findById(1L);
    }

    @Test
    @DisplayName("Проверка метода getMetricById() при исключении: несуществующий id")
    public void testGetMetricsById_WhenMetricNotExists_ThrowNotFoundException() {
        // Arrange
        when(metricRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> metricService.getMetricsById(2L));
    }

    @Test
    @DisplayName("Проверка метода saveMetric при успешном выполнении")
    public void testSaveMetric_ValidData_SaveMetricAndMeasurements() {
        // Arrange
        var metricRequest = MetricRequest.builder()
                .name("имя метрики")
                .description("описание метрики")
                .baseUnit("bytes")
                .measurements(List.of(new MeasurementDTO("STAT", 1.0f)))
                .build();

        var measurement = Measurement.builder()
                .id(1L)
                .statistic(StatisticName.builder()
                        .id(1L)
                        .name("STAT")
                        .build())
                .value(1.0f)
                .build();

        when(metricNameRepository.findMetricNameByName(anyString())).thenReturn(null);
        when(baseUnitRepository.findBaseUnitNameByName(anyString())).thenReturn(null);
        when(metricRepository.save(any(Metric.class))).thenAnswer(metric -> metric.getArgument(0));
        when(measurementService.saveMeasurements(any(MeasurementDTO.class))).thenReturn(measurement);

        // Act
        metricService.saveMetric(metricRequest);

        // Assert
        verify(metricNameRepository).save(any(MetricName.class));
        verify(baseUnitRepository).save(any(BaseUnit.class));
        verify(metricRepository).save(any(Metric.class));
        verify(measurementService, times(metricRequest.measurements().size())).saveMeasurements(any(MeasurementDTO.class));
    }
}