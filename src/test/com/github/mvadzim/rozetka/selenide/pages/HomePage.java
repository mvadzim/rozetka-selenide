package com.github.mvadzim.rozetka.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class HomePage {
    /**
     * Example:
     * https://rozetka.com.ua/
     */
    public static String pageUrl = "https://rozetka.com.ua";

    // Локатор используется для тестов не только на главной странице! вынести такие в UniversalPage\BasePage ??
    public static SelenideElement linkToCategoryWithName(String name) {
        return $$("a.menu-categories__link , a.cat-tree-l-i-link, a.m-cat-l-i-title-link").filter(matchesText(name)).filter(visible).first();
    }

}
