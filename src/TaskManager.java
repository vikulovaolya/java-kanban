import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    public static int counterId = 0;
    public int taskId;

    public static HashMap <Integer, Task> taskList = new HashMap<>();
    public static HashMap <Integer, Subtask> subtaskList = new HashMap<>();
    public static  HashMap <Integer, Epic> epicList = new HashMap<>();

    private static void countId (){
        counterId += 1;
    }

    public static Task setTask(String name, String description, TaskState state){
        countId();
        Task task = new Task(counterId, name, description, state);
        taskList.put(counterId, task);
        return taskList.get(counterId);
    }

    public static Subtask setSubtask(String name, String description, TaskState state, int epicId) {
        countId();
        Subtask subtask = new Subtask(counterId, name, description, state, epicId);
        subtaskList.put(counterId, subtask);
        ArrayList<Integer> arrayOfSubtasksForEpic = epicList.get(epicId).includeSubtaskList;
        if (arrayOfSubtasksForEpic != null) {
            arrayOfSubtasksForEpic.add(counterId);
        } else {
            ArrayList<Integer> newArrayOfSubtasksForEpic = new ArrayList<>();
            newArrayOfSubtasksForEpic.add(counterId);
            epicList.get(epicId).includeSubtaskList = newArrayOfSubtasksForEpic;
        }
        updateEpic(epicId, epicList.get(epicId).name, epicList.get(epicId).description,
                epicList.get(epicId).includeSubtaskList);
        return subtaskList.get(counterId);
    }

    public static Epic setEpic(String name, String description, ArrayList<Integer> includeSubtaskList) {
        countId();
        TaskState state = getEpicState(includeSubtaskList);
        Epic epic = new Epic(counterId, name, description, state, includeSubtaskList);
        epicList.put(counterId, epic);
        if (includeSubtaskList != null){
            for (int subtaskId: includeSubtaskList){
                subtaskList.get(subtaskId).epicId = counterId;
            }
        }
        return epicList.get(counterId);
    }

    public static ArrayList <Subtask> getSubtaskListForEpic (int taskId) {
        ArrayList<Subtask> subtaskListForEpic = new ArrayList<>();
        for (Integer subtaskId : epicList.get(taskId).includeSubtaskList) {
            Subtask subtaskForEpic = subtaskList.get(subtaskId);
            subtaskListForEpic.add(subtaskForEpic);
        }
        return subtaskListForEpic;
    }

    public static HashMap<Integer, Task> getTaskList() {
        return taskList;
    }

    public static HashMap<Integer, Subtask> getSubtaskList() {
        return subtaskList;
    }

    public static HashMap<Integer, Epic> getEpicList() {
        return epicList;
    }

    public static void clearTaskList(){
        taskList.clear();
    }
    public static void clearSubtaskList(){
        for (Subtask subtask: subtaskList.values()){
            deleteConnectionWithSubtaskForEpic(subtask);
        }
        subtaskList.clear();
    }
    public static void clearEpicList(){
        for (Epic epic: epicList.values()){
            deleteSubtasksForEpic(epic);
        }
        epicList.clear();
    }

    public static Task getTask(int taskId){
        return taskList.get(taskId);
    }

    public static Subtask getSubtask(int taskId){
        return subtaskList.get(taskId);
    }

    public static Epic getEpic(int taskId){
        return epicList.get(taskId);
    }

    public static void deleteTask(int taskId){
        taskList.remove(taskId);
    }

    public static void deleteSubtask(int taskId){
        Subtask subtask = subtaskList.get(taskId);
        deleteConnectionWithSubtaskForEpic(subtask);
        subtaskList.remove(taskId);
    }

    public static void deleteEpic(int taskId){
        Epic epic = epicList.get(taskId);
        deleteSubtasksForEpic(epic);
        epicList.remove(taskId);
    }

    public static Task updateTask(int taskId, String name, String description, TaskState state){
        if (taskList.containsKey(taskId)){
            taskList.remove(taskId);
            Task task = new Task(taskId, name, description, state);
            taskList.put(taskId, task);
            return taskList.get(taskId);
        } else {
            return null;
        }
    }

    public static Subtask updateSubtask(int taskId, String name, String description, TaskState state, int epicId){
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

    public static Epic updateEpic(int taskId, String name, String description, ArrayList<Integer> includeSubtaskList){
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

    public static TaskState getEpicState(ArrayList <Integer> includeSubtaskList){
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

    public static void deleteConnectionWithSubtaskForEpic (Subtask subtask){
        int epicIdFromSubtask = subtask.epicId;
        ArrayList<Integer> arrayOfSubtasksForEpic = epicList.get(epicIdFromSubtask).includeSubtaskList;
        if (arrayOfSubtasksForEpic != null) {
            arrayOfSubtasksForEpic.remove((Integer) subtask.id);
        }
        updateEpic(epicIdFromSubtask, epicList.get(epicIdFromSubtask).name, epicList.get(epicIdFromSubtask).description,
                arrayOfSubtasksForEpic);
    }

    public static void deleteSubtasksForEpic(Epic epic){
        if (epic.includeSubtaskList != null){
            for (int subtaskId: epic.includeSubtaskList){
                subtaskList.remove(subtaskId);
            }
        }
    }
}
