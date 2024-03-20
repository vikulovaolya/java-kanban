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
        taskManager = Managers.getDefault("х");
    }

    @Test
    public void shouldWhenAddTaskWithUsedIdTaskIsRecord(){
        Task task = new Task(1, "Task1", "DesctiptionTask1", TaskState.NEW);
        taskManager.taskList.put(1, task);
        assertNotNull(taskManager.taskList.get(1), "Ошибка: созданный task не найден");
        assertNotNull(taskManager.taskList, "Ошибка: в хеш-таблице не сохранено добавленное значение task");
        assertEquals(1, taskManager.taskList.size(), "Ошибка: неверное количество задач в " +
                "хеш-таблице task");
        Task savedTask = new Task(1, "Task1.1", "DesctiptionTask1.1", TaskState.IN_PROGRESS);
        taskManager.taskList.put(1, savedTask);
        assertEquals(taskManager.taskList.get(1), savedTask, "Ошибка: task, полученный по id и task, " +
        "который добавлялся ранее с соответствующим id не равны");
    }
}