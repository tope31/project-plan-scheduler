# Project Plan Scheduler

Simple project plan scheduler built with Java and Spring Boot.

## How it works

* Tasks without dependencies start on the project start date.
* Tasks with dependencies start after all of their dependencies are completed.
* If a task has multiple dependencies, its start date is based on the dependency that finishes last.
* Tasks that don't depend on each other can run at the same time.
* Task duration includes the start date.

For example, a task with a duration of 3 days starting on October 1 ends on October 3.

## Implementation

The scheduling logic is handled by `ProjectPlanService`.

A `HashMap` is used to look up tasks by their ID when checking dependencies.

## Tests

The unit tests cover:

* Task without dependencies
* Multiple independent tasks
* Task with one dependency
* Task with multiple dependencies

Run the tests with:

```bash
./mvnw test
```

