package com.github.mvadzim.rozetka.selenide.steps;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import com.codeborne.selenide.SelenideElement;
import com.github.mvadzim.rozetka.selenide.utils.Util;

public class MainStep {
    protected Util util = new Util();

    @Step("Открываю страницу \"{url}\"")
    public void openUrl(String url) {
        open(url);
    }

    @Step("Кликаю по элементу \"{element}\"")
    public void click(SelenideElement element) {
        element.click();
    }

    @Step("Вижу в заголовке Н1 текст \"{h1}\"")
    public void canSeeHeadingText(String h1) {
        $("h1").shouldHave(text(h1), visible);
    }

    @Step("Вижу элемент \"{element}\"")
    public void canSeeElement(SelenideElement element) {
        element.shouldBe(visible);
    }

    @Step("{comment}")
    public void comment(String comment) {
    }

    @Attachment
    @Step("Создаю текстовый файл {filePath}")
    public String writeTextFile(String filePath, String text) {
        util.writeTextFile(filePath, text);
        return text;
    }

    @Attachment
    @Step("Отправляю файл {filePath} на почту")
    public String sendFileToEmails(String filePath) {
        util.sendFileToEmails(filePath);
        String mailList = util.getConfigPoperty("mail.to");
        return mailList;
    }
}