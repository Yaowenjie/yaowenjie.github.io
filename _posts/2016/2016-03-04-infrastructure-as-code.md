---
layout: post
title: '[译]基础设施即代码'
description: Infrastructure As Code
date: 2016-03-04 17:53
comments: true
category: DevOps
tags: [InfrastructureAsCode, 基础设施即代码, 基础设施, 持续交付]
share: true
imagefeature: wj/17.jpg
---

###### 本文翻译自老马(Martin Fowler)的[博客](http://martinfowler.com/bliki/InfrastructureAsCode.html)，该译文现已被[博客原文](http://martinfowler.com/bliki/InfrastructureAsCode.html#footer)收录在其下方翻译处。

<br/>
&emsp;&emsp;“基础设施即代码”是一种通过代码来定义计算和网络基础设施的方法，它可以应用于任何软件系统中。这样的代码放在代码版本控制系统中，具有可审查性、可重用性，并且符合测试惯例，还完全遵从持续交付的原则。该方法已经在过去的十年内广泛应用于快速增长的云计算平台中了，而且也将会成为接下来管理计算机基础设施的主要方式。

<!--more-->

<center><img class="center" src="http://martinfowler.com/bliki/images/infrastructureAsCode/sketch.png" alt="未标题-2.jpg"></center>

&emsp;&emsp;我（Martin）出生在Iron Age时期（喻指软件开发起步早期），那时发布一款新的服务器应用程序，意味着需要找一些实际可以运行它的硬件，并且配置硬件使其支持应用需求，以及把应用程序部署到硬件上。而得到这些硬件的成本往往很高，而且获取它们的周期也很长，通常需要几个月的时间。但是现在我们生活在云的时代，只要有网络连接和一张信用卡，几秒钟就可以启动一台新的服务器。这些动态的基础设施实际上是通过一些程序命令来创建服务器（通常是虚拟机，当然也可以是裸机），并可以对它们进行分区、销毁，完成这些你甚至都不需要动用一个螺丝刀。

## 实践惯例
&emsp;&emsp;基础设施即代码基于以下一些实践惯例：

- __使用解释型文件：__ 所有的配置信息都被定义于可执行的配置解释文件中，比如说shell脚本，ansible的playbooks，Chef的recipes，或者Puppet的manifests。人们无需登入服务器，就可以做出实时的动态调整。开发时，这些代码可以作为持续的定义，来减少任何生成[雪花服务器（SnowflakeServer）](http://martinfowler.com/bliki/SnowflakeServer.html)的风险。这也意味着更新代码的频率要快。幸运的是，计算机可以快速执行程序，并可以准备数百个服务器，速度比任何人类都快。
- __自归档的系统和过程：__ 相比于人们使用文档中的指示来执行操作（人工级别的可靠性），代码更加清晰准确并且可以不断的被执行。而且如果有必要的话，根据这些代码也可以生成一些更具有可读性的文档。
- __给所有代码做版本控制：__ 要让所有的代码都处于版本控制之中。这样每次配置以及每个修改都会有记录可以查询的到，而且还可以利用[可重用的构建（ReproducibleBuild）](http://martinfowler.com/bliki/ReproducibleBuild.html)来诊断问题。
- __持续地测试系统和过程：__ 测试可以帮助计算机快速地找到基础设施配置中的诸多错误。在现代软件系统中，你可以搭建用于基础设施代码的“[部署流水线](http://martinfowler.com/bliki/DeploymentPipeline.html)”，这能够让你实践针对基础设施变化的[持续交付](http://martinfowler.com/bliki/ContinuousDelivery.html)流程。
- __小步改变，避免批处理：__ 基础设施改变的动作越大，就越可能包含错误，也更难去诊断错误，特别是如果有多个错误交织在一起。小步更新就会让发现和改正错误更加容易。所以频繁地修改基础设施（小步）可以减少这种事情的发生。
- __保证服务持续可用：__ 持续增长的系统负担不起更新或者修复时的宕机。一些技术，比如说[蓝绿部署](http://martinfowler.com/bliki/BlueGreenDeployment.html)和[ParallelChange](http://martinfowler.com/bliki/ParallelChange.html)，可以不失可用性地进行小步的更新。


## 益处：
&emsp;&emsp;所有这些都让我们可以利用动态的基础设施来简单地搭建新的服务器，以及安全地处理那些被新的配置代替或者配置重新加载的服务器。只需要执行一个脚本完，就可以成所需的所有服务器的搭建。这种方法非常适合凤凰服务器([PhoenixServers](http://martinfowler.com/bliki/PhoenixServer.html))和不可变服务器([ImmutableServers](http://martinfowler.com/bliki/ImmutableServer.html)）。 <br/>
&emsp;&emsp;用代码来定义服务器配置意味着服务器之间具有更高的一致性。人为的操作会根据不准确的指导（先不管其中有没有错误）产生不同的理解，导致产生具有细微不同配置的雪花（snowflakes，同前文snowflakeServer），也会经常出现一些稀奇古怪、难以调试的错误。不一致的监控往往会让出错的情况更加糟糕，而使用代码也保证了监控的一致性。 <br/>
&emsp;&emsp;最重要的是，使用配置性代码可以使改变更加安全，能够用很小的风险来升级应用程序和系统软件。错误可以很快地被发现和修复，至少修改能够退回到上一次有用的配置状态上。<br/>
&emsp;&emsp;将基础设施代码版本控制，有助于提升代码的可塑性和可审查性。配置中的每一次更改都会被记录，不容易受到错误的纪录所影响。<br/>
&emsp;&emsp;如果你正计划使用微服务架构，那这些就很重要了，因为你需要处理更多的服务器，实现“基础设施即代码”就显得尤其必要。“基础设施即代码”技术针对大型集群服务器十分地有成效，包括配置服务器以及制定它们之间交互的方式。
