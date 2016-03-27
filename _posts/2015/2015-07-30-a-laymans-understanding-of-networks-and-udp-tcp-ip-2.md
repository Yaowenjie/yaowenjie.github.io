---
layout: post
title: '一个门外汉对网络和UDP/TCP/IP的理解（2）'
date: 2015-07-30 16:14
comments: true
category: 网络基础
tags: [IP地址, DNS, 域名解析, 域名服务器]
imagefeature: wj/20.jpg
---
###### 本文源自对文章[Layman’s understanding of Networking & UDP/TCP/IP（点击进入原文）](http://www.microshell.com/sysadmin/networking/laymans-understanding-of-networking-udptcpip/2//)的翻译。


## 域名是什么
- - -
&emsp;&emsp;我希望我上面的解释还是比较清楚的。只是上面我没有提到或者强调地址，因为我觉得很明显，当我们发一封邮件时，知道收信人的人姓名及地址信息是必须的。
&emsp;&emsp;因为计算机网络就像邮局的作用一样，所有的数据报必须有一个地址（在UDP中）。在TCP中，你需要一个地址去建立连接。在计算机中，所有的地址几乎都是数字的组合。然而我们人类更喜欢名字。因此，这儿明显有冲突。所以接下来就会介绍域名系统（DNS）。

<!--more-->

&emsp;&emsp;域名系统（DNS）可以看成地址解析服务。你给它一个名字，它就会通过电脑返回一个你需要的地址，来完成某些事情。换句话说，它就像一个通讯录。你可能不知道某个人的电话号码，但是你知道他的名字。在通讯录中，你可以将名字和电话号码联系起来。同样地，在因特网中，你将域名和IP地址联系起来。


### 域名注册
&emsp;&emsp;在你通讯录中，詹姆斯的电话是415-555-1234，但在别人的通讯录红，詹姆斯的电话号码可能是510-123-4567。如果你只用你自己的通讯录，别人用别人的通讯录，就没有问题。然而在因特网里，不同的地址之间却不能有一样的名字。因此，他们创建了一注册服务。这是你注册一个和其他已存在的名字（就像上面的詹姆斯）不冲突的名字最基本的方式。为此，你需要给提供该服务的公司支付一定的费用。我喜欢的一个公司就叫做[GoDaddy](https://www.godaddy.com/).

### 域名服务器及将域名解析为地址
&emsp;&emsp;之前浏览网页的时候，你是否看到过Address Not Found的问题？接下来，我将会给你解释为什么发生了这个。
<div style="text-align: center">
	<img src="http://www.microshell.com/wp-content/uploads/2009/01/addressnotfound-500x272.gif" style="display:inline"/>
</div>
&emsp;&emsp;比如说，当你在浏览器内输入域名的时候，你的电脑会尝试去把它解析为一个等效的IP地址，这样它才可以连接上。下面就是计算机在创建连接时背后会做的事情：
1. 查看域地址是否在resolv.conf文件中列出来了。你可以把resolv.conf看成你自己的私人通讯录。该文件总是第一个被查询。
2. 如果它不在resolv.conf文件中，就会按照你的设置查询DNS服务器内指定位置。DNS代表域名系统。就是那个你提供域名给它，它就会返回这个域名对应的IP地址的系统。
3. DNS返回IP地址。
4. 你的电脑获取IP地址，然后它就可以连接到该地址。

&emsp;&emsp;现在，你知道了你的电脑创建连接的先决条件，那么你就可有在你遇到上面的Address Not Found错误时更加深入地调试你的连接了。这或许是因为你的网络断了。也或许是这个域地址真的没有一个对应的IP地址，又或者是你的DNS服务器挂了。
<div style="text-align: center">
	<img src="http://www.microshell.com/wp-content/uploads/2009/02/30kviewdns.gif"/>
</div>

&emsp;&emsp;我们假设你想去www.microshell.com这个网站。首先，你的电脑需要获取到www.microshell
.com的IP地址，然后你的网页浏览器才可以和服务器创建连接，并且下载该页面。

1. 首先，你的电脑会询问你的DNS服务器是否知道www.microshell.com。
2. 有可能，你的DNS服务器不认识www.microshell.com。所以它会转发这个请求到下一级域名，com根域。
3. com根域不知道www.microshell.com。但是它知道Microshell的DNS服务器知道www.microshell.com。所以它告诉你的DNS服务器Microshell的DNS服务器知道这个域名。
4. 然后你的DNS服务会询问Mircoshell的DNS服务器www.microshell.com的内容。
5. Mircoshell的DNS服务器认识www.microshell.com，并且返回对应的IP地址。
6. 你的DNS紧接着就会返回从Mircoshell DNS服务器获取的IP地址。
7. 这样，我们就将www.microshell.com解析成了对等的IP地址。
