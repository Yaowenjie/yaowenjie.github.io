---
layout: "post"
title: "如何调试JEST测试？"
date: "2016-08-24 11:26"
published: false
comments: true
tags: [JEST, react, debug]
category: Front-end
imagefeature: wj/10.jpg
---
##### 本文翻译自[liusy182](https://liusy182.wordpress.com/2015/03/12/jest-how-do-you-debug-it/)

&emsp;&emsp;[Jest框架](https://facebook.github.io/jest/)是facebook旗下一款单元测试框架，我个人十分喜欢它，因为它自动mock这一点十分强大。然而，当它遇到一些问题的时候，就会经常抛出一些模糊的调用栈信息。我在网上搜索尝试找到如何debug一个Jest测试的方法，却很难找到有用的信息。总之，它仍然还是一个比较新的框架。

&emsp;&emsp;Jest使用虚拟DOM来运行测试。这一点不同于Karma和Jasmine（它们是利用浏览器来运行测试的）。
