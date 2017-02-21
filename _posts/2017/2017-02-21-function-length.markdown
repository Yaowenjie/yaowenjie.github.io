---
layout: "post"
title: "[译]方法的长度"
description: "Function Length"
date: "2017-02-21 11:01"
comments: true
category: Coding
tags: [function, length]
share: true
imagefeature: wj/5.jpg
---
###### 本文翻译自老马(Martin Fowler)的[博客文章](https://martinfowler.com/bliki/FunctionLength.html)。

&emsp;&emsp;在我的职业生涯期间，我曾听过很多关于一个方法应当有多长的争论。这其实引申到另一个更加重要的问题上：我们应该在什么时候把代码封装在它自己的方法内？有些相关的参考指南会基于方法的长度，比如方法的长度不应该超出屏幕可以容纳的范围[[1]](#ref-1)。有些会基于复用 - 即任何被使用超过两次的代码都应该被放在其自己的方法内，而只在一个地方使用过的代码就应当在行内保留。然而，对我而言，这种论点最有意义的，那就是**意图和实现的分离**。如果你不得不花点精力查看代码的片段才能弄清楚它具体做了什么，那你就需要把它抽出成一个方法，并且用"它具体做了什么"来为其命名。这样当你再次读到它的时候，这个方法的意图对你来说便一目了然，并且大多数时候你将不再需要关心这个方法是如何实现它的意图的（也就是这个方法的内容）。

<!--more-->

&emsp;&emsp;一旦我接受这项原则，我就养成了编写短方法的习惯 - 一般来说只有很少几行[[2]](#ref-2)。我能嗅到任何超过六行代码的方法，而且只有单行代码的方法对我而言也没有是不寻常的[[3]](#ref-3)。Kent Beck给我展示来自最初Smalltalk系统的一个例子，使我明白了“方法的大小（长短）并不重要”这个事实。Smalltalk那时候运行在黑白系统上。如果你想强调/高亮某些文字或图像，你需要反向显示。Smalltalk的图像类有一个方法叫做`highlight`（高亮），它的实现就只是一个对`reverse`（反向）方法的实现[[4]](#ref-4)。这个方法的名字甚至比它的实现还要长，但这没有什么关系，因为这段代码的意图和它的实现之间有一段比较大的距离。

&emsp;&emsp;一些人会很关注短方法，因为他们担心方法调用时的性能损耗。想我当年年轻时，这一点确实是偶尔的一个因素，但是现在而言这已经很少见了。优化的编译器通常可以通过更加简单地缓存，更好地处理这些短方法。和以往一样，[性能优化的通用准则](https://martinfowler.com/ieeeSoftware/yetOptimization.pdf)很重要。有时你需要
在之后把方法内嵌进去，但是通常更小的方法建议你用其他的方式来加快速度。我记得人们总是拒绝为一个list便携一个`isEmpty`方法，而通常使用`aList.length == 0`这样的的惯用语法。
但是这里在一个方法上使用意图相关的名字或许也支持更好的性能，如果能够更快的找出一个collection是不是空的而不是定义它的长度。

&emsp;&emsp;小方法就像这个例子，只在它的命名正确的情况下有用，因此你需要好好留意命名。这需要练习，但是一旦你擅长于此，这种方法会让代码变得十分的文档化。更大规模的方法可以读起来像篇故事，阅读者可以按照需求选择深入了解哪些方法的细节。

### 致谢
<div style="font-size:16px;">
&emsp;&emsp;Brandon Byars, Karthik Krishnan, Kevin Yeung, Luciano Ramalho, Pat Kua, Rebecca Parsons, Serge Gebhardt, Srikanth Venugopalan, 以及 Steven Lowe在我们内部邮件组内参与讨论了这篇文章的草稿版。
<br/>

&emsp;&emsp;Christian Pekeler提醒了我嵌套的方法并不适用于我提到这种认识。
</div>

### 注释
<h6 id='ref-1'><b>1：</b>或者，在我的第一份编程工作中是：两页打印纸 - 即大约130行 Fortran IV代码</h6>
<h6 id='ref-2'><b>2：</b>一些语言让你用方法来包含其他方法。这通常用于范围减小机制，比如使用<a href='http://www.cs.uni.edu/~wallingf/patterns/envoy.pdf'>函数即对象</a>模式来实现一个类。这样的方法自然而然会大很多。</h6>

<h6 id='ref-3'><b>3： 我的方法长度</b><br/>
最近我对构建这个网站(martinfowler.com)的工具链内的方法长度很好奇。它主要是Ruby代码，并且有15千行代码（KLOC）。这就是它的方法体长度的累积频率图（横坐标：方法内代码行数；纵坐标：累积方法数）:
<center><img class="center" src="{{ site.url }}/images/2017/my-method-counts.png" width="60%" alt="my-method-counts.png"></center>
你可以观察到，有很多小方法 - 代码里面半数的方法只有两行甚至更少（这里的行数是指没有评论、空格，并且不包括def和end的行数）。
以下以一个粗略的表格形式表现数据：
<center><img class="center" src="{{ site.url }}/images/2017/data-table.png"  width="60%" alt="data-table.png"></center>
</h6>

<h6 id='ref-4'><b>4：</b>这个例子就是Kent优秀的<a href='https://www.amazon.com/gp/product/013476904X?ie=UTF8&tag=martinfowlerc-20&linkCode=as2&camp=1789&creative=9325&creativeASIN=013476904X'>Smalltalk最近实践模式</a>中意图相关的内容</h6>
