package ru.yandex.practicum.managers;

import ru.yandex.practicum.managers.historymanager.InMemoryHistoryManager;
import ru.yandex.practicum.managers.taskmanager.FileBackedTaskManager;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;

import java.io.File;

public class Managers {
    public static FileBackedTaskManager taskManaget;
    public static InMemoryHistoryManager historyManager;

    public static InMemoryTaskManager getDefault() {
        if (taskManaget == null) {
            InMemoryHistoryManager historyManager = getDefaultHistory();
            taskManaget = new FileBackedTaskManager(historyManager, new File("resources\\tasks.csv")); //InMemoryTaskManager(historyManager);
            taskManaget.loadFromFile(taskManaget.getManagerFile());
        }
        return taskManaget;
    }

    public static InMemoryHistoryManager getDefaultHistory() {
        if (historyManager == null) {
            historyManager = new InMemoryHistoryManager();
        }
        return historyManager;
    }


}
