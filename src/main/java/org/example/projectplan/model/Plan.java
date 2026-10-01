package org.example.projectplan.model;

import lombok.Data;

import java.util.List;

@Data

public class Plan {
    private int id;
    private String name;
    private List<Task> tasks;
}
