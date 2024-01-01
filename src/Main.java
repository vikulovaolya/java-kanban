import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        int taskId;
        int epicId;
        int subtaskId;
        TaskState state = null;

        /* Данные для быстрого тестирования */
        taskManager.setTask("Задача 1", "Текст задачи 1", TaskState.NEW);
        taskManager.setTask("Задача 2", "Текст задачи 2", TaskState.IN_PROGRESS);
        taskManager.setTask("Задача 3", "Текст задачи 3", TaskState.DONE);
        taskManager.setEpic("Эпик 4", "Текст эпика 4", null);
        taskManager.setEpic("Эпик 5", "Текст эпика 5", null);
        taskManager.setSubtask("Подзадача 6", "Текст подзадачи 6", TaskState.DONE, 4);
        taskManager.setSubtask("Подзадача 7", "Текст подзадачи 7", TaskState.IN_PROGRESS, 5);
        taskManager.setSubtask("Подзадача 8", "Текст подзадачи 8", TaskState.DONE, 5);
        ArrayList<Integer> subtasks1 = new ArrayList<>();
        subtasks1.add(6);
        taskManager.updateEpic(4, "Эпик 4(обновленный)", "Текст эпика 1 (обновленный)",
                subtasks1);
        ArrayList<Integer> subtasks2 = new ArrayList<>();
        subtasks2.add(7);
        subtasks2.add(8);
        taskManager.updateEpic(5, "Эпик 5(обновленный)", "Текст эпика 2 (обновленный)",
                subtasks2);
        taskManager.deleteSubtask(6);
        taskManager.deleteTask(1);
        taskManager.deleteEpic(5);
        taskManager.setEpic("Эпик 9", "Текст эпика 9", null);
        taskManager.setSubtask("Подзадача 10", "Текст Подзадачи 10", TaskState.IN_PROGRESS, 9);
        taskManager.setSubtask("Подзадача 11", "Текст Подзадачи 11", TaskState.DONE, 9);
        taskManager.updateSubtask(10,"Подзадача 10 (обновление статуса)", "Текст Подзадачи 10",
                TaskState.DONE, 9);
        taskManager.updateSubtask(10,"Подзадача 10 (меняем эпик)", "Текст Подзадачи 10",
                TaskState.DONE, 4);
        taskManager.updateSubtask(10,"Подзадача 10 (обновление статуса)", "Текст Подзадачи 10",
                TaskState.NEW, 4);
        //taskManager.clearEpicList();
        //taskManager.clearSubtaskList();


        while (true) {
            printMenu();
            String name;
            String descr;
            String command = scanner.next();
            switch (command) {
                case "CREATE_TASK":
                    System.out.println("Введите название задачи");
                    System.out.println("Введите название задачи");
                    name = scanner.next();
                    System.out.println("Введите текс задачи");
                    descr = scanner.next();
                    state = status(scanner);
                    taskManager.setTask(name, descr, state);
                    System.out.println("Задача добавлена");
                    break;
                case "CREATE_SUBTASK":
                    System.out.println("Введите название подзадачи");
                    name = scanner.next();
                    System.out.println("Введите текс подзадачи");
                    descr = scanner.next();
                    state = status(scanner);
                    System.out.println("Введите эпик, к которому относится задача");
                    epicId = scanner.nextInt();
                    taskManager.setSubtask(name, descr, state, epicId);
                    System.out.println("Подзадача добавлена");
                    break;
                case "CREATE_EPIC":
                    System.out.println("Введите название эпика");
                    name = scanner.next();
                    System.out.println("Введите текс эпика");
                    descr = scanner.next();
                    taskManager.setEpic(name, descr, null);
                    System.out.println("Эпик добавлена");
                    break;
                case "GET_TASK_LIST":
                    System.out.println("Список задач:");
                    System.out.println(taskManager.getTaskList());
                    break;
                case "GET_SUBTASK_LIST":
                    System.out.println("Список подзадач:");
                    System.out.println(taskManager.getSubtaskList());
                    break;
                case "GET_EPIC_LIST":
                    System.out.println("Список эпиков:");
                    System.out.println(taskManager.getEpicList());
                    break;
                case "CLEAR_TASK_LIST":
                    taskManager.clearTaskList();
                    System.out.println("Все задачи удалены");
                    break;
                case "CLEAR_SUBTASK_LIST":
                    taskManager.clearSubtaskList();
                    System.out.println("Все подзадачи удалены");
                    break;
                case "CLEAR_EPIC_LIST":
                    taskManager.clearEpicList();
                    System.out.println("Все эпики удалены");
                    break;
                case "GET_TASK":
                    System.out.println("Введите id задачи, которую хотите получить");
                    taskId = scanner.nextInt();
                    taskManager.getTask(taskId);
                    System.out.println(taskManager.getTask(taskId));
                    break;
                case "GET_SUBTASK":
                    System.out.println("Введите id задачи, которую хотите получить");
                    taskId = scanner.nextInt();
                    taskManager.getSubtask(taskId);
                    System.out.println(taskManager.getSubtask(taskId));
                    break;
                case "GET_EPIC":
                    System.out.println("Введите id задачи, которую хотите получить");
                    taskId = scanner.nextInt();
                    taskManager.getEpic(taskId);
                    System.out.println(taskManager.getEpic(taskId));
                    break;
                case "DELETE_TASK":
                    System.out.println("Введите id задачи, которую хотите удалить");
                    taskId = scanner.nextInt();
                    taskManager.deleteTask(taskId);
                    break;
                case "DELETE_SUBTASK":
                    System.out.println("Введите id задачи, которую хотите удалить");
                    taskId = scanner.nextInt();
                    taskManager.deleteSubtask(taskId);
                    break;
                case "DELETE_EPIC":
                    System.out.println("Введите id задачи, которую хотите удалить");
                    taskId = scanner.nextInt();
                    taskManager.deleteEpic(taskId);
                    break;
                case "UPDATE_TASK":
                    System.out.println("Введите id задачи, которую хотите обновить");
                    taskId = scanner.nextInt();
                    System.out.println("Введите новое название задачи");
                    name = scanner.next();
                    System.out.println("Введите новый текст задачи");
                    descr = scanner.next();
                    System.out.println("Введите статус задачи (NEW, IN_PROGRESS, DONE)");
                    state = status(scanner);
                    taskManager.updateTask(taskId, name, descr, state);
                    System.out.println("Задача добавлена");
                    break;
                case "UPDATE_SUBTASK":
                    System.out.println("Введите id подзадачи, которую хотите обновить");
                    taskId = scanner.nextInt();
                    System.out.println("Введите новое название задачи");
                    name = scanner.next();
                    System.out.println("Введите новый текст задачи");
                    descr = scanner.next();
                    state = status(scanner);
                    System.out.println("Введите эпик, к которому относится задача");
                    epicId = scanner.nextInt();
                    taskManager.updateSubtask(taskId, name, descr, state, epicId);
                    System.out.println("Задача добавлена");
                    break;
                case "UPDATE_EPIC":
                    System.out.println("Введите id эпика, который хотите обновить");
                    taskId = scanner.nextInt();
                    System.out.println("Введите новое название задачи");
                    name = scanner.next();
                    System.out.println("Введите новый текст задачи");
                    descr = scanner.next();
                    System.out.println("Введите подзадачи, относящиеся к эпику");
                    System.out.println("[для окончания ввода подзадач введите 0]");
                    ArrayList<Integer> includeSubtaskList = new ArrayList<>();
                    while (true) {
                        subtaskId = scanner.nextInt();
                        if (subtaskId == 0) {
                            break;
                        } else {
                            includeSubtaskList.add(subtaskId);
                        }
                    }
                    taskManager.updateEpic(taskId, name, descr, includeSubtaskList);
                    System.out.println("Задача добавлена");
                    break;
                case "GET_SUBTASKS_FOR_EPIC":
                    System.out.println("Введите эпик, подзадачи которого необходимо вывести");
                    taskId = scanner.nextInt();
                    taskManager.getSubtaskListForEpic(taskId);
                    System.out.println(taskManager.getSubtaskListForEpic(taskId));
                    break;
                case "EXIT":
                    System.exit(0);
            }
        }
    }


    public static void printMenu() {
        System.out.println("Введите команду: ");
/*        System.out.println(" CREATE_TASK - создать задачу");
        System.out.println(" CREATE_SUBTASK - создать подзадачу");
        System.out.println(" CREATE_EPIC - создать эпик");
        System.out.println(" GET_TASK_LIST - показать список задач");
        System.out.println(" GET_SUBTASK_LIST - показать список подзадач");
        System.out.println(" GET_EPIC_LIST - показать список эпиков");
        System.out.println(" CLEAR_TASK_LIST - удалить все задачи");
        System.out.println(" CLEAR_SUBTASK_LIST - удалить все подзадачи");
        System.out.println(" CLEAR_EPIC_LIST - удалить все эпики");
        System.out.println(" GET_TASK - получить инфо по задаче");
        System.out.println(" GET_SUBTASK - получить инфо по подзадаче");
        System.out.println(" GET_EPIC - получить инфо по эпику");
        System.out.println(" DELETE_TASK - удалить задачу по id");
        System.out.println(" DELETE_SUBTASK - удалить подзадачу по id");
        System.out.println(" DELETE_EPIC - удалить эпик по id");
        System.out.println(" UPDATE_TASK - обновить задачу");
        System.out.println(" UPDATE_SUBTASK - обновить подзадачу");
        System.out.println(" UPDATE_EPIC - обновить эпик");
        System.out.println(" GET_SUBTASKS_FOR_EPIC - вывести все подзадачи эпика");
        System.out.println(" EXIT - выход");*/
    }

    public static TaskState status(Scanner scanner) {
        String stateText = "";
        System.out.println("Введите статус задачи (NEW, IN_PROGRESS, DONE)");
        stateText = scanner.next();
        switch (stateText) {
            case "NEW":
                return TaskState.NEW;
            case "IN_PROGRESS":
                return TaskState.IN_PROGRESS;
            case "DONE":
                return TaskState.DONE;
            default:
                return null;
        }
    }
}
