package ru.strebkov.Monitoring_Kafka.service;

import ru.example.myLib.dto.MeasurementDTO;
import ru.strebkov.Monitoring_Kafka.model.Measurement;

public interface MeasurementService {

    Measurement saveMeasurements(MeasurementDTO measurementDTO);
}
