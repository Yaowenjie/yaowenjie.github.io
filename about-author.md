---
layout: page
title: 作者的闲言碎语
tags: [Wenjie]
imagefeature: wj/99.jpg
---
<figure>
  <img src="{{ site.url }}/images/wj/head1.jpg" alt="Wenjie Yao">
  <figcaption>Yao Wenjie</figcaption>
</figure>

> Developer ・ 开花猿
>
> Consultant ・ 自熏师
>
> Engineer ・ 攻城狮
>
> ThoughtWorker ・ 骚窝客

---

现在，<br/>
我那什么，<br/>
什么也不想说，

因为，<br/>
我并不知道，<br/>
知道说什么

但是，<br/>
听大家说，<br/>
换行，<br/>
就可以成诗

---

> 非常抱歉，
>
> 你要的某些功能
>
> 暂时可能
>
> 还没有
>
> 因为这个家伙
>
> ### *比较懒* ###
>
###### 但是你仍然可以star/fork[该博客的github repo](https://github.com/Yaowenjie/yaowenjie.github.io)，[列issue](https://github.com/Yaowenjie/yaowenjie.github.io/issues)给我，[发邮件](mailto:wsywj61@gmail.com)吐槽我，或者直接在[博客留言板]({{ site.url }}/message-board)留言
>
###### 我不会告诉你，你可能忽略了博客下面那栏社交账号，以及每篇文章下的分享按钮，哈哈哈哈啊哈哈哈


#### 你也可以通过支付宝/微信的形式**打**赏我，支持我继续创作：
<center><button onClick="switchPayment()" id="switch-payment">目前是支付宝二维码，点击切换微信二维码</button></center>
<br/>
<center><img id="alipay-QR" src="{{ site.url }}/images/alipay-QR.jpg" alt="alipay-QR.jpg"></center>
<center><img id="wechat-QR" src="{{ site.url }}/images/wechat-QR.jpg" alt="wechat-QR.jpg" style="display: none"></center>

<script type="text/javascript">
  function switchPayment() {
    var alipayQR = document.getElementById("alipay-QR");
    var wechatQR = document.getElementById("wechat-QR");
    var switchButton = document.getElementById("switch-payment");
    var textForAlipay = '目前是支付宝二维码，点击切换微信二维码';
    var textForWechat = '目前是微信二维码，点击切换支付宝二维码';

    alipayQR.style.display = (alipayQR.style.display === 'none') ? 'block' : 'none';
    wechatQR.style.display = (wechatQR.style.display === 'none') ? 'block' : 'none';
    switchButton.firstChild.data = (alipayQR.style.display === 'none') ? textForWechat : textForAlipay;
  }
</script>
