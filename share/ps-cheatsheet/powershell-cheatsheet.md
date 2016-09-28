---
title: PowerShell CheatSheet
permalink: /share/ps-cheatsheet
description: "This is cheatsheet for PowerShell"
---
<link rel="stylesheet" type="text/css" href="{{ site.url }}/assets/css/cheatsheet.css" />

<div id='ps-title'>PowerShell CheatSheet</div>

|Aliases|
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


|Assignment, Logical, Comparison Operators|
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

|Useful  Commands|
|---|---|
|Update-Help|	Downloads and installs newest help files|
|Get-Help|	Displays information about commands and concepts|
|Get-Command|	Gets all commands|
|Get-Member|	Gets the properties and methods of objects|
|Get-Module|	Gets the modules that have been imported or that can be imported into the current session|
|Get-Service|	Gets the services on a local or remote computer|
|Get-Process|	Gets the processes that are running on the local computer or a remote computer|

|Part of Cmdlets	|
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

|Common Automatic Variables	|
|---|---|
|$$|	Last token of the previous command line|
|$?|	Boolean status of last command|
|$^|	First token of the previous command line|
|$_,| $PSItem	Current pipeline object|
|$profile|	The standard profile (may not be present)|
|$PsVersionTable|	Details about the version of PowerShell|

|Comments, Escape Characters|
|---|---|
|#, <#…#>|	Comment, Multiline Comment|
|"`"test`""|	Escape char `|
|`t|	Tab|
|`n|	New line|
|`|	Line continue|
|&|	The call operator, "invocation operator"|


|Flow Control	|
|---|
|If(){ } Elseif(){ } Else{ }	|
|while() { }	|
|For($i=0; $i -lt 10; $i++) { }	|
|Foreach($file in dir C:\) {$file.name}	|
|1..10 foreach{$_}	|


|Case-Sensitive Comparison|
|---|
|-ceq, -cne, -clike, -cnotlike, -cnotmatch, -ccontains, -cnotcontains|

|Case-Insensitive Comparison|
|---|
|-ieq, -ine, -ilike, -inotlike, -inotmatch, -icontains, -inotcontains|


|Importing, Exporting, Converting	|
|---|---|
|Export-CliXML|	Import-CliXML|
|ConvertTo-XML|	ConvertTo-HTML|
|Export-CSV|	Import-CSV|
|ConvertTo-CSV|	ConvertFrom-CSV|

|Setting Security Policy|
|---|
|View and change execution policy with Get-Execution |
|Set-Execution policy: Get-Executionpolicy (with options: Restricted , AllSigned, RemoteSigned, Unrestricted)|
