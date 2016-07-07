---
layout: "post"
title: "【Chrome插件】github+travis v2"
description: "[Chrome Extension] github+travis v2"
date: "2016-07-06 17:47"
published: true
comments: true
---
### 太长不读版
&emsp;&emsp;最近自己写了一个Chrome浏览器扩展/插件（[github repo](https://github.com/Yaowenjie/travis-github-chrome-extension)），并将其发布到了谷歌官方商店（[chrome web store](https://chrome.google.com/webstore/detail/github%2Btravis-v2/ekkfhiophiaakmeppcnkblpbbjlnlnmh)），该插件为github上的仓库提供travis-ci运行状态和运行时间的 __图表__，你可以直观地了解到哪些仓库（自己或者别人的）开通了travis-ci的build，还可以看到特定repo近十次build的 __时间和状态变化__，并且点击tooltip还可以进入特定的travis build页面。<br/>

<!--more-->
<hr/>

## 背景
&emsp;&emsp;前段时间开始用[travis-ci](http://travis-ci.org)来自动化运行[一个简单的功能测试](https://github.com/Yaowenjie/yaowenjie.github.io/tree/master/cucumber-test)，感觉travis-ci简洁易用的界面还是比较适合CI入门，但是用起来发现几点不是特别方便之处：

  - 每次都需要到travis-ci的页面才能知道build最新的状态
  - 想看前几次的build状态，还要点击"build history"到对应的页面查看，并且近几次的build信息个人感觉并不直观。
  - 又一次更改CDN之后，build（跑测试）的时间变化反而增长了，如果不盯着“build history”页面上的build time，很难直接知道构建时间的变化。
  - 我会经常访问github，但不是每次提交都上travis看状态，或者坐等failed邮件发到我的邮箱。

&emsp;&emsp;所以就萌发了做一个chrome插件的想法，我期望这个chrome插件可以做到以下几件事情：

  - 在github上显示开通travis-ci服务的repo（仓库）当前的状态，不管是自己的还是别人的repo。
  - 在每个repo内显示最近10次的build的状态变化。
  - 在每个repo内显示最近10次的build的时间变化。
  - 当我想查看具体build的具体信息时，能够点击进入对应的travis-ci页面。

&emsp;&emsp;经过两三天零碎时间的摸索，借鉴别人的插件思路，还是整出来了这个：[github+travis v2](https://chrome.google.com/webstore/detail/github%2Btravis-v2/ekkfhiophiaakmeppcnkblpbbjlnlnmh)，并将其发布在chrome的官方商店，欢迎大家免费使用哈。

## 基本简介
&emsp;&emsp;该插件为github上的仓库提供travis-ci运行状态和运行时间的 __图表__，你可以看到特定repo近十次build的 __时间和状态变化__，并且点击tooltip还可以进入特定的travis build页面。如下图所示：
<center><img class="center" src="{{ site.url }}/images/2016/travis0.jpg" alt="travis.png"></center>

&emsp;&emsp;还可以直观地了解到哪些仓库（自己或者别人的）开通了travis-ci的build，以及它们的最新状态，点击build按钮还可以进入对应的travis页面：
<center><img class="center" src="{{ site.url }}/images/2016/travis1.png" alt="travis.png"></center>

## 安装
&emsp;&emsp;安装该插件，你可以直接点击[该链接](https://chrome.google.com/webstore/detail/github%2Btravis-v2/ekkfhiophiaakmeppcnkblpbbjlnlnmh)，或者访问chrome web store 并搜索“__github travis v2__”关键字，然后添加你的chrome浏览器即可。<br/>
&emsp;&emsp;如果你无法访问chrome web store，请下载[github repo最新的release版本](https://github.com/Yaowenjie/travis-github-chrome-extension/releases)，解压后，在Chrome浏览器开发者模式下手动添加该插件（如下图所示）。
<center><img class="center" src="{{ site.url }}/images/2016/travis2.png" alt="travis.png"></center>

## 最后
- 欢迎大家star/fork该[github repo](https://github.com/Yaowenjie/travis-github-chrome-extension)，接受有益的Pull Request。
- 欢迎在[github issue](https://github.com/Yaowenjie/travis-github-chrome-extension/issues)提出你的问题和建议。
