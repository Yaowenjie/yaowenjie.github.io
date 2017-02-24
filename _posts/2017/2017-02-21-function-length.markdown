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

&emsp;&emsp;在我的职业生涯期间，我曾听过很多关于一个方法(或者说函数，本文针对两者将不做区分)应当有多长的争论。这其实引申到另一个更加重要的问题上：我们应该在什么时候把代码封装在它自己的方法内？有些准则会基于方法的长度，比如方法的长度不应该超出屏幕可以容纳的范围[❶](#ref-1)。有些会基于复用，即任何被使用超过两次的代码都应该被放在其自己的方法内，而只在一个地方使用过的代码就应当保留在行内。然而，于我而言，最有意义的还是这种论点：那就是**意图和实现的分离**。如果你不得不费点精力查看代码的片段，才能弄清楚它具体做了什么，那你就需要把它抽出成一个方法，并且用“它具体做了什么”来为其命名。这样当你再次读到它的时候，这个方法的意图对你来说便一目了然，并且大多数时候你将不再需要关心这个方法是如何实现它的意图的（也就是这个方法的内容）。

<!--more-->

&emsp;&emsp;一旦我接受这项原则，我就养成了编写（短）小方法的习惯（一般来说只有很少几行[❷](#ref-2)）。我开始能够嗅到任何超过六行代码的方法，而且只有单行代码的方法对我来说也不是不寻常的了[❸](#ref-3)。Kent Beck给我展示了一个出自最初Smalltalk系统的例子，使我明白了“方法的大小（长短）并不重要”这个事实。Smalltalk那时候运行在黑白系统上。如果你想强调/高亮某些文字或图像，就需要反向显示它们。Smalltalk的图像类有一个方法叫做`highlight`（高亮），它的实现就只有一个对`reverse`（反向）方法的调用[❹](#ref-4)。这个方法的名字甚至比它的实现还要长，但这并没有什么关系，因为这段代码的意图和它的实现之间有一段比较大的距离。

&emsp;&emsp;一些人对短方法存在顾虑，因为他们担心方法调用时的性能损耗。想我当年年轻时，这一点有时候确实是一个影响性能的因素，但是现在而言这种影响已经很少见了。优化的编译器通常可以通过更加容易的缓存，更好地应对这些短方法。和以往一样，[性能优化的通用准则](https://martinfowler.com/ieeeSoftware/yetOptimization.pdf)很重要。有时你需要
在之后把方法内嵌进去，但是通常更小的方法建议你用其他的方式来加快速度。我记得人们总是拒绝为一个list编写`isEmpty`方法，而是往往使用`aList.length == 0`这样的惯用语法。
但是如果能够更快的找出一个collection是不是空的而不是取决于它的长度，在这里给方法取一个意图相关的名字或许也能够支持更好的性能。

&emsp;&emsp;小方法就像这个例子，只有在它命名合理的情况下才会起到作用，所以你需要好好留意命名。这是需要练习的，但是一旦你擅长于此，这种方法会让代码变得十分的文档化。更大型的方法可以读起来像篇故事，阅读者可以按照自身需求选择深入了解哪些方法的细节。

### 致谢
<div style="font-size:16px;">
&emsp;&emsp;感谢Brandon Byars, Karthik Krishnan, Kevin Yeung, Luciano Ramalho, Pat Kua, Rebecca Parsons, Serge Gebhardt, Srikanth Venugopalan, 以及 Steven Lowe在我们内部邮件组内参与讨论这篇文章的草稿版。
<br/>

&emsp;&emsp;Christian Pekeler提醒了我：嵌套的方法并不适用于我的这种观点。
</div>

### 注释
<div style="font-size:14px;">
  <div id='ref-1'><b>1：</b>或者，在我的第一份编程工作中是：两页打印纸 - 即大约130行 Fortran IV代码。</div>
  <div id='ref-2'><b>2：</b>一些语言会允许你用方法来包含其他方法。这通常用于范围缩小机制，比如使用<a href='http://www.cs.uni.edu/~wallingf/patterns/envoy.pdf'>函数即对象</a>模式来实现一个类。这样的方法自然会大很多。 </div>

  <div id='ref-3'>
    <b>3： 我的方法长度</b> <br/>
    最近我对构建这个网站(martinfowler.com)的工具链内的方法长度很好奇。它主要是用Ruby编写的，并且运行到了将近15千行代码（KLOC）。以下就是它的方法体长度的累积频率图（横坐标：方法内代码行数；纵坐标：累积方法数）:
    <center><img class="center" src="{{ site.url }}/images/2017/my-method-counts.png" width="60%" alt="my-method-counts.png"></center>
    你可以观察到，这里有很多小方法 - 代码里面半数的方法只有两行甚至更少（这里的行数是指没有评论、空格，并且不包括def和end的行数）。
    以面用一个粗略的表格形式来体现数据：
    <center><img class="center" src="{{ site.url }}/images/2017/data-table.png"  width="60%" alt="data-table.png"></center>
  </div>

  <div id='ref-4'>
    <b>4：</b>这个例子就是Kent这本优秀著作<a href='https://www.amazon.com/gp/product/013476904X?ie=UTF8&tag=martinfowlerc-20&linkCode=as2&camp=1789&creative=9325&creativeASIN=013476904X'>《Smalltalk最佳实践模式》</a>中提到的意图相关的内容。
  </div>
</div>
