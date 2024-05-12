package ru.strebkov.Monitoring_Kafka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strebkov.Monitoring_Kafka.dto.MeasurementDTO;
import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;
import ru.strebkov.Monitoring_Kafka.dto.MetricResponse;
import ru.strebkov.Monitoring_Kafka.exception.NotFoundException;
import ru.strebkov.Monitoring_Kafka.mapper.MetricMapper;
import ru.strebkov.Monitoring_Kafka.model.BaseUnit;
import ru.strebkov.Monitoring_Kafka.model.Measurement;
import ru.strebkov.Monitoring_Kafka.model.Metric;
import ru.strebkov.Monitoring_Kafka.model.MetricName;
import ru.strebkov.Monitoring_Kafka.repository.BaseUnitRepository;
import ru.strebkov.Monitoring_Kafka.repository.MetricNameRepository;
import ru.strebkov.Monitoring_Kafka.repository.MetricRepository;
import ru.strebkov.Monitoring_Kafka.service.MeasurementService;
import ru.strebkov.Monitoring_Kafka.service.MetricService;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MetricServiceImpl implements MetricService {

    private final MeasurementService measurementService;

    private final MetricRepository metricRepository;
    private final MetricNameRepository metricNameRepository;
    private final BaseUnitRepository baseUnitRepository;

    public List<MetricResponse> getAllMetrics() {
        return metricRepository.findAll()
                .stream()
                .map(MetricMapper.INSTANCE::toDTO)
                .toList();
    }

    public MetricResponse getMetricsById(Long id) {
        return MetricMapper.INSTANCE.toDTO(metricRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Метрики с заданным id не существует")));
    }

    public void saveMetric(MetricRequest metricRequest) {
        MetricName metricName = findOrSaveMetricName(metricRequest.name());

        BaseUnit baseUnit = null;
        if (metricRequest.baseUnit() != null) {
            baseUnit = findOrSaveBaseUnit(metricRequest.baseUnit());
        }

        Metric metric = metricRepository.save(Metric.builder()
                .name(metricName)
                .description(metricRequest.description())
                .baseUnit(baseUnit)
                .date(new Date())
                .build());

        for (MeasurementDTO measurementDTO : metricRequest.measurements()) {
            Measurement measurement = measurementService.saveMeasurements(measurementDTO);
            measurement.setMetric(metric);
        }
    }

    private MetricName findOrSaveMetricName(String name) {
        MetricName metricName = metricNameRepository.findMetricNameByName(name);
        if (metricName == null) {
            metricName = metricNameRepository.save(MetricName.builder()
                    .name(name)
                    .build());
        }
        return metricName;
    }

    private BaseUnit findOrSaveBaseUnit(String baseUnitName) {
        BaseUnit baseUnit = baseUnitRepository.findBaseUnitNameByName(baseUnitName);
        if (baseUnit == null) {
            baseUnit = baseUnitRepository.save(BaseUnit.builder()
                    .name(baseUnitName)
                    .build());
        }
        return baseUnit;
    }
}
