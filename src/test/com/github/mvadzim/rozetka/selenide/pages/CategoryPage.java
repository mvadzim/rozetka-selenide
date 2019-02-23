package com.github.mvadzim.rozetka.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class CategoryPage {
    /**
     * Example:
     * https://rozetka.com.ua/telefony-tv-i-ehlektronika/c4627949/
     */
    public SelenideElement linkToCategoryWithName(String name) {
        return $$("a.m-cat-l-i-title-link").filter(matchesText(name)).filter(visible).first();
    }
}
