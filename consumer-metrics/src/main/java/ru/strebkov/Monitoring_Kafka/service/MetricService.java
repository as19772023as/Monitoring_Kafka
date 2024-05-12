package ru.strebkov.Monitoring_Kafka.service;



import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;
import ru.strebkov.Monitoring_Kafka.dto.MetricResponse;

import java.util.List;

public interface MetricService {

    List<MetricResponse> getAllMetrics();

    MetricResponse getMetricsById(Long id);

    void saveMetric(MetricRequest metricRequest);

}
