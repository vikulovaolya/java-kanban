package ru.yandex.practicum.managers.historymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;
import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.Subtask;
import ru.yandex.practicum.tasks.Task;
import ru.yandex.practicum.tasks.TaskState;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.min;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {
    public static InMemoryTaskManager testTaskManager;
    public static HistoryManager testHistoryManager;

    @BeforeEach
    public void beforeEach(){
        testHistoryManager = new InMemoryHistoryManager();
        testTaskManager = new InMemoryTaskManager(testHistoryManager);
    }

    @Test
    public void addHistoryRecord(){
        Task task = new Task(1, "Task1", "DesctiptionTask1", TaskState.NEW);
        testHistoryManager.add(task);
        final List<Task> history = testHistoryManager.getHistory();
        assertNotNull(history, "Ошибка: история пустая.");
        assertEquals(1, history.size(), "Ошибка: неверное количество записей в истории запросов task");
    }

    @Test
    public void shouldSaveHistoryRecord(){
        testTaskManager.setEpic("Epic", "DescriptionEpic", null);
        testTaskManager.setSubtask("Subtask", "DescriptionSubtask", TaskState.NEW, 1);
        Subtask subtask = testTaskManager.getSubtask(2);
        Epic epic = testTaskManager.getEpic(1);
        ArrayList<Task> newTaskHistoryList = new ArrayList<>();
        newTaskHistoryList.add(subtask);
        newTaskHistoryList.add(epic);
        ArrayList<Task> taskHistoryList =  testTaskManager.historyManager.getHistory();
        assertEquals((Integer)newTaskHistoryList.size(), (Integer)taskHistoryList.size(), "Ошибка: заявленный и ожидаемый " +
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
        // fix-return
    }
}