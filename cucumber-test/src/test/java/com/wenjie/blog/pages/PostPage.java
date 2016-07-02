package com.wenjie.blog.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostPage {
    @FindBy(xpath = "//*[@id=\"main\"]/div/div/article/header/h1")
    private WebElement articleTitle;

    @FindBy(xpath = "//*[@id=\"main\"]/div/footer/div[2]/img")
    private WebElement avatarImg;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div/article/div/div[5]/a[1]")
    private WebElement firstTag;

    @FindBy(xpath = "//*[@id=\"main\"]/div/div/article/div/div[5]/a[2]")
    private WebElement secondTag;

    @FindBy(xpath = "//*[@id=\"main\"]/div/footer/div[1]/p")
    private WebElement shareTitle;

    @FindBy(id = "share-weibo")
    private WebElement shareToWeiboButton;

    @FindBy(id = "share-weixin")
    private WebElement shareToWechatButton;

    @FindBy(xpath = "//*[@id=\"jiathis_webchat\"]/img")
    private WebElement wechatQRCode;

    @FindBy(xpath = "//*[@id=\"ds-reset\"]/div[5]/form/div[1]/textarea")
    private WebElement messageTextarea;

    @FindBy(xpath = "//*[@id=\"ds-reset\"]/div[5]/form/div[2]/div[2]/a")
    private WebElement messageStickerButton;

    @FindBy(xpath = "//*[@id=\"ds-smilies-tooltip\"]/div/ul/li[14]/img")
    private WebElement smileSticker;

    public String getArticleTitle() {
        return articleTitle.getText();
    }

    public WebElement getAvatarImg() {
        return avatarImg;
    }

    public WebElement getFirstTag() {
        return firstTag;
    }

    public String getShareTitle() {
        return shareTitle.getText().toString();
    }

    public WebElement getWechatQRCode() {
        return wechatQRCode;
    }

    public WebElement getShareToWechatButton() {
        return shareToWechatButton;
    }

    public WebElement getMessageTextarea() {
        return messageTextarea;
    }

    public WebElement getMessageStickerButton() {
        return messageStickerButton;
    }

    public WebElement getSmileSticker() {
        return smileSticker;
    }

}
