package ru.strebkov.Monitoring_Kafka.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorMessage(

        int statusCode,
        String description,
        LocalDateTime currentTime
) {

}
