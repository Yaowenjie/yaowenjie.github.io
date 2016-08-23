---
layout: "post"
title: "[译]使用基于Babel的gulp"
date: "2016-08-14 11:00"
published: true
comments: true
tags: [gulp, babel, ES6, broswerify]
category: Front-end
imagefeature: wj/10.jpg
---
##### 本文翻译自[macr.ae](http://macr.ae/article/gulp-and-babel.html)
&emsp;&emsp;[Babel](http://babeljs.io/)是一个JavaScript转换编译器，它可以将ES6（下一代JavaScript规范，添加了一些新的特性和语法）转换成ES5(可以在浏览器中运行的代码)。这就意味你可以在一些暂时还不支持某些ES6特性的浏览器引擎中，使用ES6的这些特性 - 比如说，class和箭头方法。本文，我将围绕[gulp](http://www.gulpjs.com.cn/)和babel，介绍如何使用它们。

&emsp;&emsp;"使用基于Babel的gulp"其实可以有两种理解：一是使用Babel编写ES6语法的gulpfile；二是使用gulp来运行babel，让ES6编写的JavaScript代码转化成浏览器可以理解的JavaScript代码。这两种情况接下来我将一一介绍。

<!--more-->

## 用ES6编写gulpfile
&emsp;&emsp;Gulp自3.9版本以来，就添加了针对Babel这样的转换编译器的支持，这样你就可以使用ES6来编写gulpfile了。比如说，如果你正用着Node 0.12，你就可以使用ES6中的箭头方法了。首先，你需要使用gulp构建的项目中的npm来安装**babel**这个安装包。然后，你需要把gulpfile命名为**gulpfile.babel.js**，从而告诉gulp需要找到babel。

&emsp;&emsp;在使用**npm install babel-preset-es2015**安装好babel-preset-es2015这个插件之后，需要在**.babelrc**文件内添加它（在Babel 6.0中，默认不包括任何插件）：

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
&emsp;&emsp;这些很容易理解。你可以像往常一样调用gulp，并且得到你期望的运行结果。

&emsp;&emsp;如果你想指定babel的一些选项，最好的方式就是使用**.babelrc**。但如果这行不通的话（比如，当你想指定某个函数需要使用一个Babel选项的时候），你就不能用我上面说的方法了。所以，此时你需要创建一个名为**gulpfile.js**的文件，并包含如下内容：

```js
require('babel/register')({
  nonStandard: process.env.ALLOW_JSX
});

require('./gulpfile.babel.js');
```
&emsp;&emsp;然后再使用上面提到的gulpfile.babel.js。

## 使用Gulp构建ES6语法的文件
&emsp;&emsp;只是使用babel把ES6转换为ES5是相当简单的。使用如下的[gulp-babel](https://www.npmjs.com/package/gulp-babel)插件：

```js
var gulp = require('gulp');
var babel = require('gulp-babel');

gulp.task('default', function () {
    return gulp.src('src/app.js')
        .pipe(babel())
        .pipe(gulp.dest('dist'));
});
```

&emsp;&emsp;这里的输出将会是经过babel处理的代码，它们可以用于那些暂时还不是完全支持ES6特性的浏览器中。一旦调用了gulp-babel插件，你就可以调用像uglify这样的常用插件。

&emsp;&emsp;gulp-babel插件也支持[gulp-sourcemaps](https://www.npmjs.com/package/gulp-sourcemaps)，它可以用于简单的浏览器调试。

&emsp;&emsp;这种方式下差不多唯一没有使能的特性就是ES6的模块化了。为此，我推荐使用browserify。

### 在基于Broswerify的gulp里调用Babel
(查询所有这些库的名字的大小写花了我一会儿工夫)

&emsp;&emsp;如果你想学习ES6的模块(modules，它让你能够**import**工程中的其他文件)，你可以结合使用[Broswerify](http://browserify.org/)和babel。

&emsp;&emsp;或许你还没有听说过Broswerify，其实它让你可以使用Node.js风格的require来编写代码：

```js
var $ = require('jquery');

$('body').css('background-color', 'orange');
```

&emsp;&emsp;Browserify支持“转换(Transforms)”，它们主要是一些高效的辅助插件 - 就像gulp里面有很多插件可以处理很多文件相关的事情一样，也有很多Browserify的转换，它们可以让你在脚本编译的机器上完成很多支持环境变量的事情，或者编译[React](https://facebook.github.io/react/)的JSX文件。

&emsp;&emsp;其中一个转换插件叫做[babelify](https://github.com/babel/babelify)，它为Browserify添加了babel的支持。除了让你使用ES6和**require()**之外，它还可以把**import**声明转换为**require()**，此时你就可以在你的代码里使用ES6的模块:

```js
import $ from 'jquery';

$('body').css('background-color', 'red');
```

&emsp;&emsp;你可以在下面的代码里一起使用Browserify和babelify：

```js
var fs = require('fs');
var babelify = require('babelify');
var browserify = require('browserify');

var bundler = browserify('src/app.js');
bundler.transform(babelify);

bundler.bundle()
    .on('error', function (err) { console.error(err); })
    .pipe(fs.createWriteStream('bundle.js'));
```

&emsp;&emsp;bundler.bundle()方法返回了一段包含处理过的代码的可读流。我们可以使用vinyl-source-stream和vinyl-buffer，来把这个转化为可以被送入其他gulp插件和**gulp.dest()** 的内容：尽管之前的代码完全可以很好工作了，但最佳实践还是要避免在gulpfile里面使用fs模块。

&emsp;&emsp;在gulpfile里面，之前的代码可以写成这样:

```js
var babelify = require('babelify');
var browserify = require('browserify');
var buffer = require('vinyl-buffer');
var source = require('vinyl-source-stream');
var uglify = require('gulp-uglify');

gulp.task('default', function () {
    var bundler = browserify('src/app.js');
    bundler.transform(babelify);

    bundler.bundle()
        .on('error', function (err) { console.error(err); })
        .pipe(source('app.js'))
        .pipe(buffer())
        .pipe(uglify()) // Use any gulp plugins you want now
        .pipe(gulp.dest('dist'));
});
```
&emsp;&emsp;最后，加分题！给下面的代码添加对source maps的支持。

```js
var babelify = require('babelify');
var browserify = require('browserify');
var buffer = require('vinyl-buffer');
var source = require('vinyl-source-stream');
var sourcemaps = require('gulp-sourcemaps');
var uglify = require('gulp-uglify');

gulp.task('default', function () {
    var bundler = browserify({
        entries: 'src/app.js',
        debug: true
    });
    bundler.transform(babelify);

    bundler.bundle()
        .on('error', function (err) { console.error(err); })
        .pipe(source('app.js'))
        .pipe(buffer())
        .pipe(sourcemaps.init({ loadMaps: true }))
        .pipe(uglify()) // Use any gulp plugins you want now
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest('dist'));
});
```

&emsp;&emsp;棒!（它是不是有点长了，或许有必要把它拆成一个独立的文件？）

---

&emsp;&emsp;你现在应该知道如何在你的gulpfile中使用ES6了 - 通过安装**babel**和重命名gulpfile为**gulpfile.babel.js**； 以及如何使用gulp把ES6的代码转换成ES5 - 使用[gulp-babel](https://www.npmjs.com/package/gulp-babel)或者结合使用[babelify](https://www.npmjs.com/package/gulp-babel)和Browserify。

&emsp;&emsp;如果你在升级到Babel 6之后遇到了什么奇怪的error，先确认你是否使用了[es2015 present](https://babeljs.io/docs/plugins/preset-es2015/)！
