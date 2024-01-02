package ru.yandex.practicum.tasks;

public class Task {
    protected String name;
    protected String description;
    protected TaskState state;

    public Task(String name, String description, TaskState state) {
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public Task (Task another){
        this.name = another.name;
        this.description = another.description;
        this.state = another.state;
    }

    /* По поводу дополнительных конструкторов вопрос: Вот допустим я добавляю еще конструктор как указано ниже.
    Но я не могу понять как его использовать. Мне нужно сделать альтернативный метод setTask в TaskManager
    для его использования?

    public Task (String name) {
        this.name = name;
        this.description = "Добавь описание позже";
        this.state = TaskState.NEW;
    } */

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
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