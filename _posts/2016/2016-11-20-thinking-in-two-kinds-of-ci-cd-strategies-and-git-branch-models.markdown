---
layout: "post"
title: "关于两种CI/CD策略以及git分支模型的思考"
description: "Thinking in two CI/CD strategies and git branch models"
date: "2016-11-26 15:00"
published: false
comments: true
tags: [git, CD]
category: DevOps
imagefeature: wj/5.jpg
---

&emsp;&emsp;近两个月由于个人处于新环境、新项目的适应阶段，没怎么提笔写些文章。中间有好几个想法想记录下来分享，但受限于没有很好的时间段供自己总结思考（也可以总结为间歇性懒癌和剧癌发作），便啥也没有更新。借这个周末闲适的下午和明媚的阳光，决定把近来项目上的**CI/CD（持续集成/持续交付）策略**以及**git分支模型**和以前的项目做一下分析比较，希望对各位有所帮助，也能有所思考，尤其是那些期望**搭建项目部署流水线**或者想**了解git分支模型**的开发、运维人员。

<!--more-->

## 背景
&emsp;&emsp;废话不多说，由于近期做了N次release，所以对自己目前所处的新项目的部署方式有了一定的了解。为了方便，本文就叫该项目为A项目吧。发现A项目的部署方式和我之前接触的TW“传统”CI/CD策略差异比较大（在[TW](https://thoughtworks.com)，几乎每个项目都有持续集成/持续交付流水线，如果你对它们的概念还不是很清楚，建议阅读[持续交付](https://book.douban.com/subject/6862062/)这本书，将对你梳理整个交付流程帮助巨大）。

&emsp;&emsp;关于A项目的背景，受客户保密协议的限制，我只能透露几点。A项目所属公司为国外某大型电信运营商，主要内容为用户账户自服务平台。该平台涉及诸多内外部服务，如认证、订单跟踪、短信认证等等，数量总数在三十多个左右，而每个服务都是一个独立的子系统，有独立的代码库、独立的机器实例（AWS EC2 实例）用于运行，以及一套独立的jenkins job用于自动化构建和部署（即我们接下来谈的内容）。当然，这也是为什么A项目想往微服务架构迁移的主要目的。

&emsp;&emsp;接下来，让我剥去诸多项目的其他内容，仅仅讨论一下它的CI/CD策略，也可以说是它的构建、部署方式。

## A项目的CI/CD策略
&emsp;&emsp;千言万语还是不及一张图（作者小学美术数学老师教的，望见谅）：
<center><img class="center" src="{{ site.url }}/images/2016/cd-1.jpg" alt="cd.jpg"></center>

&emsp;&emsp;上图，为一个独立子项目（如背景中所说的某个服务）在其[jenkins](https://jenkins.io/index.html)里面的任务（job）结构图，主要有两种自动化任务:build和deploy：
- **build** - 即构建任务。developer在代码仓库（这里是github上某个私有仓库）某个分支上提交了代码后，自动或者手动地被触发。它会根据对应的分支，如develop、一些feature分支或release分支上，而在其对应的任务上构建、运行各层测试以及生成对应的[AMI](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AMIs.html)镜像。
- **deployment** - 即部署任务。该任务需要人工手动点击触发，因为很多时候需要改动一些部署配置，比如说选择刚刚build任务生成的哪个分支的那个AMI文件以及更改一些endpoint的值。它会根据你需要部署的环境，利用自动化部署工具chef，基于对应的AMI镜像生成对应的EC2实例、ELB等等资源，让我们的服务在对应的环境中正式地运行起来（当然也伴随着销毁旧的资源的过程）。这个过程如果目标环境是prod的话，其实就是真实的发布了。

&emsp;&emsp;这用在该项目组中几乎所有的以服务为单位的子系统之上，也就是说，我们有将近三十套左右类似这样的jenkins任务。

&emsp;&emsp;需要说明的是，上图中的黑色圆圈、黑色圆圈加横线和黑色空心圆圈分别代表**完全自动化**、**需要手动更改配置后点击触发** 和 **需要手动点击出发** 三种情况，即如下图所示：
<center><img class="center" src="{{ site.url }}/images/2016/cd-5.jpg" width="35%" alt="cd.jpg"></center>

## A项目的git分支模型

&emsp;&emsp;在我们了解另一种策略模式之前，先让来看看该项目使用的git分支模型 - git flow（如果你还不了解这个概念，请阅读[A successful Git branching model](http://nvie.com/posts/a-successful-git-branching-model/)）：
<center><img class="center" src="{{ site.url }}/images/2016/git-model.png" width="60%" alt="git-model.png"></center>

&emsp;&emsp;简单介绍一下的各种分支：
- **master** - 与产品环境代码保持一致的分支，也就是每次发布完成之后发布的功能分支就要合并于此，以保持master更新。
- **develop** - 开发的主分支，feature和release分支会基于此分支。
- **feature** - 具体要开发的功能的分支，完成后合并到develop。
- **release** - 用于发布新版本的分支，完成后合并到develop和master。
- **hotfix** - 用于紧急修复已发布的产品问题的分支，完成后合并到develop和master。

&emsp;&emsp;这种模型的话，理论上来说相对安全。但是一般feature分支都是需要用于开发一个较大的功能才做的分支，在此之上，我们还要建对应的故事卡（敏捷中，一个不可/不宜划分的需求单位）的分支，如下所示：
<center><img class="center" src="{{ site.url }}/images/2016/cd-2.jpg" width="45%" alt="cd.jpg"></center>

&emsp;&emsp;这么做的好处有：
1. **隔离性比较好，更加安全**
2. **分支职责明确**

&emsp;&emsp;但是缺点也比较明显：
1. **集成的周期太长** 如果以《持续集成》这一本书中观点来看，这甚至算不上持续集成。
2. **会有比较多的重复手工测试**
3. **结构相对复杂**

## “传统”TW项目的策略
&emsp;&emsp;而我曾经接触过的一些项目，不管它的CI/CD工具用的是jenkins还是[go.cd](http://go.cd)，它们都会是一种流水线[pipeline]的形式，如下图所示（没错，请叫我灵魂画师，<手动羞耻脸>）：
<center><img class="center" src="{{ site.url }}/images/2016/cd-3.jpg" alt="cd.jpg"></center>

&emsp;&emsp;对应地，这样的项目，存在分支的话（我这么说，是因为也有不使用分支的真实项目），以我之前的某个离岸海外项目为例，会像如下图所示：
<center><img class="center" src="{{ site.url }}/images/2016/cd-4.jpg" width="45%" alt="cd.jpg"></center>

&emsp;&emsp;明显地，这种结构看起来简单很多。所有分支都是基于develop或者叫master这样的主分支。

&emsp;&emsp;这么做有如下好处：
1. **结构相对简单**
2. **符合小步提交、持续集成思想**

&emsp;&emsp;但是，金无足赤，它有时候也可能会有一些缺点：
1. **feature toggle的引入与测试**
2. **隔离性较差** 但是终究是要合并到一个分支，发布成一个产品的。


## 各自的优缺点和适合应用的场景


&emsp;&emsp;这几种方式虽然各有优缺点，但相比更加传统的缺乏自动化的方式而言，已经进步太多。
