package com.wenjie;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HomePageTest {
    FirefoxDriver driver = new FirefoxDriver();

    @Test
    public void shouldClickMenuAndSearchButton() {
        driver.get("http://localhost:4000");
    }


}
