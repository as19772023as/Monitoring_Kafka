package ru.strebkov.Monitoring_Kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strebkov.Monitoring_Kafka.model.MetricName;

@Repository
public interface MetricNameRepository extends JpaRepository<MetricName, Long> {

    MetricName findMetricNameByName(String name);

}
