package ru.strebkov.Monitoring_Kafka.api.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.strebkov.Monitoring_Kafka.api.ConsumerApi;
import ru.example.myLib.dto.MetricResponse;
import ru.strebkov.Monitoring_Kafka.service.MetricService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConsumerController implements ConsumerApi {

    private final MetricService metricService;

    @Override
    public ResponseEntity<List<MetricResponse>> getAllMetrics() {
        return ResponseEntity.ok(metricService.getAllMetrics());
    }

    @Override
    public ResponseEntity<MetricResponse> getMetricById(Long id) {
        return ResponseEntity.ok(metricService.getMetricsById(id));
    }
}
