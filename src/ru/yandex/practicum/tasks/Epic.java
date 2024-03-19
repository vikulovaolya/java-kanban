package ru.yandex.practicum.tasks;

import ru.yandex.practicum.managers.taskmanager.TaskType;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> includeSubtaskList;

    public Epic(int id, String name, String description, TaskState state, ArrayList<Integer> includeSubtaskList) {
        super(id, name, description, state);
        this.state = state;
        this.includeSubtaskList = includeSubtaskList;
        this.type = TaskType.EPIC;
    }

    public Epic(Epic another) {
        super(another);
        this.includeSubtaskList = another.includeSubtaskList;

    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", type=" + type +
                ", id=" + id +
                '}';
    }

    public ArrayList<Integer> getIncludeSubtaskList() {
        return includeSubtaskList;
    }

    public void setIncludeSubtaskList(ArrayList<Integer> includeSubtaskList) {
        this.includeSubtaskList = includeSubtaskList;
    }

}