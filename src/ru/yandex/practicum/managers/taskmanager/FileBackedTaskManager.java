package ru.yandex.practicum.managers.taskmanager;

import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.Subtask;
import ru.yandex.practicum.tasks.Task;
import ru.yandex.practicum.tasks.TaskState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager{

    private String managerFile = "managerFile.csv";

    public FileBackedTaskManager(HistoryManager historyManager) {
        super(historyManager);
    }

    private void save (){
        try (Writer fileWriter = new FileWriter(managerFile)){
            //fileWriter.write( + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task setTask(String name, String description, TaskState state) {
        Task newTask = super.setTask(name, description, state);
        save();
        return newTask;
    }

    @Override
    public Subtask setSubtask(String name, String description, TaskState state, int epicId) {
        Subtask subtask = super.setSubtask(name, description, state, epicId);
        save();
        return subtask;
    }

    @Override
    public Epic setEpic(String name, String description, ArrayList<Integer> includeSubtaskList) {
        Epic epic = super.setEpic(name, description, includeSubtaskList);
        save();
        return epic;
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
    }

    @Override
    public void deleteSubtask(int taskId) {
        super.deleteSubtask(taskId);
    }

    @Override
    public void deleteEpic(int taskId) {
        super.deleteEpic(taskId);
    }
}
