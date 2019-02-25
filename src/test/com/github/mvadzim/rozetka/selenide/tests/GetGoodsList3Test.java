package com.github.mvadzim.rozetka.selenide.tests;

import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import com.github.mvadzim.rozetka.selenide.utils.Excel;

@DisplayName("Просмотр товаров на сайте розетки")
public class GetGoodsList3Test extends BaseTest {

    private static com.github.mvadzim.rozetka.selenide.utils.Util util;
    private static com.github.mvadzim.rozetka.selenide.steps.BaseStep I;
    private static com.github.mvadzim.rozetka.selenide.steps.GoodsListStep goodsListStep;
    private static com.github.mvadzim.rozetka.selenide.pages.HomePage homePage;
    private static com.github.mvadzim.rozetka.selenide.pages.CategoryPage categoryPage;
    private static com.github.mvadzim.rozetka.selenide.pages.GoodsListPage goodsListPage;

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

        I.openUrl(homePage.pageUrl);

        I.click(homePage.linkToCategoryWithName(categoryName));
        I.canSeeHeadingText(categoryName);

        I.click(categoryPage.linkToCategoryWithName(subCategoryName));
        I.canSeeHeadingText(subCategoryName);

        I.click(goodsListPage.linkToCategoryWithName(presetName));
        I.canSeeHeadingText(presetName);

        I.canSeeElement(goodsListPage.goodsNameLinks.first());


        goodsListStep.loadMoreGoodsWithAjax(2);

        // String[][0] - Goods Name
        // String[][1] - Goods Price
        String[][] goodsNameAndPriceWithPopularityBadge = goodsListPage.goodsNameAndPrice(goodsListPage.goodsBlocksWithPopularityBadge);
        goodsNameAndPriceWithPopularityBadge = goodsListStep.orderNameAndPriceListByPriceDesc(goodsNameAndPriceWithPopularityBadge);

        goodsListStep.loadMoreGoodsWithAjax(2);
        String[][] goodsNameAndPriceWithPriceInterval = goodsListPage.goodsNameAndPrice(goodsListPage.goodsBlocksWithPriceInterval("3000", "6000"));
        goodsNameAndPriceWithPriceInterval = goodsListStep.orderNameAndPriceListByPriceDesc(goodsNameAndPriceWithPriceInterval);

        String[] sheetsName = {"Популярные смартфоны", "Смартфоны за 3000-6000"};
        String filePath = util.tmpFilePath + "smartphones.xls";
        Excel excel = new Excel();
        excel.createSheets(sheetsName);
        excel.putRows(goodsNameAndPriceWithPopularityBadge, 0);
        excel.putRows(goodsNameAndPriceWithPriceInterval, 1);
        excel.writeExcelFile(filePath);
        I.sendFileToEmails(filePath);
    }
}