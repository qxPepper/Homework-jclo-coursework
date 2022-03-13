package ru.netology.homeworkjclocoursework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidTests {
    @Test
    public void cardsValidTest() {
        // вход
        String cardFromNumber = "1111222211112222";
        String cardFromValidTill = "1122";
        String cardFromCVV = "111";
        String cardToNumber = "3333444433334444";
        int amountValue = 1000;

        //реальные карты
        String realCardNumberFrom = "1111222211112222";
        String realCardValidTillFrom = "1122";
        String realCardCVVFrom = "111";
        int sum = 10000;
        String realCardNumberTo = "3333444433334444";

        boolean result = true;

        if (realCardNumberFrom.equals(realCardNumberTo)) {
            result = false;
        }

        if (!cardFromValidTill.equals(realCardValidTillFrom)) {
            result = false;
        }

        if (!cardFromCVV.equals(realCardCVVFrom)) {
            result = false;
        }

        if (sum < amountValue) {
            result = false;
        }

        boolean expected = true;

        Assertions.assertEquals(expected, result);
    }
}
