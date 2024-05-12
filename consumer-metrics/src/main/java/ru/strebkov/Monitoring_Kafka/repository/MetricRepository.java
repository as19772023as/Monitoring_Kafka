package ru.strebkov.Monitoring_Kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strebkov.Monitoring_Kafka.model.Metric;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
}
