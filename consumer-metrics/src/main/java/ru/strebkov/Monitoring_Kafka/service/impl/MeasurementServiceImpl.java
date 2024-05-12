package ru.strebkov.Monitoring_Kafka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.myLib.dto.MeasurementDTO;
import ru.strebkov.Monitoring_Kafka.model.Measurement;
import ru.strebkov.Monitoring_Kafka.model.StatisticName;
import ru.strebkov.Monitoring_Kafka.repository.MeasurementRepository;
import ru.strebkov.Monitoring_Kafka.repository.StatisticNameRepository;
import ru.strebkov.Monitoring_Kafka.service.MeasurementService;

@Service
@RequiredArgsConstructor
@Transactional
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final StatisticNameRepository statisticNameRepository;

    public Measurement saveMeasurements(MeasurementDTO measurement){
        StatisticName statisticName = findOrSaveStatisticName(measurement.statistic());

        return measurementRepository.save(Measurement.builder()
                .statistic(statisticName)
                .value(measurement.value())
                .build());
    }

    private StatisticName findOrSaveStatisticName(String name) {
        StatisticName statisticName = statisticNameRepository.findStatisticNameByName(name);
        if (statisticName == null) {
            statisticName = statisticNameRepository.save(StatisticName.builder()
                    .name(name)
                    .build());
        }
        return statisticName;
    }
}
