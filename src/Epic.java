import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Integer> includeSubtaskList;

    public Epic(int id, String name, String description, TaskState state, ArrayList<Integer> includeSubtaskList) {
        super(id, name, description, state);
        this.state = state;
        this.includeSubtaskList = includeSubtaskList;
    }


    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", includeSubtaskList=" + includeSubtaskList +
                '}';
    }
}
