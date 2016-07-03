var currentURL = window.location.href;
if (currentURL.indexOf("yaowenjie.github.io") > -1) {
  var AFS_Account="00830483";
  var AFS_Tracker="auto";
  var AFS_Server="www8";
  var AFS_Page="DetectName";
  var AFS_Url="DetectUrl";
  var AFS_Protocol="http:";
  var speed = document.createElement('script');
  if (document.location.protocol == "https:") AFS_Protocol="https:";
  speed.type = 'text/javascript';
  speed.async = true;
  speed.src =AFS_Protocol+'//'+AFS_Server+'.afsanalytics.com/cgi-bin/afstracka.cgi?usr='+AFS_Account;
  var s = document.getElementsByTagName('script')[0];
  s.parentNode.insertBefore(speed, s);
}
