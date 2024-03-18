package ru.yandex.practicum.managers.taskmanager;

import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.Subtask;
import ru.yandex.practicum.tasks.Task;
import ru.yandex.practicum.tasks.TaskState;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    public HistoryManager historyManager;
    public int counterId = 100;
    public HashMap<Integer, Task> taskList = new HashMap<>();
    public HashMap<Integer, Subtask> subtaskList = new HashMap<>();
    public HashMap<Integer, Epic> epicList = new HashMap<>();

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public Task setTask(String name, String description, TaskState state) {
        int taskId = getCountId();
        Task task = new Task(taskId, name, description, state);
        taskList.put(taskId, task);
        return taskList.get(taskId);
    }

    @Override
    public Subtask setSubtask(String name, String description, TaskState state, int epicId) {
        int taskId = getCountId();
        Subtask subtask = new Subtask(taskId, name, description, state, epicId);
        if (taskId == epicId) {
            return null;
        }
        subtaskList.put(taskId, subtask);
        ArrayList<Integer> arrayOfSubtasksForEpic = epicList.get(epicId).getIncludeSubtaskList();

        if (arrayOfSubtasksForEpic != null) {
            arrayOfSubtasksForEpic.add(taskId);
        } else {
            ArrayList<Integer> newArrayOfSubtasksForEpic = new ArrayList<>();
            newArrayOfSubtasksForEpic.add(taskId);
            epicList.get(epicId).setIncludeSubtaskList(newArrayOfSubtasksForEpic);
        }
        updateEpic(epicId, epicList.get(epicId));
        return subtaskList.get(taskId);
    }

    @Override
    public Epic setEpic(String name, String description, ArrayList<Integer> includeSubtaskList) {
        int taskId = getCountId();
        if (includeSubtaskList != null) {
            for (Integer subtask: includeSubtaskList) {
                if (subtask == taskId) {
                    return null;
                }
            }
        }
        TaskState state = getEpicState(includeSubtaskList);
        Epic epic = new Epic(taskId, name, description, state, includeSubtaskList);
        epicList.put(taskId, epic);
        if (includeSubtaskList != null) {
            for (int subtaskId : includeSubtaskList) {
                subtaskList.get(subtaskId).setEpicId(taskId);
            }
        }
        return epicList.get(taskId);
    }

    @Override
    public ArrayList<Subtask> getSubtaskListForEpic(int taskId) {
        ArrayList<Subtask> subtaskListForEpic = new ArrayList<>();
        for (Integer subtaskId : epicList.get(taskId).getIncludeSubtaskList()) {
           Subtask subtaskForEpic = subtaskList.get(subtaskId);
            subtaskListForEpic.add(subtaskForEpic);
        }
        return subtaskListForEpic;
    }

    @Override
    public ArrayList<Task> getTaskList() {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        for (Task task: taskList.values()) {
            taskArrayList.add(task);
        }
        return taskArrayList;
    }

    @Override
    public ArrayList<Subtask> getSubtaskList() {
        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();
        for (Subtask subtask: subtaskList.values()) {
            subtaskArrayList.add(subtask);
        }
        return subtaskArrayList;
    }

    @Override
    public ArrayList<Epic> getEpicList() {
        ArrayList<Epic> epicArrayList = new ArrayList<>();
        for (Epic epic: epicList.values()) {
            epicArrayList.add(epic);
        }
        return epicArrayList;
    }

    @Override
    public void clearTaskList() {
        for (Integer taskId: taskList.keySet()) {
            historyManager.remove(taskId);
        }
        taskList.clear();
    }

    @Override
    public void clearSubtaskList() {
        for (Integer subtaskId : subtaskList.keySet()) {
            historyManager.remove(subtaskId);
            deleteConnectionWithSubtaskForEpic(subtaskId, subtaskList.get(subtaskId).getEpicId());
        }
        subtaskList.clear();
    }

    @Override
    public void clearEpicList() {
        for (Epic epic : epicList.values()) {
            Integer epicId = epic.getId();
            historyManager.remove(epicId);
            deleteSubtasksForEpic(epic);
        }
        epicList.clear();
    }

    @Override
    public Task getTask(int taskId) {
        Task taskObject = taskList.get(taskId);
        if (taskObject != null) {
            historyManager.add(taskObject);
        }
        return taskObject;
    }

    @Override
    public Subtask getSubtask(int taskId) {
        Subtask subtaskObject = subtaskList.get(taskId);
        if (subtaskObject != null) {
            historyManager.add(subtaskObject);
        }
        return subtaskObject;
    }

    @Override
    public Epic getEpic(int taskId) {
        Epic epicObject = epicList.get(taskId);
        if (epicObject != null) {
            historyManager.add(epicObject);
        }
        return epicObject;
    }

    @Override
    public void deleteTask(int taskId) {
        taskList.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteSubtask(int taskId) {
        Subtask subtask = subtaskList.get(taskId);
        deleteConnectionWithSubtaskForEpic(taskId, subtask.getEpicId());
        subtaskList.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteEpic(int taskId) {
        Epic epic = epicList.get(taskId);
        deleteSubtasksForEpic(epic);
        epicList.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public Task updateTask(int taskId, Task taskWithChanges) {
        if (taskList.containsKey(taskId)) {
            taskList.remove(taskId);
            taskList.put(taskId, taskWithChanges);
            return taskList.get(taskId);
        } else {
            return null;
        }
    }

    @Override
    public Subtask updateSubtask(int taskId, Subtask subtaskWithChanges) {
        if (subtaskList.containsKey(taskId)) {
            Subtask oldVersionOfSubtask = subtaskList.get(taskId);
            int oldEpicId = oldVersionOfSubtask.getEpicId();
            Epic oldEpic = epicList.get(oldEpicId);
            if (subtaskWithChanges.getEpicId() != oldEpicId) {
                epicList.get(subtaskWithChanges.getEpicId()).getIncludeSubtaskList().add(taskId);
                ArrayList<Integer> oldEpicSubtaskList = epicList.get(oldEpicId).getIncludeSubtaskList();
                oldEpicSubtaskList.remove((Integer) taskId);
                oldEpic.setIncludeSubtaskList(oldEpicSubtaskList);
                updateEpic(oldEpicId, oldEpic);
            }
            subtaskList.remove(taskId);
            subtaskList.put(taskId, subtaskWithChanges);
            updateEpic(subtaskWithChanges.getEpicId(), epicList.get(subtaskWithChanges.getEpicId()));
            return subtaskList.get(taskId);
        } else {
            return null;
        }
    }

    @Override
    public Epic updateEpic(int taskId, Epic epicWithChanges) {
        if (epicList.containsKey(taskId)) {
            epicWithChanges.setState(getEpicState(epicWithChanges.getIncludeSubtaskList()));
            if (epicWithChanges.getIncludeSubtaskList() != epicList.get(taskId).getIncludeSubtaskList()) {
                if (epicWithChanges.getIncludeSubtaskList() != null) {
                    for (int subtaskId : epicWithChanges.getIncludeSubtaskList()) {
                        subtaskList.get(subtaskId).setEpicId(taskId);
                    }
                }
            }
            epicList.remove(taskId);
            epicList.put(taskId, epicWithChanges);
            return epicList.get(taskId);
        } else {
            return null;
        }
    }

    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }



    private int getCountId() {
        counterId += 1;
        return counterId;
    }

    private TaskState getEpicState(ArrayList<Integer> includeSubtaskList) {
        TaskState state = null;
        if (includeSubtaskList == null) {
            state = TaskState.NEW;
        } else {
            int newStateSubtasks = 0;
            int doneStateSubtasks = 0;
            for (Integer subtaskId : includeSubtaskList) {
                if (subtaskList.get(subtaskId) != null) {
                    if (subtaskList.get(subtaskId).getState() == TaskState.NEW) {
                        newStateSubtasks += 1;
                    } else if (subtaskList.get(subtaskId).getState() == TaskState.DONE) {
                        doneStateSubtasks += 1;
                    }
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

    private void deleteConnectionWithSubtaskForEpic(Integer subtaskId, int epicId) {
        ArrayList<Integer> arrayOfSubtasksForEpic = epicList.get(epicId).getIncludeSubtaskList();
        if (arrayOfSubtasksForEpic != null) {
            arrayOfSubtasksForEpic.remove(subtaskId);
        }
        updateEpic(epicId, epicList.get(epicId));
    }

    private void deleteSubtasksForEpic(Epic epic) {
        if (epic.getIncludeSubtaskList() != null) {
            for (int subtaskId : epic.getIncludeSubtaskList()) {
                historyManager.remove(subtaskId);
                subtaskList.remove(subtaskId);
            }
        }
    }
}
