package com.github.mvadzim.rozetka.selenide.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import io.qameta.allure.Step;

public class GoodsListPage {
    /**
     * Example:
     * https://rozetka.com.ua/mobile-phones/c80003/preset=smartfon/
     */
    public String goodsNameLinkCssSelector = ".g-i-tile-i-title a , .g-i-list-title a";
    public String goodsPriceCssSelector = ".g-price-uah";
    public ElementsCollection goodsNameLinks = $$(goodsNameLinkCssSelector);
    public SelenideElement goodsNameLink = $(".g-i-tile-i-title a , .g-i-list-title a");

    public SelenideElement showMoreGoodsButton = $("a.g-i-more-link");

    public SelenideElement paginatorLink(int pageNumber) {
        return $(By.xpath("//a[contains(@class , 'paginator-catalog-l-link') and contains(text() ,'" + pageNumber + "')]"));
    }

    public ElementsCollection goodsBlocksWithPriceInterval(String minPrice, String maxPrice) {
        // как вариат вместо ужасного xpath можно забрать все товары с ценами и отфильтровать по цене средствани ЯП
        return $$(By.xpath("//*[@class='g-price-uah' and (translate(text(), ' ', '')>=" + minPrice + " and translate(text(), ' ', '')<=" + maxPrice + ")]/ancestor::*[@class='g-i-tile-i-box-desc']"));
    }

    public ElementsCollection goodsBlocks = $$(".g-i-tile-i-box-desc");
    public ElementsCollection goodsBlocksWithPopularityBadge = $$(By.xpath("//*[contains(@class, 'g-tag-icon-small-popularity')]/ancestor::*[@class='g-i-tile-i-box-desc']"));

    @Step("Парсинг названия и цены товара (в цикле)")
    public String[][] goodsNameAndPrice(ElementsCollection goodsBlocks) {
        int goodsBlocksCount = goodsBlocks.size();
        String[][] goodsNameAndPrice = new String[goodsBlocksCount][2];
        for (int i = 0; i < goodsBlocksCount; i++) {
            goodsNameAndPrice[i][0] = goodsBlocks.get(i).$(goodsNameLinkCssSelector).text();
            // Изначально цена выбирается с названием валюты, как по нормальному выбрать только число так и не придумал, /text() - могло бы решить пробему если бы работало (InvalidSelectorException The result of the xpath expression [object Text]. It should be an element.)
            goodsNameAndPrice[i][1] = goodsBlocks.get(i).$(goodsPriceCssSelector).text().replaceAll("\\s", "").replaceAll(".*?(\\d+).*", "$1");
        }
        return goodsNameAndPrice;
    }
    public SelenideElement linkToCategoryWithName(String name) {
        return $$("a.cat-tree-l-i-link, a.m-cat-l-i-title-link").filter(matchesText(name)).filter(visible).first();
    }
}
