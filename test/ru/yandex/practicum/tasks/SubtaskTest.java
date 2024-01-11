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
    @Test
    public void shouldWhenAddTaskWithUsedIdTaskIsRecord(){
        Subtask subtask1 = new Subtask("Subtask1", "DesctiptionSubtask1", TaskState.NEW, 1);
        taskManager.taskList.put(1, subtask1);
        Subtask subtask2 = new Subtask("Subtask1.1", "DesctiptionSubtask1.1", TaskState.IN_PROGRESS, 2);
        taskManager.taskList.put(1, subtask2);
        assertEquals(taskManager.taskList.get(1), subtask2, "Ошибка: subtask, полученный по id и subtask, " +
                "который добавлялся ранее с соответствующим id не равны");
    }
}