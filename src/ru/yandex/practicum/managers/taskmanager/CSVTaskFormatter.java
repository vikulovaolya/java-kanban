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
        TaskState taskState;
        try {
            taskId = parseInt(taskParametres[0]);
        } catch (NumberFormatException  exp) {
            exp.getMessage();
        }
        if (taskParametres[3].equals("NEW")) {
            taskState = TaskState.NEW;
        } else if (taskParametres[3].equals("IN_PROGRESS")) {
            taskState = TaskState.IN_PROGRESS;
        } else if (taskParametres[3].equals("DONE")) {
            taskState = TaskState.DONE;
        } else {
            taskState = null;
        }
        if (taskParametres[1].equals("TASK")) {
            Task newTask = new Task(taskId, taskParametres[2], taskParametres[4], taskState);
            return newTask;
        } else if (taskParametres[1].equals("SUBTASK")) {
            int epicId = 0;
            try {
                epicId = parseInt(taskParametres[5]);
            } catch (NumberFormatException  exp) {
                exp.getMessage();
            }
            Subtask newSubtask = new Subtask(taskId, taskParametres[2], taskParametres[4], taskState, epicId);
            return newSubtask;
        } else if (taskParametres[1].equals("EPIC")) {
            Epic epic = new Epic(taskId, taskParametres[2], taskParametres[4], taskState, null);
            return epic;
        } else {
            return null;
        }
    }

    public String toString(Task task) { //id,type,name,status,description,epic
        TaskType taskType = task.getType();
        String taskStringType = null;
        int epicId = 0;
        TaskState taskState = task.getState();
        String taskStringState = null;
        if (taskType.equals(TaskType.TASK)) {
            taskStringType = "TASK";
        } else if (taskType.equals(TaskType.SUBTASK)) {
            taskStringType = "SUBTASK";
            Subtask subtask = (Subtask) task;
            epicId = subtask.getEpicId();
        } else if (taskType.equals(TaskType.EPIC)) {
            taskStringType = "EPIC";
        }
        if (taskState.equals(taskState.NEW)) {
            taskStringState = "NEW";
        } else if (taskState.equals(taskState.IN_PROGRESS)) {
            taskStringState = "IN_PROGRESS";
        } else if (taskState.equals(taskState.DONE)) {
            taskStringState = "DONE";
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
