package com.wenjie.blog.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
    @FindBy(id = "masthead")
    private WebElement header;

    @FindBy(id = "footer")
    private WebElement footer;

    @FindBy(id = "slide")
    private WebElement menuButton;

    @FindBy(xpath = "//*[@id=\"navigation\"]/li[1]/a")
    private WebElement homeLink;

    @FindBy(xpath = "//*[@id=\"navigation\"]/li[2]/a")
    private WebElement categoriesLink;

    @FindBy(xpath = "//*[@id=\"navigation\"]/li[3]/a")
    private WebElement tagsLink;

    @FindBy(xpath = "//*[@id=\"navigation\"]/li[4]/a")
    private WebElement aboutAuthorLink;

    @FindBy(xpath = "//*[@id=\"navigation\"]/li[5]/a")
    private WebElement messageBoardLink;

    @FindBy(xpath = "//*[@id=\"masthead\"]/button")
    private WebElement searchButton;

    @FindBy(xpath = "/html/body/div[1]/div/input")
    private WebElement searchInput;

    @FindBy(xpath = "/html/body/div[1]/div/ul/li[1]/article/a")
    private WebElement searchResultFirst;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div/div/div[1]/article/div/div/a")
    private WebElement firstReadMoreButton;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div/div/div[6]/div/nav/a")
    private WebElement OlderPostButton;

    
    public void searchKeywordAndEnterPost(String wantedStr){
        searchButton.click();
        searchInput.sendKeys(wantedStr);
        searchResultFirst.click();
    }

    public String getBackgroundImageFor(WebElement webElement) {
        String[] bgURL = webElement.getAttribute("style").split("/");
        return bgURL[bgURL.length - 1].split("\"")[0];
    }

    public WebElement getHeader() {
        return header;
    }

    public WebElement getFooter() {
        return footer;
    }
}
