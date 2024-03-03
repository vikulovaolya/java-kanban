package ru.yandex.practicum.managers.historymanager;

import ru.yandex.practicum.tasks.Task;

public class Node {
    protected Task task;
    protected Integer prev;
    protected Integer next;

    public Node (Task task, Integer prev, Integer next){
        this.task = task;
        this.prev = prev;
        this.next = next;
    }

    public Task getTask() {
        return task;
    }

    public Integer getPrev() {
        return prev;
    }

    public Integer getNext() {
        return next;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setPrev(Integer prev) {
        this.prev = prev;
    }

    public void setNext(Integer next) {
        this.next = next;
    }
}
