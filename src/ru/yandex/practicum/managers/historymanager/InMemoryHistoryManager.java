package ru.yandex.practicum.managers.historymanager;

import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    protected LinkedList<Task> taskHistoryList = new LinkedList<>();
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
