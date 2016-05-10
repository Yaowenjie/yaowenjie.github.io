package com.wenjie.blog.step_definitions;

import com.wenjie.blog.pages.HomePage;
import com.wenjie.blog.pages.PostPage;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.wenjie.blog.util.CommonUtil.assertContainsIngoreCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.openqa.selenium.support.PageFactory.initElements;

public class BaseSteps implements En {
    private WebDriver driver = new FirefoxDriver();
    private HomePage homePage;
    private PostPage postPage;

    public BaseSteps() {
        homePage = initElements(driver, HomePage.class);
        postPage = initElements(driver, PostPage.class);

        Given("^I enter my blog address \"([^\"]*)\" and go to Home page$", (String url) -> {
            driver.get(url);
        });

        When("^I click search button and enter \"([^\"]*)\" in the input field and click the first result$", (String keyword) -> {
            homePage.searchKeywordAndEnterPost(keyword);
        });

        Then("^I go to the article page with title containing \"([^\"]*)\"$", (String keyword) -> {
            assertContainsIngoreCase(postPage.getArticleTitle(), keyword);
        });

        Then("^I refresh current page$", () -> {
            driver.navigate().refresh();
        });

        Then("^I close current window$", () -> {
            driver.close();
        });

        And("^I can see the page header and footer have same backgroud image$", () -> {
            String headerImg = homePage.getBackgroundImageFor(homePage.getHeader());
            String footerImg = homePage.getBackgroundImageFor(homePage.getFooter());
            assertEquals(headerImg, footerImg);
        });

        Then("^I refresh current page to see the backgroud image changes$", () -> {
            String prevBg = homePage.getBackgroundImageFor(homePage.getHeader());
            driver.navigate().refresh();
            String currBg = homePage.getBackgroundImageFor(homePage.getHeader());
            assertNotEquals(prevBg, currBg);
        });

    }


}
