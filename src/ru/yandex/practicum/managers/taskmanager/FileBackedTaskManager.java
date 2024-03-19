package ru.yandex.practicum.managers.taskmanager;

import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.Subtask;
import ru.yandex.practicum.tasks.Task;
import ru.yandex.practicum.tasks.TaskState;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File managerFile;
    private static CSVTaskFormatter csvTaskFormatted = new CSVTaskFormatter();

    public FileBackedTaskManager(HistoryManager historyManager, File managerFile) {
        super(historyManager);
        this.managerFile = managerFile;
    }

    public void loadFromFile(File managerFileName) {
        if (managerFile.exists() && !managerFile.isDirectory()) {
            int numberOfLine = 0;
            try (FileReader reader = new FileReader(managerFileName); BufferedReader br = new BufferedReader(reader)) {
                while (br.ready()) {
                    String line = br.readLine();
                    if (numberOfLine != 0) {
                        if (!line.equals("")) {
                            String firstSymbolStr = String.valueOf(line.charAt(0));
                            if (!firstSymbolStr.equals("#")) {
                                Task task = csvTaskFormatted.fromString(line);
                                int taskId = task.getId();
                                if (task != null) {
                                    if (task.getType().equals(TaskType.TASK)) {
                                        taskList.put(taskId, task);
                                    } else if (task.getType().equals(TaskType.SUBTASK)) {
                                        subtaskList.put(taskId, (Subtask) task);
                                    } else if (task.getType().equals(TaskType.EPIC)) {
                                        epicList.put(taskId, (Epic) task);
                                    }
                                }
                            } else {
                                ArrayList<Integer> historyIdTasks = csvTaskFormatted.historyFromString(line);
                                for (Integer taskId: historyIdTasks) {
                                    if (taskList.containsKey(taskId)) {
                                        Task task = taskList.get(taskId);
                                        historyManager.add(task);
                                    } else if (subtaskList.containsKey(taskId)) {
                                        Subtask subtask = subtaskList.get(taskId);
                                        historyManager.add(subtask);
                                    } else if (epicList.containsKey(taskId)) {
                                        Epic epic = epicList.get(taskId);
                                        historyManager.add(epic);
                                    }
                                }
                            }
                        }


                    }
                    numberOfLine++;
                }
            } catch (IOException e) {
                System.out.println("Произошла ошибка во время чтения файла.");
            }
        }
    }

    private void save() {
        if (!(managerFile.exists() && !managerFile.isDirectory())) {
            managerFile = new File("resources\\tasks.csv");
        }
        try (Writer fileWriter = new FileWriter(managerFile, StandardCharsets.UTF_8)) {
            fileWriter.write("id,type,name,status,description,epic\n");
            if (!taskList.isEmpty()) {
                for (Task task : taskList.values()) {
                    fileWriter.write(csvTaskFormatted.toString(task) + "\n");
                }
            }
            if (!subtaskList.isEmpty()) {
                for (Subtask task : subtaskList.values()) {
                    fileWriter.write(csvTaskFormatted.toString(task) + "\n");
                }
            }
            if (!epicList.isEmpty()) {
                for (Epic task : epicList.values()) {
                    fileWriter.write(csvTaskFormatted.toString(task) + "\n");
                }
            }
            fileWriter.write("#");
            ArrayList<Task> historyList = getHistory();
            for (Task task: historyList) {
                if (task != historyList.get(0)) {
                    fileWriter.write(",");
                }
                int taskId = task.getId();
                fileWriter.write(Integer.toString(taskId));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task setTask(String name, String description, TaskState state) {
        final Task newTask = super.setTask(name, description, state);
        save();
        return newTask;
    }

    @Override
    public Subtask setSubtask(String name, String description, TaskState state, int epicId) {
        final Subtask subtask = super.setSubtask(name, description, state, epicId);
        save();
        return subtask;
    }

    @Override
    public Epic setEpic(String name, String description, ArrayList<Integer> includeSubtaskList) {
        final Epic epic = super.setEpic(name, description, includeSubtaskList);
        save();
        return epic;
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteSubtask(int taskId) {
        super.deleteSubtask(taskId);
        save();
    }

    @Override
    public void deleteEpic(int taskId) {
        super.deleteEpic(taskId);
        save();
    }

    @Override
    public void clearTaskList() {
        super.clearTaskList();
        save();
    }

    @Override
    public void clearSubtaskList() {
        super.clearSubtaskList();
        save();
    }

    @Override
    public void clearEpicList() {
        super.clearEpicList();
        save();
    }

    @Override
    public Task getTask(int taskId) {
        final Task task = super.getTask(taskId);
        save();
        return task;
    }

    @Override
    public Subtask getSubtask(int taskId) {
        final Subtask subtask = super.getSubtask(taskId);
        save();
        return subtask;
    }

    @Override
    public Epic getEpic(int taskId) {
        final Epic epic = super.getEpic(taskId);
        save();
        return epic;
    }

    @Override
    public Task updateTask(int taskId, Task taskWithChanges) {
        final Task task = super.updateTask(taskId, taskWithChanges);
        save();
        return task;
    }

    @Override
    public Subtask updateSubtask(int taskId, Subtask subtaskWithChanges) {
        final Subtask subtask = super.updateSubtask(taskId, subtaskWithChanges);
        save();
        return subtask;
    }

    @Override
    public Epic updateEpic(int taskId, Epic epicWithChanges) {
        final Epic epic = super.updateEpic(taskId, epicWithChanges);
        save();
        return epic;
    }

    public File getManagerFile() {
        return managerFile;
    }
}

