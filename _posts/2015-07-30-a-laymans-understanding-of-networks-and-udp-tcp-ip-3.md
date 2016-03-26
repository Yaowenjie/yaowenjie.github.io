---
layout: post
title: '一个门外汉对网络和UDP/TCP/IP的理解（3）'
date: 2015-07-30 17:50
comments: true
category: 网络基础
tags: [端口, port, 动机, 数据报]
imagefeature: wj/20.jpg
---
###### 本文源自对文章[Layman’s understanding of Networking & UDP/TCP/IP（点击进入原文）](http://www.microshell.com/sysadmin/networking/laymans-understanding-of-networking-udptcpip/3/)的翻译。

## 理解端口
- - -
&emsp;&emsp;既然你理解了两台电脑之间如何对话，那么接下来我们会进一步探讨这个。在大多数情况下，你会在同一个时刻有很多种网络连接。或许你正在网页上听音乐，又或许你是在163或者qq登陆电子邮箱浏览内容。对于所有的这些连接，一个数据包是如何知道它是指向哪个应用的呢？当然，你肯定不想在你浏览网页的中间看到音频数据包。谁知道那玩意是啥样的呢。

&emsp;&emsp;对，除了IP地址，你别玩了还有端口。把你的电脑想象成一所房子。这样你就可以把端口看成一个门，或者你房内的一个房间。你的房子有一个地址（一个外部IP地址）。每个数据包或者数据报将会有一个专门为其设计的端口。这样，各个数据包/报之间就不会混淆了。

<div style="text-align: center">
	<img src="http://www.microshell.com/wp-content/uploads/2009/02/networkports1-500x287.gif" style="display:inline"/>
</div>

&emsp;&emsp;[Yahoo Answer](https://answers.yahoo.com/question/index;_ylt=AvGU0rm83NB0FlaYS09OY3Xty6IX;_ylv=3?qid=20081109022946AA2yjMl&show=7#profile-info-dMHZlfslaa)里的一篇内容促使原作者写了这篇文章。原作者认为有必要写一个更加深入的回答，通过他足够清晰的解释，来让所有人都来理解计算机网络的工作原理。原作者也欢迎大家的评价/问题/批评，这将帮助他和其他读者更好的理解这些内容。
