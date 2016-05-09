package com.wenjie.blog.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
    @FindBy(xpath = "//*[@id=\"masthead\"]/button")
    private WebElement searchButton;

    @FindBy(xpath = "/html/body/div[1]/div/input")
    private WebElement searchInput;

    @FindBy(xpath = "/html/body/div[1]/div/ul/li[1]/article/a")
    private WebElement searchResultFirst;

    public void searchKeywordAndEnterPost(String wantedStr){
        searchButton.click();
        searchInput.sendKeys(wantedStr);
        searchResultFirst.click();
    }
}
