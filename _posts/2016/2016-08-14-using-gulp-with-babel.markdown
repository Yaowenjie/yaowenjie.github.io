---
layout: "post"
title: "基于Babel使用gulp"
date: "2016-08-14 11:00"
published: false
comments: true
tags: [gulp, babel]
category: Front-end
imagefeature: wj/10.jpg
---
##### 本文翻译自[macr.ae](http://macr.ae/article/gulp-and-babel.html)
&emsp;&emsp;[Babel](http://babeljs.io/)是一个JavaScript转换编译器，它可以将ES6（下一代JavaScript规范，添加了一些新的特性和语法）转换成ES5(可以在浏览器中运行的代码)。这就意味你可以在一些暂时还不支持某些ES6特性的浏览器引擎中，使用ES6的这些特性 - 比如说，class和箭头方法。本文，我将围绕[gulp](http://www.gulpjs.com.cn/)和babel，介绍如何使用它们。

&emsp;&emsp;"基于Babel使用gulp"其实可以有两种理解：可以是使用Babel编写ES6语法的gulpfile；也可以是使用gulp来运行babel，让ES6编写的JavaScript代码转化成浏览器可以理解的JavaScript代码。这两种情况接下来我将一一介绍。

<!--more-->

## 用ES6编写gulpfile
&emsp;&emsp;Gulp自3.9版本以来，就添加了针对Babel这样的转换编译器的支持，这样你就可以使用ES6来编写gulpfile了。比如说，如果你正用着Node 0.12，你就可以使用ES6中的箭头方法了。首先，你需要使用gulp构建的项目中的npm来安装**babel**这个安装包。然后，你需要把gulpfile命名为**gulpfile.babel.js**，从而告诉gulp需要找到babel。

&emsp;&emsp;在使用**npm install babel-preset-es2015**安装好babel-preset-es2015这个插件之后，需要在**.babelrc**文件内搭建好它（在Babel 6.0中，默认不包括任何插件）：

```js
{
  "presets": ["es2015"]
}
```
(参考[the Babel website](https://babeljs.io/docs/plugins/preset-es2015/))

&emsp;&emsp;此后，你就可以在gulpfile里面使用ES6的语法。举个栗子：

```js
import gulp from 'gulp';

gulp.task('default', () => console.log('Default task called'));
```
&emsp;&emsp;这些很容易理解。你可以像往常一样调用gulp，并且符合你期望的运行结果。

&emsp;&emsp;如果你想指定babel的一些选项，最好的方式就是使用**.babelrc**。但如果这行不通的话（比如，当你想指定某个函数需要使用一个Babel选项的时候），你就不能用我上面说的方法了。所以，此时你需要创建一个名为**gulpfile.js**的文件，并包含如下内容：

```js
require('babel/register')({
  nonStandard: process.env.ALLOW_JSX
});

require('./gulpfile.babel.js');
```
&emsp;&emsp;然后再使用上面提到的gulpfile.babel.js。

## 使用Gulp构建ES6语法的文件



## 使用gulp通过Broswerify
