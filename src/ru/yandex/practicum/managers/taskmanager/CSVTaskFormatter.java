package ru.yandex.practicum.managers.taskmanager;

import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.Subtask;
import ru.yandex.practicum.tasks.Task;
import ru.yandex.practicum.tasks.TaskState;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CSVTaskFormatter {

    public CSVTaskFormatter() {
    }

    public Task fromString(String value) { //id,type,name,status,description,epic
        String[] taskParametres  = value.split(",");
        int taskId = 0;
        try {
            taskId = parseInt(taskParametres[0]);
        } catch (NumberFormatException  exp) {
            exp.getMessage();
        }
        TaskType taskType = TaskType.valueOf(taskParametres[1]);
        String taskName = taskParametres[2];
        TaskState taskState = TaskState.valueOf(taskParametres[3]);
        String taskDescription = taskParametres[4];
        if (taskType.equals(TaskType.TASK)) {
            Task newTask = new Task(taskId, taskName, taskDescription, taskState);
            return newTask;
        } else if (taskParametres[1].equals("SUBTASK")) {
            int epicId = 0;
            try {
                epicId = parseInt(taskParametres[5]);
            } catch (NumberFormatException  exp) {
                exp.getMessage();
            }
            Subtask newSubtask = new Subtask(taskId, taskName, taskDescription, taskState, epicId);
            return newSubtask;
        } else if (taskParametres[1].equals("EPIC")) {
            Epic epic = new Epic(taskId, taskName, taskDescription, taskState, null);
            return epic;
        } else {
            return null;
        }
    }

    public String toString(Task task) { //id,type,name,status,description,epic
        int epicId = 0;
        TaskType taskType = task.getType();
        String taskStringType = taskType.toString();
        TaskState taskState = task.getState();
        String taskStringState = taskState.toString();
        if (taskType.equals(TaskType.SUBTASK)) {
            Subtask subtask = (Subtask) task;
            epicId = subtask.getEpicId();
        }
         String taskString = task.getId() + "," + taskStringType + "," + task.getName() + "," + taskStringState + ","
                 + task.getDescription() + ",";
        if (epicId != 0) {
            taskString = taskString + epicId;
        }
         return taskString;
    }

    public ArrayList<Integer> historyFromString(String str) {
        String historyStr = str.substring(1, str.length());
        String [] historyStrSplit = historyStr.split(",");
        ArrayList<Integer> historyIdTasks = new ArrayList<>();
        for (int i = 0; i < historyStrSplit.length; i++) {
            int taskId = Integer.parseInt(historyStrSplit[i]);
            historyIdTasks.add(taskId);
        }
        return historyIdTasks;
    }
}
