package ru.yandex.practicum.managers.historymanager;

import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    protected List<Task> taskHistoryList = new ArrayList<>();
    /* Модификатор доступа поправила, благодарю. А последовательность добавлления элементов в историю я бы могла
    исправить, но думаю лучшше не стоит править учитывая что такой подход был описан в ТЗ, чтобы в
    последующих заданиях править это не пришлось */
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
