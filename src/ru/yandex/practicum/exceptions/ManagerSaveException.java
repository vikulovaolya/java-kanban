package ru.yandex.practicum.exceptions;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String message) {
        super(message);
    }

    public ManagerSaveException() {
    }
}
