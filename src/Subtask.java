public class Subtask extends Task{
    public int epicId;
    public Subtask(int id, String name, String description, TaskState state, int epicId) {
        super(id, name, description, state);
        this.state = state;
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }
}
