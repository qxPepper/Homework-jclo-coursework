package ru.netology.homeworkjclocoursework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private String cardNumber;
    private String cardValidTill;
    private String cardCVV;
    private int sum;
}
