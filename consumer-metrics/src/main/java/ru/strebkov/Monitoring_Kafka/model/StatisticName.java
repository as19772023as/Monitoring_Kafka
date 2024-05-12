package ru.strebkov.Monitoring_Kafka.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_measurement_statistic_names")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "id имени статистики", example = "1")
    private Long id;

    @Column(name = "name", nullable = false)
    @Schema(description = "Наименование статистики", example = "VALUE")
    private String name;

    @OneToMany(mappedBy = "statistic", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

}
