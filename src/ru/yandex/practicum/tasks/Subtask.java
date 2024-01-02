package ru.yandex.practicum.tasks;

public class Subtask extends Task {
    private int epicId;
    public Subtask(String name, String description, TaskState state, int epicId) {
        super(name, description, state);
        this.state = state;
        this.epicId = epicId;
    }

    public Subtask(Subtask another) {
        super(another);
        this.epicId = another.epicId;
    }

    /* public Subtask(String name, int epicId){
        super(name);
        this.epicId = epicId;
    }*/

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}