package com.wenjie.blog.step_definitions;

import com.wenjie.blog.pages.HomePage;
import com.wenjie.blog.pages.PostPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;
import static com.wenjie.blog.util.CommonUtil.assertContainsIngoreCase;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.openqa.selenium.support.PageFactory.initElements;

public class BaseSteps {
    private WebDriver driver = new FirefoxDriver();
    private HomePage homePage;
    private PostPage postPage;

    @Given("^I enter my blog address \"([^\"]*)\" and go to Home page$")
    public void I_enter_my_blog_address_and_go_to_Home_page(String url) {
        homePage = initElements(driver, HomePage.class);
        postPage = initElements(driver, PostPage.class);
        driver.get(url);
    }

    @When("^I click search button and enter \"([^\"]*)\" in the input field and click the first result$")
    public void I_click_search_button_and_enter_in_the_input_field_and_click_the_first_result(String keyword) {
        homePage.searchKeywordAndEnterPost(keyword);
    }

    @Then("^I go to the article page with title containing \"([^\"]*)\"$")
    public void I_go_to_the_article_page_with_title_containing(String keyword) {
        assertContainsIngoreCase(postPage.getArticleTitle(), keyword);
    }

    @Then("^I refresh current page$")
    public void I_refresh_current_page() {
        driver.navigate().refresh();
    }

    @Then("^I close current window$")
    public void I_close_current_window() {
        driver.close();
    }

    @And("^I can see the page header and footer have same background image$")
    public void I_can_see_the_page_header_and_footer_have_same_background_image() {
        String headerImg = homePage.getBackgroundImageFor(homePage.getHeader());
        String footerImg = homePage.getBackgroundImageFor(homePage.getFooter());
        assertEquals(headerImg, footerImg);
    }

    @Then("^I refresh current page to see the background image changes$")
    public void I_refresh_current_page_to_see_the_background_image_changes() {
        String prevBg = homePage.getBackgroundImageFor(homePage.getHeader());
        driver.navigate().refresh();
        String currBg = homePage.getBackgroundImageFor(homePage.getHeader());
        assertNotEquals(prevBg, currBg);
    }

    @Then("^I can see some tags$")
    public void I_can_see_some_tags() {
        assertNotNull(postPage.getFirstTag());
    }

    @And("^I can see share block with title containing \"([^\"]*)\" and \"([^\"]*)\"$")
    public void I_can_see_share_block_with_title_containing(String arg0, String arg1) {
        assertContainsIngoreCase(postPage.getShareTitle(), arg0);
        assertContainsIngoreCase(postPage.getShareTitle(), arg1);
    }

    @Then("^I click the wechat button$")
    public void I_click_the_wechat_button() {
        postPage.getShareToWechatButton().click();
    }

    @Then("^I see the QR code image$")
    public void I_see_the_QR_code_image() {
        assertNotNull(postPage.getWechatQRCode());
    }

    @Then("^I can see the avatar image below share block$")
    public void I_can_see_the_avatar_image_below_share_block() {
        assertNotNull(postPage.getAvatarImg());
    }

    @And("^I can see the textArea$")
    public void I_can_see_the_textArea() {
        assertNotNull(postPage.getMessageTextarea());
    }

    @Then("^I enter \"([^\"]*)\" in this textArea$")
    public void I_enter_in_this_textArea(String arg0) {
        postPage.getMessageTextarea().sendKeys(arg0);
    }

    @And("^I click sticker button to add a smile sticker$")
    public void I_click_sticker_button_to_add_a_smile_sticker() {
        postPage.getMessageStickerButton().click();
        postPage.getSmileSticker().click();
    }

}
