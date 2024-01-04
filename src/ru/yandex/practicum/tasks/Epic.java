package ru.yandex.practicum.tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> includeSubtaskList;

    public Epic(String name, String description, TaskState state, ArrayList<Integer> includeSubtaskList) {
        super(name, description, state);
        this.state = state;
        this.includeSubtaskList = includeSubtaskList;
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
                ", includeSubtaskList=" + includeSubtaskList +
                '}';
    }

    public ArrayList<Integer> getIncludeSubtaskList() {
        return includeSubtaskList;
    }

    public void setIncludeSubtaskList(ArrayList<Integer> includeSubtaskList) {
        this.includeSubtaskList = includeSubtaskList;
    }
}