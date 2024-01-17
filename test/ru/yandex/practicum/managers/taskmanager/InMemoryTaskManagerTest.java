package ru.yandex.practicum.managers.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.managers.historymanager.InMemoryHistoryManager;
import ru.yandex.practicum.tasks.TaskState;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class InMemoryTaskManagerTest {
    public static InMemoryTaskManager taskManager;
    public static HistoryManager historyManager;
    @BeforeEach
    public void beforeEach(){
        historyManager = new InMemoryHistoryManager();
        taskManager = new InMemoryTaskManager(historyManager);
    }

    @Test
    public void shouldEpicCannotReferToItselfInEpicId(){
        ArrayList<Integer> includeSubtasks = new ArrayList<>();
        includeSubtasks.add(1);
        includeSubtasks.add(2);
        assertNull(taskManager.setEpic("Epic", "DescriptionEpic", includeSubtasks), "Ошибка" +
                ": Удалось создать epic, который в листе подзадач ссылается на самого себя");
    }

    @Test
    public void shouldSubtaskDoesNotContainItsOwnIdAsAnEpic(){
        assertNull(taskManager.setSubtask("Subtask", "DescriptionSubtask", TaskState.NEW, 1),
                "Ошибка: Удалось создать subtask, который по epicId ссылается на самого себя");
    }

    @Test
    public void shouldCreateEndFindTaskObject(){
        taskManager.setTask("Task", "DescriptionTask", TaskState.NEW);
        assertNotNull(taskManager.getTask(1), "Ошибка: не удалось получить ранее " +
                "созданный task");
    }

    @Test
    public void shouldCreateEndFindSubtaskObject(){
        taskManager.setEpic("Epic", "DescriptionEpic", null);
        taskManager.setSubtask("Subtask", "DescriptionSubtask", TaskState.NEW, 1);
        assertNotNull(taskManager.getSubtask(2), "Ошибка: не удалось получить ранее " +
                "созданный subtask");
    }

    @Test
    public void shouldCreateEndFindEpicObject(){
        taskManager.setEpic("Epic", "DescriptionEpic", null);
        assertNotNull(taskManager.getEpic(1), "Ошибка: не удалось получить ранее " +
                "созданный epic");
    }
}