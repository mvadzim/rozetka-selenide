package com.github.mvadzim.rozetka.selenide.steps;

import com.github.mvadzim.rozetka.selenide.pages.GoodsListPage;
import io.qameta.allure.Step;

import java.util.Arrays;
import java.util.Comparator;

import static com.codeborne.selenide.Selenide.$;

public class GoodsListStep extends MainStep {
    private GoodsListPage goodsListPage = new GoodsListPage();

    @Step("Кликаю {loadPageCount} раз на \"Показать еще 32 товара\"")
    public void loadMoreGoodsWithAjax(int loadPageCount) {
        for (int i = 0; i < loadPageCount; i++) {
            $(goodsListPage.showMoreGoodsButton).click();
        }
    }

    @Step("Сортирую список названий товара и его цены в порядке убывания цены")
    public String[][] orderNameAndPriceListByPriceDesc(String[][] nameAndPriceList) {
        Arrays.sort(nameAndPriceList, new Comparator<String[]>() {
            @Override
            public int compare(final String[] NameAndPrice1, final String[] NameAndPrice2) {
                final Integer price1 = Integer.parseInt(NameAndPrice1[1]);
                final Integer price2 = Integer.parseInt(NameAndPrice2[1]);
                return price2.compareTo(price1);
            }
        });
        return nameAndPriceList;
    }
}
