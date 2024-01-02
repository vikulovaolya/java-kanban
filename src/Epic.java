import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<Integer> includeSubtaskList;

    public Epic(String name, String description, TaskState state, ArrayList<Integer> includeSubtaskList) {
        super(name, description, state);
        this.state = state;
        this.includeSubtaskList = includeSubtaskList;
    }


    @Override
    public String toString() {
        return "Epic{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", includeSubtaskList=" + includeSubtaskList +
                '}';
    }
}
