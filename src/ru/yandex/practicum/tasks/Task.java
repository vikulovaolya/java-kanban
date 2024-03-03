package ru.yandex.practicum.tasks;

public class Task {
    protected String name;
    protected String description;
    protected TaskState state;
    protected int id;
    protected Task prev;
    protected Task next;

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
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", id=" + id +
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task getPrev() {
        return prev;
    }

    public Task getNext() {
        return next;
    }

    public void setPrev(Task prev) {
        this.prev = prev;
    }

    public void setNext(Task next) {
        this.next = next;
    }
}