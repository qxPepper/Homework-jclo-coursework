package ru.netology.homeworkjclocoursework;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeworkJcloCourseworkApplicationTests {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    private static final GenericContainer<?> backTest = new GenericContainer<>("my_back:1.0")
            .withExposedPorts(8080);

    @BeforeAll
    public static void setUp() {
        backTest.start();
    }

    @Test
    void contextLoads_back() throws JSONException {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var amountTest = new JSONObject();
        amountTest.put("value", 1000);

        var carToCarTest = new JSONObject();
        carToCarTest.put("cardFromNumber", "3333444433334444");
        carToCarTest.put("cardFromValidTill", "12/22");
        carToCarTest.put("cardFromCVV", "333");
        carToCarTest.put("cardToNumber", "7777888877778888");
        carToCarTest.put("amount", amountTest);

        HttpEntity<String> request = new HttpEntity<String>(carToCarTest.toString(), headers);

        Integer backTestPort = backTest.getMappedPort(8080);
        var backTestUrl = "http://localhost:" + backTestPort + "/transfer";

        ResponseEntity<String> fromBackTest = restTemplate.postForEntity(backTestUrl, request, String.class);

        var expected = new JSONObject();
        expected.put("operationId", "1. Перевод с карты 3333444433334444 на карту 7777888877778888, сумма 10₽.");

        Assertions.assertEquals(expected.toString(), fromBackTest.getBody());
    }
}
