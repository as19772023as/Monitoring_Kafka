package ru.strebkov.Monitoring_Kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strebkov.Monitoring_Kafka.model.BaseUnit;

@Repository
public interface BaseUnitRepository extends JpaRepository<BaseUnit, Long> {

    BaseUnit findBaseUnitNameByName(String name);

}