package com.wenjie.blog.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostPage {
    @FindBy(xpath = "//*[@id=\"main\"]/div/div/article/header/h1")
    private WebElement articleTitle;

    public String getArticleTitle() {
        return articleTitle.getText();
    }
}
