---
title: PowerShell CheatSheet 中文版
permalink: /share/ps-cheatsheet-chn
description: "This is cheatsheet for PowerShell"
---
<link rel="stylesheet" type="text/css" href="{{ site.url }}/assets/css/cheatsheet.css" />

<!-- <div id='ps-title'>PowerShell CheatSheet 中文版</div> -->


|计算、逻辑、比较运算符|
|---|---|
|+=, −=, ×=, ÷=, %=, ++, −−, =|	将一个或者多个值赋给一个变量|
|-and,-or,-not,-xor,!|	连接表达式/声明 |
|-eq, -ne|	相等, 不等|
|-gt, -ge|	大于, 大于或等于|
|-lt, -le|	小于, 小于或等于|
|-replace|	替换字符|
|-match,-notmatch|	正则表达式匹配|
|-like,-notlike|	通配符匹配|
|-contains,-notcontains|	检查数组内是否包含该值|
|-in, -notin|	contains,notcontains的反向调用|


|部分常见Cmdlets	|
|---|---|---|
|Get-EventLog|Get-WinEvent|	Get-Date|
|New-Item|Set-Item|	Move-Item|
|Copy-Item|	Remove-Item|	Compare-Object|
|Get-Content|	Set-Content|	Add-Content|
|Start-Sleep|	Start-Job|	Get-CimInstance|
|Get-Credential|	Test-Connection|	New-PSSession|
|Test-Path|	Split-Path|	Get-History|
|Get-ADUser|	Get-ADComputer|	New-ISESnippet|
|Get-WMIObject|	Out-File|	Out-String|


|输入、输出、转换	|
|---|
|Export-CliXML|
|Import-CliXML|
|ConvertTo-XML|
|ConvertTo-HTML|
|Export-CSV|
|Import-CSV|
|ConvertTo-CSV|
|ConvertFrom-CSV|

|命令昵称1|
|---|---|
|dir, ls, gci|	Get-ChildItem|
|h,history,ghy| 	Get-History|
|cd,chdir,sl|	Set-Location|
|copy,cp,cpi|	Copy-Item|
|move,mv,mi|	Move-Item|
|del,rm|	Remove-Item|
|rni,ren|	Rename-Item|
|cls,clear|	Clear-Host|
|cat,gc,type|	Get-Content|
|sc|	Set-Content|
|pwd,gl|	Get-Location|
|foreach,%| 	Foreach-Object|
|where,?|	Where-Object|

|命令昵称2|
|---|---|
|gcm|	Get-Command|
|gcim|	Get-CimInstance|
|sort|	Sort-Object|
|diff,compare|	Compare-Object|
|r,ihy|	Invoke-History|
|gi|	Get-Item|
|gp|	Get-ItemProperty|
|sp|	Set-ItemProperty|
|gm|	Get-Member|
|sls|	Select-String|
|fl|	Format-List|
|ft|	Format-Table|

|常见有用指令|
|---|---|
|Update-Help|	下载安装最新的帮助文件|
|Get-Help| 显示命令和概念的基本信息|
|Get-Command|	获取所有命令|
|Get-Member|	获取对象的属性和方法|
|Get-Module|	获取已经导入或者可以被导入到当前session的模块|
|Get-Service|	获取本地或者远程机器上的服务|
|Get-Process|	获取本地或者远程机器上的进程|

|常见自动化变量	|
|---|---|
|$$|	会话最后一行中的最后一个令牌|
|$?|	最后一个操作的执行状态（TRUE/FALSE）|
|$^|	会话最后一行中的第一个令牌|
|$_,| $PSItem	当前管道内的对象|
|$profile|	标准profile (可能不是当前profile)|
|$PsVersionTable|	PowerShell版本相关信息|


|流控制	|
|---|
|If(){ } Elseif(){ } Else{ }	|
|while() { }	|
|For($i=0; $i -lt 10; $i++) { }	|
|Foreach($file in dir C:\) {$file.name}	|
|1..10 foreach{$_}	|


|注释及忽视符号|
|---|---|
| ` |	转义符, 单独使用时作为续行符|
|#, <#…#>|	注释, 多行注释|
|`t|	Tab|
|`n|	另起一行|
|&|	调用操作符|

|安全策略相关|
|---|
|查看当前执行策略: Get-ExecutionPolicy |
|设置执行策略: Set-Executionpolicy (可选参数: Restricted , AllSigned, RemoteSigned, Unrestricted)|


|大小写敏感的比较|
|---|
|-ceq, -cne, -clike, -cnotlike, -cnotmatch, -ccontains, -cnotcontains|

|大小写不敏感的比较|
|---|
|-ieq, -ine, -ilike, -inotlike, -inotmatch, -icontains, -inotcontains|



<script type="text/javascript">
  document.querySelectorAll("table th:first-child").forEach(function(th) {
    th.colSpan = 2;
  })

  document.querySelectorAll("table th:last-child").forEach(function(th) {
    if(th.innerHTML === "&nbsp;") th.style.display = 'none';
  })
</script>
