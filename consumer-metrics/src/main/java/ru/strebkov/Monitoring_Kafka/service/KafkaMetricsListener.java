package ru.strebkov.Monitoring_Kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.example.myLib.dto.MetricRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMetricsListener {

    private final MetricService metricService;

    @KafkaListener(id = "MetricGroup", topics = "metrics-topic")
    public void listen(MetricRequest metric){
        log.info("Received: {}", metric.name());
        if (metric.name().startsWith("fail")){
            throw new RuntimeException();
        }
        log.info("Ok");

        metricService.saveMetric(metric);
    }

    @KafkaListener(id = "dltGroup", topics = "topic.DLT")
    public void dltListen(byte[] in){
        log.info("Received from dlt: {}", new String(in));
    }
}