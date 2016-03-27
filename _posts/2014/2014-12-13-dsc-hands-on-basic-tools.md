---
layout: post
title: 'Windows Powershell DSC实践（一）| 认识及工具准备'
date: 2014-12-13 00:07
comments: true
category: PowerShell
tags: [powershell, DSC, vagrant, Chocolatey, 自动化部署, virtualbox]
imagefeature: wj/16.jpg
---
## DSC? Who are you？| 什么叫DSC
<hr/>
&emsp;&emsp;DSC（Desired Stated Configuration）是微软2013年发布的PowerShell4.0携带的一项新功能，稍微检索了一下，貌似没有发现统一标准的中文译名，google翻译给的直译结果是“理想状态配置”，嗯嗯，靠谱。<br/>
<!--more-->
<center><img class="center" src="{{ site.url }}/images/2014-15/powershell4.jpg" alt="powershell4.jpg"></center>

&emsp;&emsp;DSC提供了对Powershell语言的扩展、新的Cmdlet，以及可以用来配置各种软件环境的Resource。最大的好处是用户可以根据自身的需求编写自己量身定制的Resource，编写用户定制的具体流程将在之后的博客中展示。 <br/>
&emsp;&emsp;DSC可以干什么？部署新的软件，启动和关闭服务与进程，管理User和Group、环境变量、文件与目录、注册表设置，甚至可以运行powershell脚本，还有等等等等。但是可能发布时间不长，物有所长，必有所短，在使用过程中还是发现诸多小问题，这也将在之后讨论。[  欲了解更多DSC官方信息，请戳这儿.](http://technet.microsoft.com/en-us/library/dn249912.aspx) <br/>


&emsp;&emsp;好了！说了这些，看了官方文档，你是否对DSC有了一定了解？ <br/>
&emsp;&emsp;什么？！还是不知道干什么？感兴趣那就一起来做几份套餐吧！ <br/>
&emsp;&emsp;首先设想我们身处一个基于windows开发测试平台的中型或者大型项目之中，目前只有一堆空荡荡的机器（并且可以升级powershell至4.0的）和几个拥有一堆Resource（文件、程序等等）的服务器，经理要让你配置几台机器给QA用于测试，配置几台给Dev开发，配置几台用作流水线上的其他工作。。。这时候，你肯定不能把机器搬过来，插上电源，启动就完事了（很多时候这些步骤都走完了）。要你做的是了解不同用途的机器需要安装什么样的程序，需要什么样的环境和文件等等，然后利用你的知识实现自动部署上去。这时候你就可以站起来坚定地说：我们可以试试DSC！对，如果这儿是餐厅，DSC就是配菜师，帮你搭配好几份套餐用于满足不同客户群体的需求。 <br/>

## Tools I used | 辅助工具
<hr/>
&emsp;&emsp;没有windows操作系统的电脑？没关系，用虚拟机搭建，这里我们使用支持多种平台的VirtualBox来搭建虚拟的搭载windows系统的机器。然后，推荐使用与之搭配的工具Vagrant，更好的管理虚拟机。这样你就可以随意的在你搭建的虚拟机上肆意的开发和调试了（话有点过了，还是没有那么随意的），一旦你的目的达成就可以销毁这台虚拟机了（是不是有点过河拆桥的感觉，猿类，就是这么任性！）。 <br/>
&emsp;&emsp;另外，为了像Linux平台上用yum或apt-get那样的软件包管理器来优雅的安装程序，再推荐一款不错的针对windows平台的包管理器，它的名字叫Chocolatey。你可以从Chocolatey.org的官网上直接下载安装包，也可以指定自己的软件包地址。后面很多软件包都可以通过它来实现安装，比如powershell4.0的安装就可以通过如下两个简单命令(其实两者意思相同)中任一个来实现:<br/>

``` powershell
cinst powershell4
chocolatey install powershell4
```

&emsp;&emsp;当然，在这里就需要我们把它包装成DSC的一个Resource，这个后续再述。
<hr/>
&emsp;&emsp;这三款工具还有个最大的好处，那便是开源、免费。想了解以上三种工具的详细信息，请关注以下：

>
>    [了解Virtual Box，请戳这里](http://www.virtualbox.org)
>
>    [了解Vagrant，请戳这里](http://www.vagrantup.com/)
>
>    [了解Chocolatey，请戳这里](https://chocolatey.org)
