package ru.yandex.practicum.managers.historymanager;

import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    protected ArrayList<Task> taskHistoryList = new ArrayList<>();
    @Override
    public ArrayList<Task> getHistory(){
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
