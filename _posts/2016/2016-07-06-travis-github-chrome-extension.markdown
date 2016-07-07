---
layout: "post"
title: "【Chrome插件】github+travis v2"
description: "[Chrome Extension] github+travis v2"
date: "2016-07-06 17:47"
published: false
comments: true
---
### 太长不读版
&emsp;&emsp;最近自己写了一个Chrome浏览器扩展/插件（[chrome web store](https://chrome.google.com/webstore/detail/github%2Btravis-v2/ekkfhiophiaakmeppcnkblpbbjlnlnmh)， [github repo](https://github.com/Yaowenjie/travis-github-chrome-extension)），该插件为github上的仓库提供travis-ci运行状态和运行时间的 __图表__，你可以直观地了解到哪些仓库（自己或者别人）开通了travis-ci的build，还可以看到特定repo近十次build的 __时间和状态变化__，并且点击tooltip还可以进入特定的travis build页面。<br/>

<!--more-->
<hr/>

## 背景

## 基本简介
<center><img class="center" src="{{ site.url }}/images/2016/travis0.jpg" alt="travis.png"></center>
<center><img class="center" src="{{ site.url }}/images/2016/travis1.png" alt="travis.png"></center>
<center><img class="center" src="{{ site.url }}/images/2016/travis2.png" alt="travis.png"></center>

## 安装
安装该插件，你可以直接点击[该链接](https://chrome.google.com/webstore/detail/github%2Btravis-v2/ekkfhiophiaakmeppcnkblpbbjlnlnmh)，或者访问chrome web store 并搜索“__github travis v2__”关键字，然后添加你的chrome浏览器即可。
如果你无法访问chrome web store，请下载[本repo最新的release版本](https://github.com/Yaowenjie/travis-github-chrome-extension/releases)，解压，然后在Chrome浏览器开发者模式下手动添加该插件（见上文"How to install it in Developer mode"）。

<!--more-->

## 简介
该chrome插件为在github上的repo提供travis-ci运行状态和运行时间的图标（见上图1、2），你可以直观的了解到哪些repo（自己或者别人）开通了travis-ci的build，还可以看到特定repo近十次build的时间和状态变化，并且点击tooltip还可以进入特定的travis build页面。

## 反馈
欢迎在[github issue](https://github.com/Yaowenjie/travis-github-chrome-extension/issues)提出你的问题和建议。
