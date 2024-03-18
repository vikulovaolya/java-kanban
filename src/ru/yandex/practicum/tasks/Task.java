package ru.yandex.practicum.tasks;

import ru.yandex.practicum.managers.taskmanager.TaskType;

public class Task {
    protected String name;
    protected String description;
    protected TaskState state;
    protected TaskType type;

    protected int id;
    protected Task prev;
    protected Task next;

    public Task(int id, String name, String description, TaskState state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.type = TaskType.TASK;
    }

    public Task(Task another) {
        this.id = another.id;
        this.name = another.name;
        this.description = another.description;
        this.state = another.state;
        this.type = TaskType.TASK;
    }

    @Override
    public String toString() { //id,type,name,status,description,epic
        return id + "," + type + "," + name + "," + state + "," + description + ",";
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

    public TaskType getType() {
        return type;
    }
}