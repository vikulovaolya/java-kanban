package ru.yandex.practicum.managers.historymanager;

import ru.yandex.practicum.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {
    protected ArrayList<Task> taskHistoryList = new ArrayList<>();

    protected HashMap<Integer, Node> taskHistoryMap = new HashMap<>();

    Node firstNodeInHistory;

    Node lastNodeInHistory;

    @Override
    public ArrayList<Task> getHistory() {
        taskHistoryList = getTasks();
        return taskHistoryList;
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        final int id = task.getId();
        removeNode(id);
        linkLast(task);
        taskHistoryMap.put(id, lastNodeInHistory);
    }

    private void linkLast(Task task) {
        final Node node = new Node(task, lastNodeInHistory, null);
        if (firstNodeInHistory == null) {
            firstNodeInHistory = node;
        } else {
            lastNodeInHistory.setNext(node);
        }
        lastNodeInHistory = node;
    }

    private void removeNode(int id) {
        final Node node = taskHistoryMap.remove(id);
        if (node == null) {
            return;
        }
        Node prevNode = node.getPrev();
        Node nextNode = node.getNext();
        if (prevNode == null && nextNode == null) {
            firstNodeInHistory = null;
            lastNodeInHistory = null;
        } else if (prevNode == null) {
            firstNodeInHistory = nextNode;
            nextNode.setPrev(null);
        } else if (nextNode == null) {
            lastNodeInHistory = prevNode;
            prevNode.setNext(null);
        } else {
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskHistory = new ArrayList<>();
        Node node = firstNodeInHistory;
        while (node != null) {
            taskHistory.add(node.task);
            node = node.next;
        }
        return taskHistory;
    }
}
