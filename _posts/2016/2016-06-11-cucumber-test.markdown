---
layout: "post"
title: "从头写一个Cucumber测试"
description: “Writing Cucumber Test”
date: "2016-06-11 22:36"
published: true
comments: true
category: 编程相关
tags: [Cucumber, Test, SpringBoot, Selenium]
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

## 搭建基本工程
&emsp;&emsp;在开始之前，首先确保你的机器上安装了[Java8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)。<br/>
&emsp;&emsp;可以使用Spring的初始化工具自动生成基本的项目文件，首先进入[start.spring.io](https://start.spring.io/)，把Maven Project改为Gradle Project，Group和Artifact名字可以更改成你自定义的名字，如下图：
<center><img class="center" src="{{ site.url }}/images/2016/cucumber01.png" alt="smudge.png"></center>
由于这个初始化工具并没有提供什么Cucumber相关的依赖，所以我们并不需要在Dependencies中添加任何东西。然后点击“Generate Project”，它就会自动生成基于SpringBoot和Gradle的初始项目，并打包成zip自动通过浏览器下载到本地。<br/>

然后解压该zip包，在命令行(工作路径为解压后的工程根目录)中输入:

```
./gradlew clean build
```

当然，如果你本地安装了[Gradle](http://gradle.org/),你也可以使用：

```
gradle clean build
```
然后gradle就会帮你在本地机器上安装好一些基本的依赖。

## Selenium Test
&emsp;&emsp;在写Cucumber Test之前，我们先了解一下如何写Selenium Test。<br/>
&emsp;&emsp;[Selenium](http://www.seleniumhq.org/)是一个WebDriver框架，支持驱动各个平台的各种常用浏览器，最早由[ThoughtWorks](https://thoughtworks.com)开发。通俗点说，它可以实现对浏览器上中的各个元素的操作动作（包括点击，填写等）的自动化，所以它可以被用于各大BDD框架/自动化测试工具中，其中就包括[Cucumber](https://cucumber.io/)，[Jbehave](http://jbehave.org/)，[SauceLabs](https://saucelabs.com/)等。

所以我们先使用Selenium写一个简单的行为级别的测试，然后再使用Cucumber将它们转化更加服务BDD思想的测试。
