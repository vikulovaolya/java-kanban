package ru.yandex.practicum.taskmanager;

public class Managers {
    public static InMemoryTaskManager taskManaget;
    public static InMemoryTaskManager getDefault(){
        taskManaget = new InMemoryTaskManager();
        return taskManaget;
    }
}
