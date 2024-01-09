package ru.yandex.practicum.managers;

import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.managers.historymanager.InMemoryHistoryManager;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;

public class Managers {
    public static InMemoryTaskManager taskManaget;
    public static InMemoryHistoryManager historyManager;
    public static InMemoryTaskManager getDefault(){
        taskManaget = new InMemoryTaskManager();
        return taskManaget;
    }

    public static HistoryManager getDefaultHistory(){
        historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}
