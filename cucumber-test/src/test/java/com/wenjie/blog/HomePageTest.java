package com.wenjie.blog;

import com.wenjie.blog.pages.HomePage;
import com.wenjie.blog.pages.PostPage;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.wenjie.blog.util.CommonUtil.assertContainsIngoreCase;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.PageFactory.initElements;

public class HomePageTest{
    FirefoxDriver driver = new FirefoxDriver();
    HomePage homePage = initElements(driver, HomePage.class);
    PostPage postPage = initElements(driver, PostPage.class);

    @Test
    public void shouldClickSearchButtonAndGoToAnArticle() {
        driver.get("http://localhost:4000");

        homePage.searchKeywordAndEnterPost("Powershell");

        assertContainsIngoreCase(postPage.getArticleTitle(), "Powershell");
        driver.close();
    }

    @Test
    public void shouldHeaderAndFooterHasSameBackgroundImage() {
        driver.get("http://localhost:4000");

        String headerImageName = homePage.getBackgroundImageFor(homePage.getHeader());
        String footerImageName = homePage.getBackgroundImageFor(homePage.getFooter());

        assertEquals(headerImageName, footerImageName);
        driver.close();
    }
}
