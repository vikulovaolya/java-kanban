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
import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    public void historyListIsEmptyAfterClearAllTaskLists (){
        testTaskManager.setTask("Task1", "Task1Description", TaskState.NEW);
        testTaskManager.setTask("Task2", "Task2Description", TaskState.IN_PROGRESS);
        testTaskManager.setEpic("Epic", "DescriptionEpic", null);
        testTaskManager.setSubtask("Subtask", "DescriptionSubtask", TaskState.NEW, 3);
        Task task1 = testTaskManager.getTask(1);
        Task task2 = testTaskManager.getTask(2);
        Subtask subtask = testTaskManager.getSubtask(3);
        Epic epic = testTaskManager.getEpic(4);
        testTaskManager.clearTaskList();
        testTaskManager.clearEpicList();
        final List<Task> history = testHistoryManager.getHistory();
        assertNull(history, "Ошибка: после очистки всех списков история оказалась не пуста");
    }

    @Test
    public void historyListIsEmptyAfterDeleteAllTasks (){
        testTaskManager.setTask("Task1", "Task1Description", TaskState.NEW);
        testTaskManager.setTask("Task2", "Task2Description", TaskState.IN_PROGRESS);
        testTaskManager.setEpic("Epic", "DescriptionEpic", null);
        testTaskManager.setSubtask("Subtask", "DescriptionSubtask", TaskState.NEW, 3);
        Task task1 = testTaskManager.getTask(1);
        Task task2 = testTaskManager.getTask(2);
        Subtask subtask = testTaskManager.getSubtask(3);
        Epic epic = testTaskManager.getEpic(4);
        testTaskManager.deleteTask(1);
        testTaskManager.deleteTask(2);
        testTaskManager.deleteEpic(3);
        final List<Task> history = testHistoryManager.getHistory();
        assertNull(history, "Ошибка: после удаления всех задач история оказалась не пуста");
    }

    @Test
    public void historyListDoesNotContainDuplicates (){
        testTaskManager.setTask("Task1", "Task1Description", TaskState.NEW);
        Task task1 = testTaskManager.getTask(1);
        testTaskManager.getTask(1);
        testTaskManager.getTask(1);
        final List<Task> history = testHistoryManager.getHistory();
        assertEquals(1, history.size(), "Ошибка: При нескольких запросах на получение такски в истории она зафиксирована некорректное количество раз");
    }

    @Test
    public void rightTaskInNodeAfterUpdateTask(){
        testTaskManager.setTask("Task1", "Task1Description", TaskState.NEW);
        testTaskManager.setEpic("Epic", "DescriptionEpic", null);
        testTaskManager.setSubtask("Subtask", "DescriptionSubtask", TaskState.NEW, 2);
        Task task = testTaskManager.getTask(1);
        Subtask subtask = testTaskManager.getSubtask(2);
        Epic epic = testTaskManager.getEpic(3);
        Task newTask = new Task(testTaskManager.getTask(1));
        newTask.setDescription("UpdateDescription1");
        testTaskManager.updateTask(1,newTask);
        Epic newEpic = new Epic(testTaskManager.getEpic(2));
        newEpic.setDescription("UpdateDescriptionEpic");
        testTaskManager.updateEpic(2,newEpic);
        Subtask newSubtask = new Subtask(testTaskManager.getSubtask(3));
        newSubtask.setDescription("UpdateDescriptionSubtask");
        testTaskManager.updateSubtask(3,newSubtask);
        final List<Task> historyList = testHistoryManager.getHistory();
        final List<Task> historyTasks = new ArrayList<>();
        historyTasks.add(newTask);
        historyTasks.add(newEpic);
        historyTasks.add(newSubtask);
        assertEquals(historyList, historyTasks, "Ошибка: проблемы обновления ссылки на таски в истории просмотров");
    }
}