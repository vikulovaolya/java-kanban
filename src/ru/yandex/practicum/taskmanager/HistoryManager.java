package ru.yandex.practicum.taskmanager;

import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    void add(Task task);
    List<Task> getHistory();
}
