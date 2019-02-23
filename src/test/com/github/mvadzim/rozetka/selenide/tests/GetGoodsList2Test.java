package com.github.mvadzim.rozetka.selenide.tests;

import com.github.mvadzim.rozetka.selenide.utils.Db;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;

@DisplayName("Просмотр товаров на сайте розетки")
public class GetGoodsList2Test extends BaseTest {
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

        open(homePage.pageUrl);

        $(homePage.linkToCategoryWithName(categoryName)).click();
        step.canSeeHeadingText(categoryName);
        $(categoryPage.linkToCategoryWithName(subcategoryName)).click();
        step.canSeeHeadingText(subcategoryName);
        $(categoryPage.linkToCategoryWithName(sub2CategoryName)).click();
        step.canSeeHeadingText(sub2CategoryName);
        $(goodsListPage.linkToCategoryWithName(sub3CategoryName)).click();
        step.canSeeHeadingText(sub3CategoryName);

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
