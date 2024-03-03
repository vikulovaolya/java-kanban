package ru.yandex.practicum.managers.historymanager;

import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    void update(Task task);

    ArrayList<Task> getHistory();
}
