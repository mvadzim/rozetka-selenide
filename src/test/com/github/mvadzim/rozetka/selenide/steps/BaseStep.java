package com.github.mvadzim.rozetka.selenide.steps;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

public class BaseStep {
    private static com.github.mvadzim.rozetka.selenide.utils.Util util;

    @Step("Открываю страницу \"{url}\"")
    public static void openUrl(String url) {
        open(url);
    }

    @Step("Кликаю по элементу \"{element}\"")
    public static void click(SelenideElement element) {
        element.click();
    }

    @Step("Вижу в заголовке Н1 текст \"{h1}\"")
    public static void canSeeHeadingText(String h1) {
        $("h1").shouldHave(text(h1), visible);
    }

    @Step("Вижу элемент \"{element}\"")
    public static void canSeeElement(SelenideElement element) {
        element.shouldBe(visible);
    }

    @Step("{comment}")
    public static void comment(String comment) {
    }

    @Attachment
    @Step("Создаю текстовый файл {filePath}")
    public static String writeTextFile(String filePath, String text) {
        util.writeTextFile(filePath, text);
        return text;
    }

    @Attachment
    @Step("Отправляю файл {filePath} на почту")
    public static String sendFileToEmails(String filePath) {
        util.sendFileToEmails(filePath);
        String mailList = util.getConfigPoperty("mail.to");
        return mailList;
    }
}