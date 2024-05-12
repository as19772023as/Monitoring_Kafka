package ru.strebkov.Monitoring_Kafka.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_metric_names")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "id имени метрики", example = "1")
    private Long id;

    @Column(name = "name", nullable = false)
    @Schema(description = "Наименование метрики", example = "disk.total")
    private String name;

    @OneToMany(mappedBy = "name", cascade = CascadeType.ALL)
    private List<Metric> metrics;

}
