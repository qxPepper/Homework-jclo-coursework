package ru.netology.homeworkjclocoursework.transfer;

import lombok.Data;

@Data
public class ErrorTransfer extends RuntimeException {
    private int id;
    private String message;

    public ErrorTransfer(int id, String message) {
        super(message);
        this.id = id;

        this.message = Integer.toString(id) + ". " + message;
    }
}
