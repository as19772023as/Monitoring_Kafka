package ru.strebkov.Monitoring_Kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strebkov.Monitoring_Kafka.model.StatisticName;

@Repository
public interface StatisticNameRepository extends JpaRepository<StatisticName, Long> {

    StatisticName findStatisticNameByName(String name);
}