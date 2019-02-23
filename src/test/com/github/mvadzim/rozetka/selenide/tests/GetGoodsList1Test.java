package com.github.mvadzim.rozetka.selenide.tests;

import org.junit.Test;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;

/**
 * Этот тест написан в стиле https://codeception.com/ и с упором на удобный для восприятия отчет в Allure
 */

@DisplayName("Просмотр товаров на сайте розетки")
public class GetGoodsList1Test extends BaseTest {

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

    public void smartphonesListTest() {
        // В ТЗ фигурировал раздел "Телефоны, ТВ и электроника", но такого раздела не нашел, взял "Смартфоны, ТВ и электроника".
        // Todo: Попробовать для интереса https://github.com/TNG/junit-dataprovider
        String categoryName = "Смартфоны, ТВ и электроника";
        String subCategoryName = "Телефоны";
        String presetName = "Смартфоны";

        I.openUrl(homePage.pageUrl); // // Алиас open(homePage.pageUrl)
        I.click(homePage.linkToCategoryWithName(categoryName)); // Алиас $(homePage.linkToCategoryWithName(categoryName)).click();
        I.canSeeHeadingText(categoryName);

        I.click(categoryPage.linkToCategoryWithName(subCategoryName));
        I.canSeeHeadingText(subCategoryName);

        I.click(goodsListPage.linkToCategoryWithName(presetName));
        I.canSeeHeadingText(presetName);
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

