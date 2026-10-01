package org.example.projectplan.service;

import org.example.projectplan.model.Plan;
import org.example.projectplan.model.Task;
import org.example.projectplan.service.impl.ProjectPlanServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;


public class PlanServiceTest {

    private static Plan createPlan() {
        Task task1 = Task.builder()
                .id(1)
                .name("Draw character illustrations")
                .duration(3)
                .dependencyIds(List.of())
                .build();

        Task task2 = Task.builder()
                .id(2)
                .name("Create BGM")
                .duration(15)
                .dependencyIds(List.of())
                .build();

        Plan plan = new Plan();
        plan.setId(1);
        plan.setName("Visual Novel Project Plan");
        plan.setTasks(List.of(task1, task2));

        return plan;
    }

    private static Plan createPlanWithDependency() {
        Task task1 = Task.builder()
                .id(1)
                .name("Draw character illustrations")
                .duration(3)
                .dependencyIds(List.of())
                .build();

        Task task2 = Task.builder()
                .id(2)
                .name("Draw background CG")
                .duration(7)
                .dependencyIds(List.of(1))
                .build();

        Plan plan = new Plan();
        plan.setId(1);
        plan.setName("Visual Novel Project Plan");
        plan.setTasks(List.of(task1, task2));

        return plan;
    }

    private static Plan createPlanWithMultipleDependencies() {
        Task task1 = Task.builder()
                .id(1)
                .name("Draw character illustrations")
                .duration(3)
                .dependencyIds(List.of())
                .build();

        Task task2 = Task.builder()
                .id(2)
                .name("Create BGM")
                .duration(5)
                .dependencyIds(List.of())
                .build();

        Task task3 = Task.builder()
                .id(3)
                .name("Draw background CG")
                .duration(2)
                .dependencyIds(List.of(1, 2))
                .build();

        Plan plan = new Plan();
        plan.setId(1);
        plan.setName("Visual Novel Project Plan");
        plan.setTasks(List.of(task1, task2, task3));

        return plan;
    }


    @Test
    void shouldScheduleTaskWithoutDependencies() {
        ProjectPlanService service = new ProjectPlanServiceImpl();

        Plan result = service.schedule(createPlan(), LocalDate.of(2026, 10, 1));

        Assertions.assertEquals(LocalDate.of(2026, 10, 1), result.getTasks().get(0).getStartDate());
        Assertions.assertEquals(LocalDate.of(2026, 10, 3), result.getTasks().get(0).getEndDate());
    }

    @Test
    void shouldScheduleTwoTaskWithoutDependencies() {
        ProjectPlanService service = new ProjectPlanServiceImpl();

        Plan result = service.schedule(createPlan(), LocalDate.of(2026, 10, 1));

        Assertions.assertEquals(LocalDate.of(2026, 10, 1), result.getTasks().get(0).getStartDate());
        Assertions.assertEquals(LocalDate.of(2026, 10, 3), result.getTasks().get(0).getEndDate());
        Assertions.assertEquals(LocalDate.of(2026, 10, 1), result.getTasks().get(1).getStartDate());
        Assertions.assertEquals(LocalDate.of(2026, 10, 15), result.getTasks().get(1).getEndDate());
    }

    @Test
    void shouldScheduleTaskWithDependency() {
        ProjectPlanService service = new ProjectPlanServiceImpl();

        Plan result = service.schedule(createPlanWithDependency(), LocalDate.of(2026, 10, 1));

        // Task 1 Assertion
        Assertions.assertEquals(LocalDate.of(2026, 10, 1), result.getTasks().get(0).getStartDate());
        Assertions.assertEquals(LocalDate.of(2026, 10, 3), result.getTasks().get(0).getEndDate());

        // Task 2 Assertion
        Assertions.assertEquals(LocalDate.of(2026, 10, 4), result.getTasks().get(1).getStartDate());
        Assertions.assertEquals(LocalDate.of(2026, 10, 10), result.getTasks().get(1).getEndDate());
    }

    @Test
    void shouldScheduleTaskWithTwoDependencies() {
        ProjectPlanService service = new ProjectPlanServiceImpl();

        Plan result = service.schedule(createPlanWithMultipleDependencies(), LocalDate.of(2026, 10, 1));

        Assertions.assertEquals(LocalDate.of(2026, 10, 6), result.getTasks().get(2).getStartDate());
        Assertions.assertEquals(LocalDate.of(2026, 10, 7), result.getTasks().get(2).getEndDate());
    }
}
