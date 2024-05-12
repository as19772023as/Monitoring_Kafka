package ru.strebkov.Monitoring_Kafka.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.strebkov.Monitoring_Kafka.api.ApiProducer;
import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;
import ru.strebkov.Monitoring_Kafka.service.ServiceProducer;

@RestController
@RequiredArgsConstructor
public class ControllerProducer implements ApiProducer {

    private final ServiceProducer serviceProducer;

    @Override
    public ResponseEntity<MetricRequest> sendMetrics(String metricName) {
        return ResponseEntity.ok(serviceProducer.sendMetrics(metricName));
    }
}
