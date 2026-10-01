package org.example.projectplan.service;

import org.example.projectplan.model.Plan;

import java.time.LocalDate;

public interface ProjectPlanService {
    Plan schedule(Plan plan, LocalDate projectStartDate);
}
