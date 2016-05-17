---
layout: page
title: 留言板
permalink: message-board/index.html
description: 有問題，欢迎給我留言
tags: [Message]
imagefeature: wj/27.jpg
comments: true
---
<link rel="stylesheet" type="text/css" href="{{ site.url }}/assets/css/walk.css" />
<div id="walk-container">
  <div id="walk"></div>
</div>


> 那什么，没事，没事说两句：

<div class="ds-thread" data-thread-key="{{ page.title }}" data-title="{{ page.title }}" data-url="{{ page.url }}"></div>

<script type="text/javascript">
var duoshuoQuery = {short_name:"{{ site.duoshuo_shortname }}"};
  (function() {
    var ds = document.createElement('script');
    ds.type = 'text/javascript';ds.async = true;
    ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
    ds.charset = 'UTF-8';
    (document.getElementsByTagName('head')[0]
     || document.getElementsByTagName('body')[0]).appendChild(ds);
  })();
</script>
