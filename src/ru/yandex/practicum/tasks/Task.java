package ru.yandex.practicum.tasks;

public class Task {
    protected String name;
    protected String description;
    protected TaskState state;
    protected int id;

    public Task(int id, String name, String description, TaskState state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public Task (Task another){
        this.id = another.id;
        this.name = another.name;
        this.description = another.description;
        this.state = another.state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskState getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(TaskState state) {
        this.state = state;
    }
}