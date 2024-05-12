package ru.strebkov.Monitoring_Kafka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.strebkov.Monitoring_Kafka.dto.MeasurementDTO;

import java.util.List;

@Builder
@Schema(description = "DTO для метрики")
public record MetricRequest(

        @Schema(description = "Название метрики", example = "jvm.info")
        @NotNull
        String name,

        @Schema(description = "Описание метрики", example = "Total space for path")
        String description,

        @Schema(description = "Базовая единица метрики", example = "bytes")
        String baseUnit,

        @Schema(description = "Список измерений")
        List<MeasurementDTO> measurements

) {

}
