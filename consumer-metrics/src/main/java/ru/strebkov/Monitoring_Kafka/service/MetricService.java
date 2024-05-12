package ru.strebkov.Monitoring_Kafka.service;

import ru.example.myLib.dto.MetricRequest;
import ru.example.myLib.dto.MetricResponse;

import java.util.List;

public interface MetricService {

    List<MetricResponse> getAllMetrics();

    MetricResponse getMetricsById(Long id);

    void saveMetric(MetricRequest metricRequest);

}
