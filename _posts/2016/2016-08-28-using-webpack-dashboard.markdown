---
layout: "post"
title: "使用优雅的webpack命令行工具:webpack-dashboard"
date: "2016-08-28 22:55"
published: true
comments: true
tags: [Webpack, Dashboard, 命令行]
category: Front-end
imagefeature: 2016/wd3.png
---
&emsp;&emsp;webpack-dashboard是用于改善开发人员使用[webpack](http://webpack.github.io/)时控制台用户体验的一款工具。它摒弃了webpack（尤其是使用dev server时）在命令行内诸多杂乱的信息结构，为webpack在命令行上构建了一个一目了然的仪表盘(dashboard)，其中包括**构建过程**和**状态**、**日志**以及涉及的**模块列表**。有了它，你就可以更加优雅的使用webpack来构建你的代码。

&emsp;&emsp;另外，它自开源以来短短半个月，就已经在github上收获了6000多枚star，足见人们对于提升开发工具的用户体验有着巨大的需求。
<center><img class="center" src="{{ site.url }}/images/2016/wd1.png" alt="star.png"></center>

<!--more-->

## 它是什么

&emsp;&emsp;简单地说，[webpack-dashboard](https://github.com/FormidableLabs/webpack-dashboard)就是把原先你使用webpack时（特别是使用webpack dev server时）命令行控制台打印的日志：
<center><img class="center" src="{{ site.url }}/images/2016/wd2.png" alt="console.png"></center>

&emsp;&emsp;转换成了这样：
<center><img class="center" src="{{ site.url }}/images/2016/wd3.png" alt="dashboard.png"></center>

&emsp;&emsp;看到这里，是不是觉得整个人生都变美好了呢。仔细看，这个dashboard里面按日志(Log)、状态(Status)、运行(Operation)、过程(Progess)、模块(Modules)、产出(Assets)这6个部分将信息区分开来。用官方的话，这将会给你一种在NASA工作的即使感，哈哈。

## 如何使用
&emsp;&emsp;其实安装和使用webpack-dashboard的过程非常简单，首先使用npm本地安装它，到你基于webpack的前端项目上：

```js
npm install webpack-dashboard --save-dev
```
&emsp;&emsp;如果你利用webpack-dev-server启动了一个server，而不是express的话，可以直接在**webpack.config.js**里面初始化dashboard。

&emsp;&emsp;首先，导入dashboard和其对应的插件，并创建一个dashboard的实例：

```js
var Dashboard = require('webpack-dashboard');
var DashboardPlugin = require('webpack-dashboard/plugin');
var dashboard = new Dashboard();
```

&emsp;&emsp;然后，在对应的**plugins**里面添加DashboardPlugin：

```js
plugins: [
  new DashboardPlugin(dashboard.setData)
]
```

&emsp;&emsp;最后，你需要让dev server在静默的状态中启动（主要是为了去掉多余的日志），要实现这一点，你可以像官方的做法那样，在WebpackDevServer的构造函数里添加 **quiet: true**。

```js
new WebpackDevServer(
  Webpack(settings),
  {
    publicPath: settings.output.publicPath,
    hot: true,
    quiet: true, // lets WebpackDashboard do its thing
    historyApiFallback: true,
  }
).listen(
```

&emsp;&emsp;当然，你也可以在npm的script里面启动dev server时添加**quiet**选项（我在尝试的时候选择这种简单的方式）。

```js
"scripts": {
  "start": "webpack-dev-server --quiet"
},
```

&emsp;&emsp;这样，你就可以运行诸如**npm start**这样的命令启动你的server。然后，你就可以休息一下，泡杯咖啡，假装自己就是一位宇航员，静静地看着你的dashboard。

&emsp;&emsp;如下图所示，为笔者尝试时的截图：
<center><img class="center" src="{{ site.url }}/images/2016/wd4.png" alt="dashboard.png"></center>


## 最后
&emsp;&emsp;本文只介绍了基于webpack-dev-server的这一种使用情况，其他启动server的方式（比如express）或者其他情况可以参考[webpack-dashboard github官方仓库](https://github.com/FormidableLabs/webpack-dashboard)。

&emsp;&emsp;webpack-dashboard目前还处于初期阶段，所以必然还有一些值得注意或者值得改进的地方。如果你使用的是OS X自带的终端(Terminal)，需要确认“View → Allow Mouse Reporting”是使能(Enable)状态，如果你的终端没有这个功能的话，你或许可以尝试一下[iTerm2](https://www.iterm2.com/index.html)。另外，如果你忘记使用quiet模式或者你的某句日志或者名字过长，可能会导致显示的字符串“越界”，跑到另一个区域，看起来没有那么直接美观了。

&emsp;&emsp;最后，如果你想简单的看一下webpack-dashboard启动起来的效果，你可以参考使用[本文示例代码](https://github.com/Yaowenjie/React-learning/tree/master/lesson1)。
