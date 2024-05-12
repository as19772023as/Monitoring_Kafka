package ru.strebkov.Monitoring_Kafka.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.strebkov.Monitoring_Kafka.dto.MetricRequest;
import ru.strebkov.Monitoring_Kafka.exception.ErrorMessage;


@Tag(name = "API для отправки метрик")
@RequestMapping("/api/metrics")
public interface ApiProducer {

    @Operation(summary = "Отправить метрику", description = "Отправить данные запрошенной метрики")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Метрика успешно отправлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MetricRequest.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Нарушение контракта",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )),
            @ApiResponse(responseCode = "500", description = "В случае внутренних ошибок",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    ))
    })
    @PostMapping
    ResponseEntity<MetricRequest> sendMetrics(
            @Parameter(description = "Имя метрики для отправки", required = true, example = "jvm.info")
            @RequestParam(name = "metricName")
            String metricName
    );

}
