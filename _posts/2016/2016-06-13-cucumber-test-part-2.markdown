---
layout: "post"
title: "从头写一个Cucumber测试(二) Cucumber Test"
description: “Writing Cucumber Test”
date: "2016-06-13 10:16"
published: true
comments: true
category: 编程相关
tags: [Cucumber, Test, SpringBoot, Selenium]
---
### 承接上文
&emsp;&emsp;[前一篇博客]({{site.url}}/编程相关/cucumber-test)介绍了如何写一个简单的Selenium Test，接下来你将会了解如何将其转化更符合BDD（行为驱动开发）思想的Cucumber专有语言。

<!--more-->

## 添加依赖
&emsp;&emsp;第一步还是要在 ___build.gradle___ 文件内的Dependencies内添加相应的依赖:

```
compile('info.cukes:cucumber-java8:1.2.4')
compile('info.cukes:cucumber-junit:1.2.4')
```
&emsp;&emsp;当然，如果你用的不是java8，也可以换成对应的其他依赖。然后像之前一样，执行gradle的build指令，完成添加依赖。

## 编写Feature文件
&emsp;&emsp;接下来，你可能就会想着把[上一篇博客]({{site.url}}/编程相关/cucumber-test)提到的步骤转化为Cucumber的语言，也就是feature文件，如下所示：

```
@base_flow
Feature: This is a simple test using Cucumber.

  Scenario:
    Given I enter my blog address "{{site.url}}" and go to Home page
    # Search Button Feature:
    When I click the search button
    And I enter "PowerShell" in the input field
    And I click the first result of search
    Then I go to the article page with title containing "PowerShell"
```

&emsp;&emsp;你可以给它命名为诸如 ___simple_test.feauture___ 这样的文件。相信即便你不是开发人员，只要你懂英文，你就明白这段话的意思。除去base_flow的tag，以及上面的四行描述，下面用和自然语言很相似的语言描述了具体的行为。注意，其中的Feature, Scenario, Given, When, And, Then都是Cucumber的feature文件关键字。

## 编写具体实现和测试
&emsp;&emsp;但是写好了feature文件，它却并不能执行，所以我们还得依赖java代码来具体执行上述feature文件中的那些步骤。所以需要编写一个名为BaseSteps的类，用于表示上述行为的实现：

```
package com.wenjie;

import cucumber.api.java8.En;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.PageFactory.initElements;

public class BaseSteps implements En {
    private WebDriver driver = new FirefoxDriver();
    private BlogPage blogPage = new BlogPage();

    public BaseSteps() {
        blogPage = initElements(driver, BlogPage.class);

        Given("^I enter my blog address \"([^\"]*)\" and go to Home page$", (String url) -> {
            driver.get(url);
        });

        When("^I click the search button$", () -> {
            blogPage.clickSearchButton();
        });

        And("^I enter \"([^\"]*)\" in the input field$", (String keyword) -> {
            blogPage.inputSearchWording(keyword);
        });

        And("^I click the first result of search$", () -> {
            blogPage.clickFirstResultOfSearch();
        });

        Then("^I go to the article page with title containing \"([^\"]*)\"$", (String keyword) -> {
            assertContainsIngoreCase(blogPage.getArticleTitle(), keyword);
        });
    }

    public static void assertContainsIngoreCase(String set, String subset) {
        assertThat(set.toLowerCase(), containsString(subset.toLowerCase()));
    }
}
```

&emsp;&emsp;可以看出来，我们这里采用了Java8中Lambda表达式的写法，并通过正则注入参数。

```
注意：java版本为1.8.0.71时，这种用法将会报这样的错误：java.lang.IllegalArgumentException: Wrong type at constant pool index；换成1.8.0.51后问题消失。
```

&emsp;&emsp;至此，我们这个简单的Cucumber Test的实现部分就完成了，但是要执行它们，通常我们需要写一个对应的测试类，比如说BaseFlowTest这个类：

```
package com.wenjie;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        tags = {"@base_flow"}
)
public class BaseFlowTest {
}
```

&emsp;&emsp;它指定了feature文件路径和对应的feature文件的tag，也就是说执行这个测试，就会执行对应路径下tag为base_flow的feature文件内容，而这个feature文件则依赖BaseSteps这个类来具体实现它指定的步骤。<br/>
&emsp;&emsp;你可以通过IDE或者使用gradle的task来执行这个测试(BaseFlowTest),你会看到和[上一篇博客]({{site.url}}/编程相关/cucumber-test)一样的结果。不同的是，你维护的一份更加可读的文件，这对于存在大量 __可复用__ 操作的测试来说十分便利、有益。
