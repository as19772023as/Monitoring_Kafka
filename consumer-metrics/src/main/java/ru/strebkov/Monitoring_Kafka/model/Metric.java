package ru.strebkov.Monitoring_Kafka.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_metrics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "id метрики", example = "1")
    private Long id;

    @Column(name = "description")
    @Schema(description = "Описание метрики", example = "Total space for path")
    private String description;

    @Column(name = "date")
    @Schema(description = "Дата сохранения метрики", example = "2024-05-09T12:00:00Z")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "name_id", nullable = false)
    private MetricName name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_unit_id")
    private BaseUnit baseUnit;

    @OneToMany(mappedBy = "metric", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

}
