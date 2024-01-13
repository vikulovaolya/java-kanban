package ru.yandex.practicum.managers.historymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.managers.Managers;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;
import ru.yandex.practicum.tasks.Task;
import ru.yandex.practicum.tasks.TaskState;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions.*;

import static java.lang.Integer.min;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.managers.Managers.historyManager;

class InMemoryHistoryManagerTest {
    public static InMemoryTaskManager taskManager;

    @BeforeEach
    public void beforeEach(){
        taskManager = Managers.getDefault();
    }

    @Test
    public void addHistoryRecord(){
        Task task = new Task("Task1", "DesctiptionTask1", TaskState.NEW);
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "Ошибка: история пустая.");
        assertEquals(1, history.size(), "Ошибка: неверное количество записей в истории запросов task");
    }

    @Test
    public void shouldSaveHistoryRecord(){
        taskManager.setEpic("Epic", "DescriptionEpic", null);
        taskManager.setSubtask("Subtask", "DescriptionSubtask", TaskState.NEW, 1);
        taskManager.getSubtask(2, true);
        taskManager.getEpic(1, true);
        List<Task> newTaskHistoryList = new ArrayList<>();
        newTaskHistoryList.add(taskManager.getSubtask(2, false));
        newTaskHistoryList.add(taskManager.getEpic(1, false));
        List<Task> taskHistoryList = taskManager.historyManager.getHistory();
        assertEquals(newTaskHistoryList.size(), taskHistoryList.size(), "Ошибка: заявленный и ожидаемый " +
                "массивы е совпадают по размеру");
        boolean isEqualsArraysValue = true;
        int quantityCheckElements = min (newTaskHistoryList.size(), taskHistoryList.size());
        if (quantityCheckElements != 0){
            for (int i = 0; i < quantityCheckElements; i++){
                if (newTaskHistoryList.get(i) != taskHistoryList.get(i)){
                    isEqualsArraysValue = false;
                }
            }
        }
        assertEquals(isEqualsArraysValue, true, "Ошибка: не совпадают значения элементов массивов");
    }
}