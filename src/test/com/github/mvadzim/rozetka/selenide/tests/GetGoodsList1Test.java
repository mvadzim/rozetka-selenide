package com.github.mvadzim.rozetka.selenide.tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;


/**
 * Этот тест написан в стиле https://codeception.com/ и с упором на удобный для восприятия отчет в Allure
 */

@DisplayName("Просмотр товаров на сайте розетки")
@RunWith(DataProviderRunner.class)
public class GetGoodsList1Test extends BaseTest {

    private static com.github.mvadzim.rozetka.selenide.utils.Util util;
    private static com.github.mvadzim.rozetka.selenide.steps.BaseStep I;
    private static com.github.mvadzim.rozetka.selenide.steps.GoodsListStep goodsListStep;
    private static com.github.mvadzim.rozetka.selenide.pages.HomePage homePage;
    private static com.github.mvadzim.rozetka.selenide.pages.CategoryPage categoryPage;
    private static com.github.mvadzim.rozetka.selenide.pages.GoodsListPage goodsListPage;

    @DataProvider
    public static Object[][] breadcrumbsCategoryNameDataProvider() {
        return new Object[][]{
                {"Смартфоны, ТВ и электроника", "Телефоны", "Смартфоны"},        // В ТЗ фигурировал раздел "Телефоны, ТВ и электроника", но такого раздела не нашел, взял "Смартфоны, ТВ и электроника".
                {"Товары для дома", "Бытовая химия", "Средства для стирки", "Стиральные средства"}, // В ТЗ не было, самодеятельность для тестирования датапровайдера
        };
    }

    @Test
    @DisplayName("Получение списка смартфонов на первых трех страницах выдачи категории")
    @Description("" +
            "Сценарий 1:<br/>" +
            "- зайти на сайт rozetka.com.ua<br/>" +
            "- перейти в раздел \"Телефоны, ТВ и электроника\"<br/>" +
            "- перейти в раздел \"Телефоны\"<br/>" +
            "- перейти в раздел \"Смартфоны\"<br/>" +
            "- выбрать из первых трех страниц поисковой выдачи название всех девайсов<br/>" +
            "- записать полученные результаты в текстовый файл<br/>" +
            "- отправить данный файл по списку рассылки (e-mails из отдельного файла)<br/>" +
            "")
    @UseDataProvider("breadcrumbsCategoryNameDataProvider")
    public void smartphonesListTest(String... categoryNamesForNavigation) {

        I.goFromHomePageToCategory(categoryNamesForNavigation);

        I.comment("Хочу убедится что на странице есть хоть один товар");
        I.canSeeElement(goodsListPage.goodsNameLinks.first());

        goodsListStep.loadMoreGoodsWithAjax(2);

        I.comment("Запоминаю названия всех товаров на странице");
        List goodsName = goodsListPage.goodsNameLinks.texts();
        String filePath = util.tmpFilePath + "smartphones_name.txt";

        I.writeTextFile(filePath, StringUtils.join(goodsName, "\n"));
        I.sendFileToEmails(filePath);

    }
}