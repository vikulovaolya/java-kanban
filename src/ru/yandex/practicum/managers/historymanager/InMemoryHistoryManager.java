package ru.yandex.practicum.managers.historymanager;

import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    protected ArrayList<Task> taskHistoryList = new ArrayList<>();

    protected HashMap<Integer, Task> taskHistoryMap = new HashMap<>();

    Task firstTaskInHistory;

    Task lastTaskInHistory;
    @Override
    public ArrayList<Task> getHistory(){
        getTasks();
        return taskHistoryList;
    }

    @Override
    public void add(Task task){
        if (taskHistoryMap.size() == 0){
            firstTaskInHistory = task;
            lastTaskInHistory = task;
        } else {
            int taskId = task.getId();
            if (taskHistoryMap.containsKey(taskId)){
                remove(taskId);
            }
            linkLast(task);
        }
    }

    void linkLast (Task task){
        Task oldNextTask = lastTaskInHistory;
        oldNextTask.setNext(task);
        lastTaskInHistory = task;
        lastTaskInHistory.setPrev(oldNextTask);
        int taskId = task.getId();
        taskHistoryMap.put(taskId,task);
    }

    public void remove(int id){
        Task task = taskHistoryMap.get(id);
        Task prevTask = task.getPrev();
        Task nextTask = task.getNext();
        prevTask.setNext(nextTask);
        nextTask.setPrev(prevTask);
    }

    public void getTasks(){
        taskHistoryList.clear();
        Task task = firstTaskInHistory;
        while (task.getNext() != null){
            taskHistoryList.add(task);
            task = task.getNext();
        }
    }
}
