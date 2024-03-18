package ru.yandex.practicum.managers.taskmanager;

import ru.yandex.practicum.managers.historymanager.HistoryManager;
import ru.yandex.practicum.tasks.Epic;
import ru.yandex.practicum.tasks.Subtask;
import ru.yandex.practicum.tasks.Task;
import ru.yandex.practicum.tasks.TaskState;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.awt.Event.HOME;
import static java.lang.Integer.parseInt;

public class FileBackedTaskManager extends InMemoryTaskManager{

    private String managerFileName = "managerFile.csv";
    private static String historyManagerFileName = "historyManagerFile.csv";

    public FileBackedTaskManager(HistoryManager historyManager) {
        super(historyManager);
    }

//    static void historyToString(HistoryManager manager){ // сохраняем в файл с историей данные
//        //В ТЗ был почему-то тип возвращаемого String
//        ArrayList<Task> history = new ArrayList<>();
//        history = manager.getHistory();
//        try (Writer fileWriter = new FileWriter(historyManagerFileName)){ // Для проверки такого исключения FileNotFoundException
//            fileWriter.write( "id,type,name,status,description,epic\n");
//            if (!history.isEmpty()){
//                for (Task task: history){
//                    fileWriter.write( task.toString() + "\n");
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    //static List<Integer> historyFromString(String value){ //вычитываем данные из файла с историей
    //}

    public void importTasksFromFile(){
        int numberOfLine = 0;
        try (FileReader reader = new FileReader(managerFileName); BufferedReader br = new BufferedReader(reader)){
            while (br.ready()) {
                String line = br.readLine();
                if (numberOfLine != 0){
                    Task task = fromString(line);
                    int taskId = task.getId();
                    if (task !=null){
                        if (task.getType().equals(TaskType.TASK)){
                            taskList.put(taskId, task);
                        } else if (task.getType().equals(TaskType.SUBTASK)){
                            subtaskList.put(taskId, (Subtask) task);
                        } else if (task.getType().equals(TaskType.EPIC)){
                            epicList.put(taskId, (Epic) task);
                        }
                    }
                }
                numberOfLine++;
            }
        } catch (IOException e){
            System.out.println("Произошла ошибка во время чтения файла.");
        }
    }

    private Task fromString(String value){ //id,type,name,status,description,epic
        String[] taskParametres  = value.split(",");
        int taskId = 0;
        TaskState taskState;
        try {
            taskId = parseInt(taskParametres[0]);
        } catch (NumberFormatException  exp){ // здесь исключение правильного типа, для "String > int"
            exp.getMessage();
        }
            if (taskParametres[3].equals("NEW")){
                taskState = TaskState.NEW;
            } else if (taskParametres[3].equals("IN_PROGRESS")){
                taskState = TaskState.IN_PROGRESS;
            } else if (taskParametres[3].equals("DONE")){
                taskState = TaskState.DONE;
            } else {
                taskState = null;
            }
        if (taskParametres[1].equals("TASK")){
            Task newTask = new Task(taskId, taskParametres[2], taskParametres[4], taskState);
            return newTask;
        } else if (taskParametres[1].equals("SUBTASK")){
            int epicId = 0;
            try {
                epicId = parseInt(taskParametres[5]);
            } catch (NumberFormatException  exp){ // здесь исключение правильного типа, для "String > int"
                exp.getMessage();
            }
            Subtask newSubtask = new Subtask(taskId, taskParametres[2], taskParametres[4], taskState, epicId);
            return newSubtask;
        } else if (taskParametres[1].equals("EPIC")){
            Epic epic = new Epic(taskId, taskParametres[2], taskParametres[4], taskState, null);
            return epic;
        } else {
            return null;
        }
    }


    private void save (){
//        File fileWithManagerInfo = new File(managerFile);
//        if(fileWithManagerInfo.exists() && !fileWithManagerInfo.isDirectory()) { //Проверяем, что файл существует
            try (Writer fileWriter = new FileWriter(managerFileName)){ // Для проверки такого исключения FileNotFoundException
                fileWriter.write( "id,type,name,status,description,epic\n");
                if (!taskList.isEmpty()){
                    for (Task task: taskList.values()){
                        fileWriter.write( task.toString() + "\n");
                    }
                }
                if (!subtaskList.isEmpty()){
                    for (Subtask task: subtaskList.values()){
                        fileWriter.write( task.toString() + "\n");
                    }
                }
                if (!epicList.isEmpty()){
                    for (Epic task: epicList.values()){
                        fileWriter.write(task.toString() + "\n");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//        } else {
//            Path testFile =
//                    Files.createFile(Paths.get(managerFile));
//        }
    }

    @Override
    public Task setTask(String name, String description, TaskState state) {
        Task newTask = super.setTask(name, description, state);
        save();
        return newTask;
    }

    @Override
    public Subtask setSubtask(String name, String description, TaskState state, int epicId) {
        Subtask subtask = super.setSubtask(name, description, state, epicId);
        save();
        return subtask;
    }

    @Override
    public Epic setEpic(String name, String description, ArrayList<Integer> includeSubtaskList) {
        Epic epic = super.setEpic(name, description, includeSubtaskList);
        save();
        return epic;
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteSubtask(int taskId) {
        super.deleteSubtask(taskId);
        save();
    }

    @Override
    public void deleteEpic(int taskId) {
        super.deleteEpic(taskId);
        save();
    }

    @Override
    public void clearTaskList() {
        super.clearTaskList();
        save();
    }

    @Override
    public void clearSubtaskList() {
        super.clearSubtaskList();
        save();
    }

    @Override
    public void clearEpicList() {
        super.clearEpicList();
        save();
    }

    @Override
    public Task getTask(int taskId) {
        Task task = super.getTask(taskId);
        save();
        return task;
    }

    @Override
    public Subtask getSubtask(int taskId) {
        Subtask subtask = super.getSubtask(taskId);
        save();
        return subtask;
    }

    @Override
    public Epic getEpic(int taskId) {
        Epic epic = super.getEpic(taskId);
        save();
        return epic;
    }

    @Override
    public Task updateTask(int taskId, Task taskWithChanges) {
        Task task = super.updateTask(taskId, taskWithChanges);
        save();
        return task;
    }

    @Override
    public Subtask updateSubtask(int taskId, Subtask subtaskWithChanges) {
        Subtask subtask = super.updateSubtask(taskId, subtaskWithChanges);
        save();
        return subtask;
    }

    @Override
    public Epic updateEpic(int taskId, Epic epicWithChanges) {
        Epic epic = super.updateEpic(taskId, epicWithChanges);
        save();
        return epic;
    }
}
