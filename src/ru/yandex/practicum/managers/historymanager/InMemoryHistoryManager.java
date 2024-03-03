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
        Node node = taskHistoryMap.get(id);
        if (node != null) {
            removeNode(node);
            taskHistoryMap.remove(id);
        }
    }

    @Override
    public void update(Task task) {
        int taskId = task.getId();
        Node node = taskHistoryMap.get(taskId);
        if (node != null) {
            node.setTask(task);
        }
    }

    @Override
    public void add(Task task) {
        int taskId = task.getId();
        if (taskHistoryMap.size() == 0) {
            Node node = new Node(task, null, null);
            firstNodeInHistory = node;
            lastNodeInHistory = node;
            taskHistoryMap.put(taskId,node);
        } else {
            if (taskHistoryMap.get(taskId) != lastNodeInHistory) {
                if (taskHistoryMap.containsKey(taskId)) {
                    Node node = taskHistoryMap.get(taskId);
                    removeNode(node);
                }
                linkLast(task);
            }
        }
    }

    void linkLast(Task task) {
        Node oldLastNode = lastNodeInHistory;
        int taskId = task.getId();
        oldLastNode.setNext(taskId);
        Task taskInOldLastNode = oldLastNode.getTask();
        Node node = new Node(task, taskInOldLastNode.getId(), null);
        lastNodeInHistory = node;
        taskHistoryMap.put(taskId,node);
    }

    public void removeNode(Node node) {
        Integer prevNodeId = node.getPrev();
        Integer nextNodeId = node.getNext();
        Node prevNode;
        Node nextNode;
        if (prevNodeId != null) {
            prevNode = taskHistoryMap.get(prevNodeId);
        } else {
            prevNode = null;
        }
        if (nextNodeId != null) {
            nextNode = taskHistoryMap.get(nextNodeId);
        } else {
            nextNode = null;
        }

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
            Integer nextId = nextNode.getTask().getId();
            prevNode.setNext(nextId);
            Integer prevId = prevNode.getTask().getId();
            nextNode.setPrev(prevId);
        }
        Integer nodeId = node.getTask().getId();
        taskHistoryMap.remove(nodeId);
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskHistory = new ArrayList<>();
        Node node = firstNodeInHistory;
        if (firstNodeInHistory == null) {
            return null;
        } else {
            while (true) {
                Task task = node.getTask();
                taskHistory.add(task);
                Integer nextNodeId = node.getNext();
                if (nextNodeId == null) {
                    break;
                } else {
                    node = taskHistoryMap.get(nextNodeId);
                }

            }
            return taskHistory;
        }
    }
}
