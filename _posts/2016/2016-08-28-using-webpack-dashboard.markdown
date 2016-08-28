---
layout: "post"
title: "使用优雅的webpack命令行工具:webpack-dashboard"
date: "2016-08-28 22:55"
published: false
comments: true
tags: [webpack, dashboard, 命令行]
category: Front-end
imagefeature: wj/10.jpg
---
&emsp;&emsp;webpack-dashboard是用于改善开发人员使用[webpack](http://webpack.github.io/)时控制台用户体验的一款工具。它摒弃了webpack（尤其是使用dev server时）在命令行内诸多杂乱的信息结构，为Webpack在命令行上构建提供了一目了然的仪表盘(dashboard)，其中包括**构建过程**和**状态**、**日志**以及涉及的**模块列表**。使用它，你就可以更加优雅的使用webpack构建你的代码。

&emsp;&emsp;另外，它自开源以来短短半个月，就已经在github上收获了6000多枚star。
<center><img class="center" src="{{ site.url }}/images/2016/wd1.png" alt="star.png"></center>

<!--more-->
## 它是什么
&emsp;&emsp;简单地说，[webpack-dashboard](https://github.com/FormidableLabs/webpack-dashboard)就是把原先你使用webpack时（特别是使用webpack dev server时）命令行控制台打印的日志：
<center><img class="center" src="{{ site.url }}/images/2016/wd2.png" alt="console.png"></center>

&emsp;&emsp;转换成了这样：
<center><img class="center" src="{{ site.url }}/images/2016/wd3.png" alt="dashboard.png"></center>

&emsp;&emsp;看到这里，是不是觉得整个人生都变美好了呢。仔细看，这个dashboard里面按日志(Log)、状态(Status)、运行(Operation)、过程(Progess)、模块(Modules)、产出(Assets)这6个部分将信息区分开来。用官方的话，这将会给你一种在NASA工作的即使感，哈哈。

## 如何使用
&emsp;&emsp;其实安装和使用webpack-dashboard的过程非常简单，首先使用npm本地安装它：

```js
npm install webpack-dashboard --save-dev
```

```js
var Dashboard = require('webpack-dashboard');
var DashboardPlugin = require('webpack-dashboard/plugin');
var dashboard = new Dashboard();
```

```js
new DashboardPlugin(dashboard.setData)
```

## 最后

- OS X Terminal.app users: Make sure that View → Allow Mouse Reporting is enabled
- 非webpack dev server启动的server（如express），log可能会越界
- github repo
- 本文示例代码
