import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public int counterId = 0;
    public int taskId;

    public HashMap <Integer, Task> taskList = new HashMap<>();
    public HashMap <Integer, Subtask> subtaskList = new HashMap<>();
    public  HashMap <Integer, Epic> epicList = new HashMap<>();

    private int getCountId(){
        counterId += 1;
        return  counterId;
    }

    public Task setTask(String name, String description, TaskState state){
        int taskId = getCountId();
        Task task = new Task(taskId, name, description, state);
        taskList.put(taskId, task);
        return taskList.get(taskId);
    }

    public Subtask setSubtask(String name, String description, TaskState state, int epicId) {
        int taskId = getCountId();
        Subtask subtask = new Subtask(taskId, name, description, state, epicId);
        subtaskList.put(taskId, subtask);
        ArrayList<Integer> arrayOfSubtasksForEpic = epicList.get(epicId).includeSubtaskList;
        if (arrayOfSubtasksForEpic != null) {
            arrayOfSubtasksForEpic.add(taskId);
        } else {
            ArrayList<Integer> newArrayOfSubtasksForEpic = new ArrayList<>();
            newArrayOfSubtasksForEpic.add(taskId);
            epicList.get(epicId).includeSubtaskList = newArrayOfSubtasksForEpic;
        }
        updateEpic(epicId, epicList.get(epicId).name, epicList.get(epicId).description,
                epicList.get(epicId).includeSubtaskList);
        return subtaskList.get(taskId);
    }

    public Epic setEpic(String name, String description, ArrayList<Integer> includeSubtaskList) {
        int taskId = getCountId();
        TaskState state = getEpicState(includeSubtaskList);
        Epic epic = new Epic(taskId, name, description, state, includeSubtaskList);
        epicList.put(taskId, epic);
        if (includeSubtaskList != null){
            for (int subtaskId: includeSubtaskList){
                subtaskList.get(subtaskId).epicId = taskId;
            }
        }
        return epicList.get(taskId);
    }

    public ArrayList <Subtask> getSubtaskListForEpic (int taskId) {
        ArrayList<Subtask> subtaskListForEpic = new ArrayList<>();
        for (Integer subtaskId : epicList.get(taskId).includeSubtaskList) {
            Subtask subtaskForEpic = subtaskList.get(subtaskId);
            subtaskListForEpic.add(subtaskForEpic);
        }
        return subtaskListForEpic;
    }

    public HashMap<Integer, Task> getTaskList() {
        return taskList;
    }

    public HashMap<Integer, Subtask> getSubtaskList() {
        return subtaskList;
    }

    public HashMap<Integer, Epic> getEpicList() {
        return epicList;
    }

    public void clearTaskList(){
        taskList.clear();
    }
    public void clearSubtaskList(){
        for (Subtask subtask: subtaskList.values()){
            deleteConnectionWithSubtaskForEpic(subtask);
        }
        subtaskList.clear();
    }
    public void clearEpicList(){
        for (Epic epic: epicList.values()){
            deleteSubtasksForEpic(epic);
        }
        epicList.clear();
    }

    public Task getTask(int taskId){
        return taskList.get(taskId);
    }

    public Subtask getSubtask(int taskId){
        return subtaskList.get(taskId);
    }

    public Epic getEpic(int taskId){
        return epicList.get(taskId);
    }

    public void deleteTask(int taskId){
        taskList.remove(taskId);
    }

    public void deleteSubtask(int taskId){
        Subtask subtask = subtaskList.get(taskId);
        deleteConnectionWithSubtaskForEpic(subtask);
        subtaskList.remove(taskId);
    }

    public void deleteEpic(int taskId){
        Epic epic = epicList.get(taskId);
        deleteSubtasksForEpic(epic);
        epicList.remove(taskId);
    }

    public Task updateTask(int taskId, String name, String description, TaskState state){
        if (taskList.containsKey(taskId)){
            taskList.remove(taskId);
            Task task = new Task(taskId, name, description, state);
            taskList.put(taskId, task);
            return taskList.get(taskId);
        } else {
            return null;
        }
    }

    public Subtask updateSubtask(int taskId, String name, String description, TaskState state, int epicId){
        if (subtaskList.containsKey(taskId)){
            if (epicId != subtaskList.get(taskId).epicId){
                epicList.get(epicId).includeSubtaskList.add(taskId);
                int oldEpic = subtaskList.get(taskId).epicId;
                ArrayList<Integer> oldEpicSubtaskList = epicList.get(oldEpic).includeSubtaskList;
                oldEpicSubtaskList.remove((Integer) taskId);
                updateEpic(oldEpic, epicList.get(oldEpic).name, epicList.get(oldEpic).description,
                        epicList.get(oldEpic).includeSubtaskList);
            }
            Subtask subtask = new Subtask(taskId, name, description, state, epicId);
            subtaskList.remove(taskId);
            subtaskList.put(taskId, subtask);
            updateEpic(epicId, epicList.get(epicId).name, epicList.get(epicId).description,
                    epicList.get(epicId).includeSubtaskList);
            return subtaskList.get(taskId);
        } else {
            return null;
        }
    }

    public Epic updateEpic(int taskId, String name, String description, ArrayList<Integer> includeSubtaskList){
        if (epicList.containsKey(taskId)) {
            TaskState state = getEpicState(includeSubtaskList);
            if (includeSubtaskList != epicList.get(taskId).includeSubtaskList){
                if (includeSubtaskList != null){
                    for (int subtaskId: includeSubtaskList){
                        subtaskList.get(subtaskId).epicId = taskId;
                    }
                }
            }
            epicList.remove(taskId);
            Epic epic = new Epic(taskId, name, description, state, includeSubtaskList);
            epicList.put(taskId, epic);
            return epicList.get(taskId);
        } else {
            return null;
        }
    }

    public TaskState getEpicState(ArrayList <Integer> includeSubtaskList){
        TaskState state = null;
        if (includeSubtaskList == null) {
            state = TaskState.NEW;
        } else {
            int newStateSubtasks = 0;
            int doneStateSubtasks = 0;
            for (Integer subtaskId : includeSubtaskList) {
                if (subtaskList.get(subtaskId).state == TaskState.NEW) {
                    newStateSubtasks += 1;
                } else if (subtaskList.get(subtaskId).state == TaskState.DONE) {
                    doneStateSubtasks += 1;
                }
            }
            if (newStateSubtasks == includeSubtaskList.size()) {
                state = TaskState.NEW;
            } else if (doneStateSubtasks == includeSubtaskList.size()) {
                state = TaskState.DONE;
            } else {
                state = TaskState.IN_PROGRESS;
            }
        }
        return state;
    }

    public void deleteConnectionWithSubtaskForEpic (Subtask subtask){
        int epicIdFromSubtask = subtask.epicId;
        ArrayList<Integer> arrayOfSubtasksForEpic = epicList.get(epicIdFromSubtask).includeSubtaskList;
        if (arrayOfSubtasksForEpic != null) {
            arrayOfSubtasksForEpic.remove((Integer) subtask.id);
        }
        updateEpic(epicIdFromSubtask, epicList.get(epicIdFromSubtask).name, epicList.get(epicIdFromSubtask).description,
                arrayOfSubtasksForEpic);
    }

    public void deleteSubtasksForEpic(Epic epic){
        if (epic.includeSubtaskList != null){
            for (int subtaskId: epic.includeSubtaskList){
                subtaskList.remove(subtaskId);
            }
        }
    }
}
