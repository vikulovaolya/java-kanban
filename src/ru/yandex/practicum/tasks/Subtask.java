package ru.yandex.practicum.tasks;

import ru.yandex.practicum.managers.taskmanager.TaskType;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, TaskState state, int epicId) {
        super(id, name, description, state);
        this.state = state;
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public Subtask(Subtask another) {
        super(another);
        this.epicId = another.epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", type=" + type +
                ", id=" + id +
                ", epicId=" + epicId +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}