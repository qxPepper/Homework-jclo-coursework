package ru.netology.homeworkjclocoursework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.homeworkjclocoursework.model.Card;
import ru.netology.homeworkjclocoursework.service.MoneyService;
import ru.netology.homeworkjclocoursework.transfer.CardToCard;
import ru.netology.homeworkjclocoursework.transfer.ErrorInputData;
import ru.netology.homeworkjclocoursework.transfer.ErrorTransfer;
import ru.netology.homeworkjclocoursework.transfer.SuccessTransfer;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MoneyController implements CommandLineRunner {
    private final MoneyService moneyService;
    private List<Card> cards;

    @Autowired
    public MoneyController(MoneyService moneyService) {
        this.moneyService = moneyService;
    }

    @PostMapping(value = "/transfer")
    public SuccessTransfer moneyTransfer(@RequestBody CardToCard cardToCard) {
        return moneyService.moneyTransfer(cardToCard, cards);
    }

    @PostMapping(value = "/confirmOperation")
    public ResponseEntity<?> moneyConfirm() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ErrorInputData.class)
    public String handleInvalidCredentials(ErrorInputData e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ErrorTransfer.class)
    public String handleInvalidCredentials(ErrorTransfer e) {
        return e.getMessage();
    }

    @Override
    public void run(String... args) throws Exception {
        cards = new ArrayList<>();

        cards.add(new Card("1111222211112222", "1122", "111", 10000));
        cards.add(new Card("3333444433334444", "1222", "333", 5000));
        cards.add(new Card("5555666655556666", "1123", "555", 1000));
        cards.add(new Card("7777888877778888", "1223", "777", 500));
        cards.add(new Card("9999000099990000", "1124", "999", 2000));
    }
}
