package ru.strebkov.Monitoring_Kafka.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_base_unit_names")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "id единицы измерения", example = "1")
    private Long id;

    @Column(name = "name", nullable = false)
    @Schema(description = "Наименование единицы измерения", example = "bytes")
    private String name;

    @OneToMany(mappedBy = "baseUnit", cascade = CascadeType.ALL)
    private List<Metric> metrics;

}
