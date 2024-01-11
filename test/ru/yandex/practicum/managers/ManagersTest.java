package ru.yandex.practicum.managers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.managers.historymanager.InMemoryHistoryManager;
import ru.yandex.practicum.managers.taskmanager.InMemoryTaskManager;
import ru.yandex.practicum.managers.taskmanager.TaskManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.yandex.practicum.managers.Managers.*;

class ManagersTest {
    @Test
    public void shouldCreateDefaultTaskManager(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "Ошибка: Создается taskManager = null");
    }

    @Test
    public void shouldCreateDefaultHistoryManager(){
        InMemoryHistoryManager historyManager = getDefaultHistory();
        assertNotNull(historyManager, "Ошибка: Создается historyManager = null");
    }

}