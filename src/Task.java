import java.util.Objects;

public class Task {
    public int id;
    public String name;
    public String description;
    public TaskState state;

    public Task(int id, String name, String description, TaskState state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
