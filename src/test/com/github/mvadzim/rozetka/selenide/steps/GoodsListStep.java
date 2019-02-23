package com.github.mvadzim.rozetka.selenide.steps;

import com.github.mvadzim.rozetka.selenide.pages.GoodsListPage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class GoodsListStep extends MainStep {
    private GoodsListPage goodsListPage = new GoodsListPage();

    @Step("Кликаю {loadPageCount} раз на \"Показать еще 32 товара\"")
    public void loadMoreGoodsWithAjax(int loadPageCount) {
        for (int i = 0; i < loadPageCount; i++) {
            $(goodsListPage.showMoreGoodsButton).click();
        }
    }
}
