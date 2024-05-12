package ru.strebkov.Monitoring_Kafka.service;

import org.springframework.stereotype.Service;
import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;


//@Service
public interface ServiceProducer {
    public MetricRequest sendMetrics(String metricName) ;
}
