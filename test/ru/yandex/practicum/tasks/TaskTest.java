package ru.yandex.practicum.tasks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.managers.Managers;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.tasks.TaskTest.taskManager;

class TaskTest {
    public static InMemoryTaskManager taskManager;
    @BeforeAll
    public static void beforeEach(){
        taskManager = Managers.getDefault();
    }

    @Test
    public void shouldWhenAddTaskWithUsedIdTaskIsRecord(){
        int taskId = 1;
        Task task1 = new Task("Task1", "DesctiptionTask1", TaskState.NEW);
        taskManager.taskList.put(taskId, task1);
        Task task2 = new Task("Task1.1", "DesctiptionTask1.1", TaskState.IN_PROGRESS);
        taskManager.taskList.put(taskId, task2);
        assertEquals(taskManager.taskList.get(taskId), task2, "Ошибка: task, полученный по id и task, " +
        "который добавлялся ранее с соответствующим id не равны");
    }
}