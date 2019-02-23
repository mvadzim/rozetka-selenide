package com.github.mvadzim.rozetka.selenide.tests;

import com.github.mvadzim.rozetka.selenide.utils.Excel;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;

@DisplayName("Просмотр товаров на сайте розетки")
public class GetGoodsList3Test extends BaseTest {

    /*
    Сценарий 3:
        - зайти на сайт rozetka.com.ua
        - перейти в раздел "Телефоны, ТВ и электроника"
        - перейти в раздел "Телефоны"
        - перейти в раздел "Смартфоны"
        - выбрать из первых трех страниц поисковой выдачи название и цены всех девайсов, обозначенных как “Топ продаж”
        - выбрать из первых пяти страниц поисковой выдачи название и цены всех товаров в диапазоне от 3000 до 6000 гривен
        - записать полученные результаты в Excel файл в порядке убывания цены на двух разных листах
        - отправить данный файл по списку рассылки (e-mails из отдельного файла)
     */
    @Test
    @DisplayName("Получение смартфонов с признаком Топ продаж, и от 3000 до 6000 грн. ")
    public void sendTopSaleSmartphonesToEmail() {

        // Копипаст с Сценарий 1:
        // можно было вынести в отдельный общий для двух тестов шаг, или вынести Сценарий 1 и Сценарий 3 в отдельный клас и там копипаст в @before
        // но я это пока не сделал. Todo: Избавится от повторяющегося кода.

        // В ТЗ фигурировал раздел "Телефоны, ТВ и электроника", но такого раздела не нашел, взял "Смартфоны, ТВ и электроника".
        String categoryName = "Смартфоны, ТВ и электроника";
        String subCategoryName = "Телефоны";
        String presetName = "Смартфоны";

        open(homePage.pageUrl);

        $(homePage.linkToCategoryWithName(categoryName)).click();
        step.canSeeHeadingText(categoryName);

        $(categoryPage.linkToCategoryWithName(subCategoryName)).click();
        step.canSeeHeadingText(subCategoryName);

        $(goodsListPage.linkToCategoryWithName(presetName)).click();
        step.canSeeHeadingText(presetName);

        step.canSeeElement(goodsListPage.goodsNameLinks.first());


        goodsListStep.loadMoreGoodsWithAjax(2);
        String[][] goodsNameAndPriceWithPopularityBadge = goodsListPage.goodsNameAndPrice(goodsListPage.goodsBlocksWithPopularityBadge);

        goodsListStep.loadMoreGoodsWithAjax(2);
        String[][] goodsNameAndPriceWithPriceInterval = goodsListPage.goodsNameAndPrice(goodsListPage.goodsBlocksWithPriceInterval("3000", "6000"));

        Excel excel = new Excel();
        String[] sheetsName = {"Популярные смартфоны", "Смартфоны за 3000-6000"};
        String filePath = util.tmpFilePath + "smartphones.xls";
        excel.createSheets(sheetsName);
        // Todo: Нужно сохранить в порядке убывания цены
        excel.putRows(goodsNameAndPriceWithPopularityBadge, 0);
        excel.putRows(goodsNameAndPriceWithPriceInterval, 1);
        excel.writeExcelFile(filePath);
        I.sendFileToEmails(filePath);
    }
}