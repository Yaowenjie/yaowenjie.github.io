---
title: PowerShell CheatSheet 中文版
permalink: /share/ps-cheatsheet-chn
description: "This is cheatsheet for PowerShell"
---
<link rel="stylesheet" type="text/css" href="{{ site.url }}/assets/css/cheatsheet.css" />

<div id='ps-title'>PowerShell CheatSheet 中文版</div>

|昵称|
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

|计算、逻辑、比较运算符|
|---|---|
|+=, −=, ×=, ÷=, %=, ++, −−, =|	Assigns one or more values to a variable|
|-and,-or,-not,-xor,!|	Connect expressions / statements |
|-eq, -ne|	Equal, not equal|
|-gt, -ge|	Greater than, greater than or equal|
|-lt, -le|	Less than, less than or equal|
|-replace|	“Hi” -replace “H”, “P”|
|-match,-notmatch|	Regular expression match|
|-like,-notlike|	Wildcard matching|
|-contains,-notcontains|	Check if value in array|
|-in, -notin|	Reverse of contains,notcontains|


|部分Cmdlets	|
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

|常见有用指令|
|---|---|
|Update-Help|	Downloads and installs newest help files|
|Get-Help|	Displays information about commands and concepts|
|Get-Command|	Gets all commands|
|Get-Member|	Gets the properties and methods of objects|
|Get-Module|	Gets the modules that have been imported or that can be imported into the current session|
|Get-Service|	Gets the services on a local or remote computer|
|Get-Process|	Gets the processes that are running on the local computer or a remote computer|


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


|安全策略相关|
|---|
|View and change execution policy with Get-ExecutionPolicy |
|Set Execution policy: Set-Executionpolicy (with options: Restricted , AllSigned, RemoteSigned, Unrestricted)|


|大小写敏感的比较|
|---|
|-ceq, -cne, -clike, -cnotlike, -cnotmatch, -ccontains, -cnotcontains|

|大小写不敏感的比较|
|---|
|-ieq, -ine, -ilike, -inotlike, -inotmatch, -icontains, -inotcontains|


|流控制	|
|---|
|If(){ } Elseif(){ } Else{ }	|
|while() { }	|
|For($i=0; $i -lt 10; $i++) { }	|
|Foreach($file in dir C:\) {$file.name}	|
|1..10 foreach{$_}	|

|常见自动变量	|
|---|---|
|$$|	Last token of the previous command line|
|$?|	Boolean status of last command|
|$^|	First token of the previous command line|
|$_,| $PSItem	Current pipeline object|
|$profile|	The standard profile (may not be present)|
|$PsVersionTable|	Details about the version of PowerShell|


|注释及忽视符号|
|---|---|
| ` |	Escape char `, and Line continue|
|#, <#…#>|	Comment, Multiline Comment|
|`t|	Tab|
|`n|	New line|
|&|	The call operator, "invocation operator"|


<script type="text/javascript">
  document.querySelectorAll("table th:first-child").forEach(function(th) {
    th.colSpan = 2;
  })

  document.querySelectorAll("table th:last-child").forEach(function(th) {
    if(th.innerHTML === "&nbsp;") th.style.display = 'none';
  })
</script>
