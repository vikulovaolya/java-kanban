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
        int taskId = 4;
        ArrayList<Integer> includeSubtaskList1 = new ArrayList<>();
        includeSubtaskList1.add(1);
        includeSubtaskList1.add(2);
        ArrayList<Integer> includeSubtaskList2 = new ArrayList<>();
        includeSubtaskList2.add(3);
        Epic epic1 = new Epic("Epic1", "DesctiptionEpic1", TaskState.NEW, includeSubtaskList1);
        Epic epic2 = new Epic("Epic1.1", "DesctiptionEpic1.1", TaskState.DONE, includeSubtaskList2);
        taskManager.taskList.put(taskId, epic1);
        taskManager.taskList.put(taskId, epic2);
        assertEquals(taskManager.taskList.get(taskId), epic2, "Ошибка: epic, полученный по id и epic, " +
                "который добавлялся ранее с соответствующим id не равны");
    }
}