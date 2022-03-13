package ru.netology.homeworkjclocoursework.logs;

import ru.netology.homeworkjclocoursework.model.Card;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger {
    private static ru.netology.homeworkjclocoursework.logs.Logger logger = null;
    private FileWriter fileLog;

    private Logger() {
        try {
            fileLog = new FileWriter("log/file.log", true);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String logDataTime() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        return dateFormat.format(date);
    }

    public static ru.netology.homeworkjclocoursework.logs.Logger getInstance() {
        if (logger == null) {
            logger = new ru.netology.homeworkjclocoursework.logs.Logger();
        }
        return logger;
    }

    public void log(String cardFromNumber, String cardToNumber, int amountValue, float commission, String result) {
        String currentTime = logDataTime();
        try {
            fileLog.write("[" + currentTime + "] списано с №" + cardFromNumber +
                    " на №" + cardToNumber + " " + amountValue / 100 + "₽, комиссия " +
                    commission * 100 + "%. " + result + "\n");
            fileLog.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void logMoney(Card cardFrom, Card cardTo) {
        String currentTime = logDataTime();
        try {
            fileLog.write("\t[" + currentTime + "] остаток на карте №" + cardFrom.getCardNumber() +
                    " " + cardFrom.getSum() + "₽, остаток на карте №" + cardTo.getCardNumber() +
                    " " + cardTo.getSum() + "₽.\n");
            fileLog.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
