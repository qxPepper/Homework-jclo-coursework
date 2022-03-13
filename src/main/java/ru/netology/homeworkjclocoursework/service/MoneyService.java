package ru.netology.homeworkjclocoursework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.homeworkjclocoursework.logs.Logger;
import ru.netology.homeworkjclocoursework.model.Card;
import ru.netology.homeworkjclocoursework.repository.MoneyRepository;
import ru.netology.homeworkjclocoursework.transfer.CardToCard;
import ru.netology.homeworkjclocoursework.transfer.ErrorInputData;
import ru.netology.homeworkjclocoursework.transfer.ErrorTransfer;
import ru.netology.homeworkjclocoursework.transfer.SuccessTransfer;

import java.util.List;

@Service
public class MoneyService {
    private final MoneyRepository moneyRepository;
    public static int errorInputDataNumber = 0;
    public static int errorTransferNumber = 0;
    private Logger logger = Logger.getInstance();

    @Autowired
    public MoneyService(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    public SuccessTransfer moneyTransfer(CardToCard cardToCard, List<Card> cards) {
        int indexFrom = selectIndexCard(cardToCard.getCardFromNumber(), cards);
        int indexTo = selectIndexCard(cardToCard.getCardToNumber(), cards);

        if (indexFrom == -1 || indexTo == -1) {
            errorTransferNumber++;
            logger.log(cardToCard.getCardFromNumber(), cardToCard.getCardToNumber(),
                    cardToCard.getAmount().getValue(), cardToCard.getCommission(), " Не выполнено.");
            throw new ErrorTransfer(errorTransferNumber, "Таких карт нет.");
        }

        if (!cardsValid(cardToCard, cards.get(indexFrom), cards.get(indexTo))) {
            errorInputDataNumber++;
            logger.log(cardToCard.getCardFromNumber(), cardToCard.getCardToNumber(),
                    cardToCard.getAmount().getValue(), cardToCard.getCommission(), " Не выполнено.");
            throw new ErrorInputData(errorInputDataNumber, "Карта не валидна или недостаточно денег.");
        }
        return moneyRepository.moneyTransfer(cardToCard, cards, indexFrom, indexTo);
    }

    private static boolean cardsValid(CardToCard cardToCard, Card cardFrom, Card cardTo) {
        if (cardFrom.getCardNumber().equals(cardTo.getCardNumber())) {
            return false;
        }

        if (!validTillToString(cardToCard.getCardFromValidTill()).equals(cardFrom.getCardValidTill())) {
            return false;
        }

        if (!cardToCard.getCardFromCVV().equals(cardFrom.getCardCVV())) {
            return false;
        }
        return cardFrom.getSum() >= (cardToCard.getAmount().getValue() * (1 + cardToCard.getCommission())) / 100;
    }

    private static String validTillToString(String cardFromValidTill) {
        return cardFromValidTill.substring(0, 2).concat(cardFromValidTill.substring(3));
    }

    private static int selectIndexCard(String cardNumber, List<Card> cards) {
        int i;
        for (i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardNumber().equals(cardNumber)) {
                return i;
            }
        }
        return -1;
    }
}
