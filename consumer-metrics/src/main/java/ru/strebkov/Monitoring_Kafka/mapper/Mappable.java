package ru.strebkov.Monitoring_Kafka.mapper;

public interface Mappable<E, D> {

    D toDTO(E entity);
}