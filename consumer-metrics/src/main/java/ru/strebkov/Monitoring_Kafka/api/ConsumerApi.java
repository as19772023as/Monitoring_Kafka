package ru.strebkov.Monitoring_Kafka.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.example.myLib.dto.MetricResponse;
import ru.example.myLib.exception.ErrorMessage;

import java.util.List;

@Tag(name = "API для получения метрик")
@RequestMapping("/api/metrics")
public interface ConsumerApi {

    @Operation(summary = "Получить список всех метрик")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список метрик получен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MetricResponse.class)
                    )),
            @ApiResponse(responseCode = "400", description = "В случае нарушения контракта",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )),
            @ApiResponse(responseCode = "500", description = "В случае внутренних ошибок",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @GetMapping
    ResponseEntity<List<MetricResponse>> getAllMetrics();

    @Operation(summary = "Получить метрику по id",
            description = "Получить конкретную метрику по её id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Метрика получена",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MetricResponse.class)
                    )),
            @ApiResponse(responseCode = "400", description = "В случае нарушения контракта",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )),
            @ApiResponse(responseCode = "500", description = "В случае внутренних ошибок",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @GetMapping("/{id}")
    ResponseEntity<MetricResponse> getMetricById(
            @Parameter(description = "id метрики для получения", required = true, example = "1")
            @PathVariable Long id
    );
}
