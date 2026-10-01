package org.example.projectplan.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Task {
    private int id;
    private String name;
    private int duration;
    private List<Integer> dependencyIds;
    private LocalDate startDate;
    private LocalDate endDate;
}
