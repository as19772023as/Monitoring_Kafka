package ru.strebkov.Monitoring_Kafka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.example.myLib.dto.MetricResponse;
import ru.strebkov.Monitoring_Kafka.model.Metric;

@Mapper(componentModel = "spring")
public interface MetricMapper extends Mappable<Metric, MetricResponse> {

    MetricMapper INSTANCE = Mappers.getMapper(MetricMapper.class);

    @Override
    default MetricResponse toDTO(Metric metric) {
        return MetricResponse.builder()
                .id(metric.getId())
                .name(metric.getName().getName())
                .description(metric.getDescription())
                .baseUnit(metric.getBaseUnit() != null ? metric.getBaseUnit().getName() : null)
                .date(metric.getDate())
                .measurements(metric.getMeasurements()
                        .stream()
                        .map(MeasurementMapper.INSTANCE::toDTO)
                        .toList())
                .build();
    }

}
