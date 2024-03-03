package ru.yandex.practicum.managers;

import ru.yandex.practicum.managers.historymanager.InMemoryHistoryManager;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;

public class Managers {
    public static InMemoryTaskManager taskManaget;
    public static InMemoryHistoryManager historyManager;

    public static InMemoryTaskManager getDefault() {
        if (taskManaget == null) {
            InMemoryHistoryManager historyManager = getDefaultHistory();
            taskManaget = new InMemoryTaskManager(historyManager);

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
