package com.github.mvadzim.rozetka.selenide.tests;

import io.qameta.allure.selenide.AllureSelenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.mvadzim.rozetka.selenide.pages.CategoryPage;
import com.github.mvadzim.rozetka.selenide.pages.GoodsListPage;
import com.github.mvadzim.rozetka.selenide.pages.HomePage;
import com.github.mvadzim.rozetka.selenide.steps.GoodsListStep;
import com.github.mvadzim.rozetka.selenide.steps.MainStep;
import com.github.mvadzim.rozetka.selenide.utils.Util;


public class BaseTest {
    // Идея в том что бы в каждом тесте не делать импорт страниц и шагов и не создавать экземляр класа
    // но не факт что так правильно делать, особенно если этих страниц и шагов будет очень много, а в тесте они все не нужны
    protected final HomePage homePage = new HomePage();
    protected final CategoryPage categoryPage = new CategoryPage();
    protected final GoodsListPage goodsListPage = new GoodsListPage();
    protected final MainStep I = new MainStep();
    protected final MainStep step = new MainStep();
    protected final GoodsListStep goodsListStep = new GoodsListStep();
    protected final Util util = new Util();

    public BaseTest() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

}
