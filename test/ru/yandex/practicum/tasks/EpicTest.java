package ru.yandex.practicum.tasks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.managers.Managers;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    public static InMemoryTaskManager taskManager;

    @BeforeAll
    public static void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldWhenAddTaskWithUsedIdTaskIsRecord() {
        ArrayList<Integer> includeSubtaskList1 = new ArrayList<>();
        includeSubtaskList1.add(1);
        includeSubtaskList1.add(2);
        ArrayList<Integer> includeSubtaskList2 = new ArrayList<>();
        includeSubtaskList2.add(3);
        Epic epic = new Epic(4, "Epic1", "DesctiptionEpic1", TaskState.NEW, includeSubtaskList1);
        Epic savedEpic = new Epic(4, "Epic1.1", "DesctiptionEpic1.1", TaskState.DONE, includeSubtaskList2);
        taskManager.taskList.put(4, epic);
        taskManager.taskList.put(4, savedEpic);
        assertNotNull(taskManager.taskList.get(4), "Ошибка: созданный epic не найден");
        assertNotNull(taskManager.taskList, "Ошибка: в хеш-таблице не сохранено добавленное значение epic");
        assertEquals(1, taskManager.taskList.size(), "Ошибка: неверное количество задач в хеш-таблице epic.");
        assertEquals(taskManager.taskList.get(4), savedEpic, "Ошибка: epic, полученный по id и epic, " +
                "который добавлялся ранее с соответствующим id не равны");
    }
}