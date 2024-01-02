public class Subtask extends Task{
    public int epicId;
    public Subtask(String name, String description, TaskState state, int epicId) {
        super(name, description, state);
        this.state = state;
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }
}
