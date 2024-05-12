package ru.strebkov.Monitoring_Kafka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.example.myLib.dto.MeasurementDTO;
import ru.strebkov.Monitoring_Kafka.model.Measurement;

@Mapper(componentModel = "spring")
public interface MeasurementMapper extends Mappable<Measurement, MeasurementDTO>{
    MeasurementMapper INSTANCE = Mappers.getMapper(MeasurementMapper.class);

    @Override
    default MeasurementDTO toDTO(Measurement measurement) {
        return MeasurementDTO.builder()
                .statistic(measurement.getStatistic().getName())
                .value(measurement.getValue())
                .build();
    }
}
