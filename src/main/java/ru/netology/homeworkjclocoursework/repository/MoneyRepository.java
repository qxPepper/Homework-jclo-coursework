package ru.netology.homeworkjclocoursework.repository;

import org.springframework.stereotype.Repository;
import ru.netology.homeworkjclocoursework.logs.Logger;
import ru.netology.homeworkjclocoursework.model.Card;
import ru.netology.homeworkjclocoursework.transfer.CardToCard;
import ru.netology.homeworkjclocoursework.transfer.SuccessTransfer;

import java.util.List;

@Repository
public class MoneyRepository {
    // не удобно, что в условии суммы - integer
    public static int successTransferNumber = 0;
    private Logger logger = Logger.getInstance();

    public SuccessTransfer moneyTransfer(CardToCard cardToCard, List<Card> cards, int indexFrom, int indexTo) {
        cards.get(indexFrom).setSum((int) (cards.get(indexFrom).getSum()
                - cardToCard.getAmount().getValue() * (1 + cardToCard.getCommission()) / 100));
        cards.get(indexTo).setSum(cards.get(indexTo).getSum() + cardToCard.getAmount().getValue() / 100);

        successTransferNumber++;
        logger.log(cardToCard.getCardFromNumber(), cardToCard.getCardToNumber(),
                cardToCard.getAmount().getValue(), cardToCard.getCommission(), " Выполнено.");

        logger.logMoney(cards.get(indexFrom), cards.get(indexTo));

        String message = Integer.toString(successTransferNumber) + ". Перевод с карты " +
                cards.get(indexFrom).getCardNumber() + " на карту " + cards.get(indexTo).getCardNumber() +
                ", сумма " + cardToCard.getAmount().getValue() / 100 + "₽.";
        return new SuccessTransfer(message);
    }
}
