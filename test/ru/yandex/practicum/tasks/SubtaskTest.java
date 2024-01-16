package ru.yandex.practicum.tasks;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.managers.Managers;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.tasks.TaskTest.taskManager;


class SubtaskTest {
    public static InMemoryTaskManager taskManager;
    @BeforeAll
    public static void beforeEach(){
        taskManager = Managers.getDefault();
    }

    /*
    @Test
    public void shouldWhenAddTaskWithUsedIdTaskIsRecord(){
        Subtask subtask = new Subtask("Subtask1", "DesctiptionSubtask1", TaskState.NEW, 1);
        taskManager.taskList.put(1, subtask);
        Subtask savedSubtask = new Subtask("Subtask1.1", "DesctiptionSubtask1.1", TaskState.IN_PROGRESS, 2);
        taskManager.taskList.put(1, savedSubtask);
        assertNotNull(taskManager.taskList.get(1), "Ошибка: созданный subtask не найден");
        assertNotNull(taskManager.taskList, "Ошибка: в хеш-таблице не сохранено добавленное значение subtask");
        assertEquals(1, taskManager.taskList.size(), "Ошибка: неверное количество задач в хеш-таблице subtask.");
        assertEquals(taskManager.taskList.get(1), savedSubtask, "Ошибка: subtask, полученный по id и subtask, " +
                "который добавлялся ранее с соответствующим id не равны");
    } */
}