import java.util.Objects;

public class Task {
    public int id;
    public String name;
    public String description;
    public TaskState state;

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
