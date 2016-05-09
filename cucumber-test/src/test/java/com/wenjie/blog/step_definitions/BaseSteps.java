package com.wenjie.blog.step_definitions;

import com.wenjie.blog.pages.HomePage;
import com.wenjie.blog.pages.PostPage;
import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.wenjie.blog.util.CommonUtil.assertContainsIngoreCase;
import static org.openqa.selenium.support.PageFactory.initElements;

public class BaseSteps implements En {
    private WebDriver driver = new FirefoxDriver();
    private HomePage homePage;
    private PostPage postPage;

    public BaseSteps() {
        Given("^I go to My Blog Home page$", () -> {
            driver.get("http://localhost:4000");
        });

        When("^I click search button and enter \"([^\"]*)\" in the input blank and click the first article$", (String keyword) -> {
            homePage = initElements(driver, HomePage.class);
            homePage.searchKeywordAndEnterPost(keyword);
        });

        Then("^I go to the article page with title containing \"([^\"]*)\"$", (String keyword) -> {
            postPage = initElements(driver, PostPage.class);
            assertContainsIngoreCase(postPage.getArticleTitle(), keyword);
        });
    }


}
