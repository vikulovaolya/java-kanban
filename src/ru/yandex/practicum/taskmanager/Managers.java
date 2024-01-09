package ru.yandex.practicum.taskmanager;

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
