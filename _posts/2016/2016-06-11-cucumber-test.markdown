---
layout: "post"
title: "从头写一个Cucumber测试(一) Selenium Test"
description: “Writing Selenium Test”
date: "2016-06-11 22:36"
published: true
comments: true
category: 编程相关
tags: [Cucumber, Test, SpringBoot, Selenium]
imagefeature: wj/20.jpg
---
### 背景(废话不读系列)
&emsp;&emsp;前段时间闲来无事，试着给自己的[博客]({{site.url}})从头写了一些简单的Cucumber Test。现隔了数周，还是决定把整个过程整理成一份博客文章，一是分享给大家、参考指正，二是便于自己后期回阅。

<!--more-->

## 技术栈选型
&emsp;&emsp;为了便于Coding，也顺便用起来一起基本常用的工具/技术，所以这篇文章会基于以下技术栈来展开:

- 语言: Java8
- 框架: SpringBoot
- 构建工具: Gradle
- WebDriver: Selenium
- BDD框架: Cucumber
- 单元测试框架: Junit

## 搭建基本工程
&emsp;&emsp;在开始之前，首先确保你的机器上安装了[Java8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)。<br/>
&emsp;&emsp;Spring Boot作为Spring新一代的框架，提升了Spring开发者的开发体验，这里原则上并不一定需要使用Spring Boot。[Gradle]((http://gradle.org/))作为一个自动化构建工具，也极大的改善的开发者构建工程时的体验。<br/>
&emsp;&emsp;可以使用Spring的初始化工具自动生成基本的项目文件，首先进入[start.spring.io](https://start.spring.io/)，把Maven Project改为Gradle Project，Group和Artifact名字可以更改成你自定义的名字，如下图：
<center><img class="center" src="{{ site.url }}/images/2016/cucumber01.png" alt="smudge.png"></center>
&emsp;&emsp;由于这个初始化工具并没有提供什么Cucumber相关的依赖，所以我们并不需要在Dependencies中添加任何东西。然后点击“Generate Project”，它就会自动生成基于SpringBoot和Gradle的初始项目，并打包成zip自动通过浏览器下载到本地。<br/>
&emsp;&emsp;然后解压该zip包，在命令行(工作路径为解压后的工程根目录)中输入:

```
./gradlew clean build
```

&emsp;&emsp;当然，如果你本地安装了Gradle,你也可以使用：

```
gradle clean build
```
&emsp;&emsp;然后gradle就会帮你在本地机器上安装好一些基本的依赖（包括Junit），构建好了接下来需要的基本工具。你可以使用诸如Intellij这样的IDE打开这个工程，并根据使用的IDE来更改 ___build.gralde___ 文件中的部分plugin。

## Selenium Test
&emsp;&emsp;在写Cucumber Test之前，我们先了解一下如何写Selenium Test。<br/>
&emsp;&emsp;[Selenium](http://www.seleniumhq.org/)是一个WebDriver框架，支持驱动各个平台的各种常用浏览器，最早由[ThoughtWorks](https://thoughtworks.com)开发。通俗点说，它可以实现对浏览器上中的各个元素的操作动作（包括点击，填写等）的自动化，所以它可以被用于各大BDD框架/自动化测试工具中，其中就包括[Cucumber](https://cucumber.io/)，[Jbehave](http://jbehave.org/)，[SauceLabs](https://saucelabs.com/)等。<br/>
&emsp;&emsp;所以我们先使用Selenium写一个简单的行为级别的测试，然后再使用Cucumber将它们转化更加服务BDD思想的测试。这里，就以我的博客 ___{{site.url}}___ 为例，将接下来的几个行为自动化了：

 1. 进入{{site.url}}这个网页
 2. 点击右边的搜索按钮
 3. 在输入框中输入要搜索的关键字“PowerShell”
 4. 点击搜索出来的第一个结果，进入该篇文章页面

&emsp;&emsp;在写代码之前，你需要在 ___build.gradle___ 中的dependencies中添加selenium这个依赖:

```
compile('org.seleniumhq.selenium:selenium-java:2.53.0')
```

&emsp;&emsp;接下来需要重新执行一遍之前的build指令(./gradlew clean build)，然后你需要在YourProject/src/main/java/com/wenjie下创建一个新的类（名为BlogPage），用于表示页面下的对应元素以及对其的操作，代码如下：

```
package com.wenjie;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BlogPage {
    @FindBy(xpath = "//*[@id=\"masthead\"]/button")
    private WebElement searchButton;

    @FindBy(xpath = "/html/body/div[1]/div/input")
    private WebElement searchInput;

    @FindBy(xpath = "/html/body/div[1]/div/ul/li[1]/article/a")
    private WebElement searchResultFirst;

    public void clickSearchButton() {
        searchButton.click();
    }

    public void inputSearchWording(String wantedStr){
        searchInput.sendKeys(wantedStr);
    }

    public void clickFirstResultOfSearch() {
        searchResultFirst.click();
    }
}
```

&emsp;&emsp;可以看到在BlogPage中定义了三个 ___WebElement___，它们分别是搜索按钮，搜索输入框，以及搜索结果中的第一个（它们都是通过xpath或者id的形式定义），并且通过三个方法将对这三个元素的点击和输入操作暴露出去。接下来就可以新建一个新的测试类（也可以基于原有的测试类），并编写一个对应的测试，如下：

```
package com.wenjie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.openqa.selenium.support.PageFactory.initElements;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CucumberApplication.class)
public class CucumberApplicationTests {
	FirefoxDriver driver = new FirefoxDriver();
	BlogPage blogPage = initElements(driver, BlogPage.class);
	String blogUrl = "{{site.url}}";

	@Test
	public void search_function_should_work_well() {
		driver.get(blogUrl);

		blogPage.clickSearchButton();
		blogPage.inputSearchWording("PowerShell");
		blogPage.clickFirstResultOfSearch();
	}

}
```
从上面的代码很容易看出来这个测试干了什么:

- 打开{{site.url}} - driver.get(blogUrl);
- 点击搜索按钮 - blogPage.clickSearchButton();
- 输入关键字 - blogPage.inputSearchWording("PowerShell");
- 点击搜索的第一个结果 - blogPage.clickFirstResultOfSearch();

最后，执行这个测试:

```
./gradlew test
```

&emsp;&emsp;你将看到这个测试会调用firefox浏览器（要换成Chrome或者其他浏览器怎么办？），并自动地执行了刚刚的那些操作，是不是很炫酷？至此，这个简单的Selenium Test就完成了，你也可以模仿这样的写法，去自动化的操作其他的网页。<br/>
&emsp;&emsp;接下来，将会讲述如何基于此写一个简单的Cucumber Test，请戳[本文下半部分]({{site.url}}/编程相关/cucumber-test-part-2)。
