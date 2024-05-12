package ru.strebkov.Monitoring_Kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // @Scheduled(fixedRate = 10000) над методами,
// работа в фоновом режиме, с периодичностью
public class ProducerMetricsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerMetricsApplication.class);
    }
}
