---
layout: "post"
title: "如何让Git忽略掉文件中的单/多行内容"
date: "2016-03-30 20:59"
description: Git ignore specific lines
comments: true
category: 编程相关
tags: [Git, ignore, gitattributes, filter]
imagefeature: wj/20.jpg
mathjax: true
share: true
---
### ___背景___
____
&emsp;&emsp;大部分使用git的人都知道怎么让git忽略掉某个或者某些文件(使用.gitignore，如果还不知道赶紧去Google补个课吧)，但是绝大多数人却不知如何用git忽略掉文件中某一/几行内容。这极有可能是因为大家没有遇到这样的情况，或者说使用其他手段规避了这种事情。我在自己博客的搭建中就遇到这样一个问题。

<!--more-->
&emsp;&emsp;由于博客是使用Jekyll搭的，所以所有的配置信息都会写在_config.yml，jekyll读取该文件并完成博客配置以及对应静态文件的生成。其中有一行这样的配置:

```
url:       		    http://localhost:4000
```

&emsp;&emsp;很显然，这条配置表明了本地调试时使用的地址，博客的子路径、资源等都是基于localhost:4000这个地址，但是要把放在github上托管的话，这个地址就必须改为我实际对应的博客地址:yaowenjie.github.io，要不这么做的话，到时候github上托管的静态博客就会可能会出现取localhost:4000/images/1.jpg这种情况了，这必然是不可行的（虽然我曾经这么愚蠢地干过，然后自己在本地机器上试居然没问题，实际上换台机器样式啥的就都挂了，苦笑ing）。所以每次push代码之前，我都需要把这段代码改为如下

```
url:       		    http://yaoenjie.github.io
```

&emsp;&emsp;然后在本地调试的时候再把这段代码改为localhost:4000。这样频繁反复地来回修改这段属性，着实让人恼火。由于_config.yml这个文件包含了众多可能会修改的配置信息，所以又不能忽略/过滤掉整个文件。所以这种情况下，我个人觉得最好的解决方案是让git仅仅忽略掉着这一行内容。所以，问题来了，如何让git忽略这一行内容，不让它检测到这行内容的这种固定修改呢？

### ___解决办法___
____
&emsp;&emsp;实际上，git的filter功能可以实现这一点。对于我的这种情况，只需要：

1. 在工程的根目录下创建/打开一个.gitattributes文件(会被提交到本地或者远程仓库)，或者在根目录下创建/.git/info/attributes（不会被提交到本地或者远程仓库）。
2. 在第一步的文件（两者任选其一）中添加如下，用于定义有内容需要被过滤/忽略的文件：
```
 *.yml filter=_config
```
&emsp;&emsp;这表明将要过滤所有名为_config的yml文件。
3. 在你的gitconfig里定义对应的过滤规则，这里我需要在push/add代码时将localhost:4000替换成yaowenjie.github.io，所以利用sed指令可以这么配置：
```
git config --global filter._config.clean 'sed "s/localhost:4000/yaowenjie.github.io/g"'
```
&emsp;&emsp;其中_config与第二步中的filter名字匹配，sed指令实现了这种替换，整句会在git add时被触发。反之亦然，我们需要在pull/checkout代码的时候让本地的这个配置恢复localhost:4000，就需要使用：
```
git config --global filter._config.smudge 'sed "s/yaowenjie.github.io/localhost:4000/g"'
```
&emsp;&emsp;同样的，但是这次用的不是clean，而是smudge，它会在git checkout时被触发。显然，要实现我之前说的让git忽略这一行必须同时加上这两个配置，当然，实际上它是通过git的这种过滤规则来实现的。
<center><img class="center" src="{{ site.url }}/images/2016/smudge.png" alt="smudge.png"></center>
&emsp;&emsp;smudge过滤器会在checkout时执行。
<center><img class="center" src="{{ site.url }}/images/2016/clean.png" alt="clean.png"></center>
&emsp;&emsp;clean过滤器会在staged时（add时）执行。

##### 相关参考：
- [Can git ignore a specific line?](http://stackoverflow.com/questions/6557467/can-git-ignore-a-specific-line)
- [How to tell git to ignore individual lines](https://stackoverflow.com/questions/16244969/how-to-tell-git-to-ignore-individual-lines-i-e-gitignore-for-specific-lines-of)
