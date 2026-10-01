package org.example.projectplan.service.impl;

import org.example.projectplan.model.Plan;
import org.example.projectplan.model.Task;
import org.example.projectplan.service.ProjectPlanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectPlanServiceImpl implements ProjectPlanService {


    @Override
    public Plan schedule(Plan plan, LocalDate projectStartDate) {
        int duration;
        Map<Integer, Task> taskMap = new HashMap<>();

        // construct the lookup
        for (Task task : plan.getTasks()) {
            taskMap.put(task.getId(), task);
        }


        for (Task task : plan.getTasks()) {
            duration = task.getDuration();

            if (task.getDependencyIds().isEmpty()) {
                LocalDate endDate = projectStartDate.plusDays(duration).minusDays(1);
                task.setStartDate(projectStartDate);
                task.setEndDate(endDate);
            } else {
                LocalDate latestDependencyEndDate = null;

                for (Integer dependencyId : task.getDependencyIds()) {
                    Task dependencyTask = taskMap.get(dependencyId);

                    if (latestDependencyEndDate == null) {
                        latestDependencyEndDate = dependencyTask.getEndDate();
                    } else {
                        if (dependencyTask.getEndDate().isAfter(latestDependencyEndDate)) {
                            latestDependencyEndDate = dependencyTask.getEndDate();
                        }
                    }
                }

                LocalDate startDate = latestDependencyEndDate.plusDays(1);
                LocalDate endDate = startDate.plusDays(duration).minusDays(1);
                task.setStartDate(startDate);
                task.setEndDate(endDate);
            }
        }
        return plan;
    }

}
