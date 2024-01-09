package ru.yandex.practicum.taskmanager;

import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    public List<Task> taskHistoryList = new ArrayList<>();
    @Override
    public List<Task> getHistory(){
        return taskHistoryList;
    }

    @Override
    public void add(Task task){
        if (taskHistoryList.size() == 10){
            taskHistoryList.remove(0);

        }
        taskHistoryList.add(task);
    }
}
