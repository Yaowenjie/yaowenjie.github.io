---
layout: post
title: 'Windows PowerShell DSC实践 - Ultimate'
date: 2015-07-30 11:49
comments: true
category: PowerShell
tags: [powershell, DSC, TheDSCBook, penflip, 自动化配置管理]
imagefeature: wj/16.JPG
---
## 续言
- - -
  &emsp;&emsp;距离上一次编写博客已经有一段时间了,DSC实践的很多内容，包括很多细节的总结和问题解决方案都没有说到，接下来我也可能也不会花过多时间在此上，该篇博客我将简单总结一下DSC的实现机制和脚本编写的内容，然后会介绍我在实践过程中遇到的一本不错的关于DSC的英文指南，以及我的中文翻译版，最后简述一下个人对DSC未来的看法。如果你在使用或者探索DSC的过程中遇到任何问题，欢迎和我一起探讨，我的邮箱地址：[wjyao@thoughtworks.com](wjyao@thoughtworks.com) .

## DSC的实现机制
- - -
  &emsp;&emsp;DSC本质上类似PowerShell的一个模块，所以DSC脚本实质也是一种PowerShell脚本。DSC脚本相比单纯的PowerShell脚本，它的好处在于它是声明性的，它包含了一个软件环境各个要素的所有基本信息，比如安装了10.9.8版本的chrome、IIS管理器启动、某个路径文件应该存在等等内容，所以更加易读，易管理。
  &emsp;&emsp;DSC部署的实现机制是：运行DSC部署脚本，PowerShell就会针对每一个目标机器生成一个托管对象格式（MOF）文件。这个MOF文件以某种方式传递到它们需要实际配置或者部署的机器上。最后，这些机器开始根据MOF文件的内容来调用对应的DSC 资源(Resource)，使自己与MOF文件所描述的内容匹配。
&emsp;&emsp;整个过程最重要的有三个部分：一是DSC配置脚本的编写，二是MOF文件的推送，三是DSC资源的编写与调用。
&emsp;&emsp;将MOF文件和要使用的DSC资源推送到它们的目标（待部署）机器上，有两种方式可以实现:
&emsp;&emsp;- Push模式是一种类似人工拷贝文件的形式，基于Windows 远程管理的远程（Remoting）协议来执行。
&emsp;&emsp;- Pull模式通过设定待部署机器录入到一个特殊的网页服务器上，也就是说通过http协议，目标机器主动抓取它们的MOF文件来实现。Pull模式也可以通过使用服务器消息块（SMB：Windows原生的文件共享协议）来实现从文件服务器上拉回MOF文件。可以看出来，它和Push模式最大的不同点是，目标机器会主动从一个服务器上拉取对应的MOF文件。

### DSC脚本
&emsp;&emsp;DSC配置脚本主要由三项组成：
- 主配置(Configuration)块，基本包含所有内容。
- 一个或者多个节点(Node)段落，根据机器名或者IP指定特定的目标机器，并且包含配置项。
- 每个节点段落都有一个或者多个配置项（由DSC资源定义的），用于指定你想那些节点（待部署机器）被配置成什么样。
&emsp;&emsp;其中，Configuration关键字包含整个配置块，Node声明了该段配置的目标机器或者机器群，具体的部署配置内容用各个DSC资源配置项来实现。

### DSC Resource
&emsp;&emsp;所有的DSC资源至少包含两个文件，一个.schema.mof格式文件，一个.psm1格式文件。前者用于声明资源的各个属性值；后者属于PowerShell模块，是具体的PowerShell脚本用于实现。在实际使用过程中，两者的关系有点类似C语言编程的头文件和源文件。
&emsp;&emsp;psm1文件内又主要包含三个函数：
&emsp;&emsp;- Get-TargetResource，
&emsp;&emsp;- Test-TargetResource，
&emsp;&emsp;- Set-TargetResource。
&emsp;&emsp;它们三者分别用于获取配置内容是否存在，测试配置的各个属性是否和对应脚本描述一致，以及根据获取、测试结果按属性设定进行相应配置。

## The DSC Book
- - -
&emsp;&emsp;在实际解决过程中，在[PowerShell.org](PowerShell.org)发现了一本不错的书---The DSC Book，里面对DSC的操作及原理做出了很多解释，也指出了很多问题的解决方案，甚喜。于是我借助空余时间把它翻译成了中文，并于原作者**Don Jones**联系，放在[Penflip](www.penflip.com)上分享，里面较为全面的介绍了DSC，感兴趣者可以参考本书，遇到问题的童鞋也可能在书内寻找到答案，甚至可以加入这本的维护团队中去（本书及原著都可能还会有些有误或者不准确之处，我们把他当做开源项目来做）。

> &emsp;&emsp;- [The DSC Book （中文版）](https://www.penflip.com/powershellorg/the-dsc-book-chinese)
>
> &emsp;&emsp;- [The DSC Book](https://www.penflip.com/powershellorg/the-dsc-book)

## 最后
- - -
&emsp;&emsp;DSC其实还有很多待完善的部分，但是确实是Windows环境下用于自动化配置管理，甚至是部署的一个不错的选择。微软在[WMF5.0预览版](http://blogs.msdn.com/b/powershell/archive/2014/11/18/windows-management-framework-5-0-preview-november-2014-is-now-available.aspx)中也增加大量了针对DSC的改善措施，可见DSC作为微软官方提供的声明配置技术，还是拥有很值得憧憬的应用价值的！
&emsp;&emsp;另外，推荐[pstips](http://www.pstips.net/)这个网站，适合学习PowerShell，也有很多DSC的内容。
