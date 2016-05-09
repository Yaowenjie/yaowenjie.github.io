package com.wenjie.blog;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class HomePageTest {
    FirefoxDriver driver = new FirefoxDriver();

    @Test
    public void shouldClickSearchButtonAndGoToAnArticle() throws InterruptedException {
        driver.get("http://localhost:4000");
        driver.findElement(By.xpath("//*[@id=\"masthead\"]/button")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/input")).sendKeys("Powershell");
        driver.findElement(By.xpath("/html/body/div[1]/div/ul/li[1]/article/a")).click();
        String articleTitle = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div/article/header/h1")).getText();
        assertContainsIngoreCase(articleTitle, "Powershell");
        driver.close();
    }


    public void assertContainsIngoreCase(String set, String subset) {
        assertThat(set.toLowerCase(), containsString(subset.toLowerCase()));
    }

}
