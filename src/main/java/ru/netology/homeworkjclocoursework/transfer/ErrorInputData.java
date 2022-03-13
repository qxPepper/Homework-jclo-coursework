package ru.netology.homeworkjclocoursework.transfer;

import lombok.Data;

@Data
public class ErrorInputData extends RuntimeException {
    private int id;
    private String message;

    public ErrorInputData(int id, String message) {
        super(message);
        this.id = id;

        this.message = Integer.toString(id) + ". " + message;
    }
}
