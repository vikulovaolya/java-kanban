package ru.yandex.practicum.taskmanager;

import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.Subtask;
import ru.yandex.practicum.tasks.Task;
import ru.yandex.practicum.tasks.TaskState;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager  {
    Task setTask(String name, String description, TaskState state);

    Subtask setSubtask(String name, String description, TaskState state, int epicId);

    Epic setEpic(String name, String description, ArrayList<Integer> includeSubtaskList);

    ArrayList<Subtask> getSubtaskListForEpic(int taskId);

    HashMap<Integer, Task> getTaskList();

    HashMap<Integer, Subtask> getSubtaskList();

    HashMap<Integer, Epic> getEpicList();

    void clearTaskList();

    void clearSubtaskList();

    void clearEpicList();

    Task getTask(int taskId);

    Subtask getSubtask(int taskId);

    Epic getEpic(int taskId);

    void deleteTask(int taskId);

    void deleteSubtask(int taskId);

    void deleteEpic(int taskId);

    Task updateTask(int taskId, Task taskWithChanges);

    Subtask updateSubtask(int taskId, Subtask subtaskWithChanges);

    Epic updateEpic(int taskId, Epic epicWithChanges);
}
