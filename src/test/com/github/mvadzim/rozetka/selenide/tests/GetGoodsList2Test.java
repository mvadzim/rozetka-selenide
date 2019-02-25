package com.github.mvadzim.rozetka.selenide.tests;

import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import com.github.mvadzim.rozetka.selenide.utils.Db;

@DisplayName("Просмотр товаров на сайте розетки")
public class GetGoodsList2Test extends BaseTest {

    private static com.github.mvadzim.rozetka.selenide.steps.BaseStep I;
    private static com.github.mvadzim.rozetka.selenide.steps.GoodsListStep goodsListStep;
    private static com.github.mvadzim.rozetka.selenide.pages.HomePage homePage;
    private static com.github.mvadzim.rozetka.selenide.pages.CategoryPage categoryPage;
    private static com.github.mvadzim.rozetka.selenide.pages.GoodsListPage goodsListPage;

    /*
    Сценарий 2:
        - развернуть локально БД, например MySQL
        - зайти на сайт rozetka.com.ua
        - перейти в раздел "Товары для дома"
        - перейти в раздел "Бытовая химия"
        - перейти в раздел "Для стирки"
        - перейти в раздел "Порошки для стирки"
        - выбрать из первых пяти страниц поисковой выдачи название и цены всех товаров в диапазоне от 100 до 300 гривен
        - записать полученные результаты в базу данных
     */
    @Test
    @DisplayName("Получение списка порошка для стирки от 100 до 300 гривен")
    public void saveProductListToDbTest() {
        String categoryName = "Товары для дома";
        String subcategoryName = "Бытовая химия";
        String sub2CategoryName = "Средства для стирки"; // В ТЗ было "Для стирки"
        String sub3CategoryName = "Стиральные средства"; // В ТЗ было "Порошки для стирки"

        I.openUrl(homePage.pageUrl);

        I.click(homePage.linkToCategoryWithName(categoryName));
        I.canSeeHeadingText(categoryName);
        I.click(categoryPage.linkToCategoryWithName(subcategoryName));
        I.canSeeHeadingText(subcategoryName);
        I.click(categoryPage.linkToCategoryWithName(sub2CategoryName));
        I.canSeeHeadingText(sub2CategoryName);
        I.click(goodsListPage.linkToCategoryWithName(sub3CategoryName));
        I.canSeeHeadingText(sub3CategoryName);

        goodsListStep.loadMoreGoodsWithAjax(4);

        String[][] goodsNameAndPrice = goodsListPage.goodsNameAndPrice(goodsListPage.goodsBlocksWithPriceInterval("100", "300"));

        Db db = new Db();
        db.createDbConnect();
        for (String[] goods : goodsNameAndPrice) {
            String GoodsName = goods[0].replaceAll("['\"\\\\]", "\\\\$0"); // при желании можно вместо костыля копнуть в сторону PreparedStatement
            String GoodsPrice = goods[1];
            db.executeQuery("insert into `rozetka-test`.`goods` set name = '" + GoodsName + "',  price = " + GoodsPrice + ";");
        }
        db.closeDbConnect();
    }
}