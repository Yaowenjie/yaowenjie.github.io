---
layout: post
title: 'Windows PowerShell DSC实践（二）| 开始动手！'
date: 2014-12-13 22:31
comments: true
category: PowerShell
tags: [powershell, DSC, resource]
imagefeature: wj/16.JPG
---
<center><h3>Let's Start!</h3></center><hr/>
&emsp;&emsp;在一切工作开始之前，我们必须把Powershell版本升到4.0，具体的升级方法推荐下载安装[Chocolatey](chocolatey.org)后，利用cinst命令安装（安装成功后需重启机器），通过在Powershell窗口输入$PsVersiontable命令查看当前版本,如下方所示:
```powershell
PS C:\Windows\system32> $PSVersionTable

Name                           Value
----                           -----
PSVersion                      4.0
WSManStackVersion              3.0
SerializationVersion           1.1.0.1
CLRVersion                     4.0.30319.17929
BuildVersion                   6.3.9600.16406
PSCompatibleVersions           {1.0, 2.0, 3.0, 4.0}
PSRemotingProtocolVersion      2.2
```
<h4>1.开始一个DSC脚本</h4>
&emsp;&emsp;首先，用管理员身份打开Powershell ISE，在Script Pane编写脚本。当然也可以随便用一个编辑器,在其中编写完后,储存为.ps1格式的Powershell脚本,DSC脚本的简洁写法如下:
```powershell #MyConfig.ps1
Configuration MyConfig
{
  Node ($MachineName0,$MachineName1)
  {
    WindowsFeature MyRoleExample
    {
      Ensure = "Present" # To uninstall the role, set Ensure to "Absent"
      Name = "Web-Server"  
    }

    Script Test
    {
      SetScript = {
        Write－Verbose “This is just a test script!"
      }
      TestScript = {$false}
      GetScript = { <# This must return a hash table #> }
    }

  }
}
MyConfig
```
&emsp;&emsp;DSC脚本主要涉及到对Configuration, Node, WindowsFeature, Script等等关键字的识别。Configuration后接用户定义的DSC脚本名，Node后接一个或多个机器的名字（可以是localhost或者其他机器的IP），而WindowsFeature, Script是DSC内置的Resource（微软官方提供的Resource还有File, Environment 等等），然后Resource里对应的是其属性（属性也是Resource本身定义好的）。其实脚本的三个层次一目了然，一个DSC脚本整体框架内，有一个或几个机器节点，每个节点对应相应的若干Resource，再利用Resource的相应属性对各个节点进行相应的配置。
<h4>2.生成.mof文件,运行DSC</h4>
&emsp;&emsp;同样，在此开始之前我们依然得确认几个前提：
A. Powershell的执行策略是不是UnRestricted(一般默认是Restricted,这样执行上文的MyConfig.ps1时会报错),所以我们需要手动输入下面命令,调整执行策略:
```powershell
Set-ExecutionPolicy UnRestricted
```
B.  Powershell的Remoting是否使能(Disable情况下也会出错),所以同样需要有这么一步:
```powershell
Enable-PSRemoting
```
&emsp;&emsp;然后再Powershell命令行中输入该脚本（Myconfig.ps1）, 并执行。你会看到在同一路径下，生成了一个与该脚本同名的文件夹，文件夹内是同名的.mof文件（MyConfig.mof）。接下来你只要执行类似如下命令,即可完成一个简单DSC配置的过程(其中-Path指向.mof所在路径):
```powershell
Start-DscConfiguration -Wait -Verbose -Force -Path ./MyConfig
```
<h4>3.认识Resource</h4>
&emsp;&emsp;Resource是实现DSC最基本的元素，所以有必要对Resource有一定的了解。执行Get-DscResource指令可以查看系统已经存在的Resource,如下:
```powershell
PS C:\Windows\system32> Get-DscResource

ImplementedAs   Name                      Module                         Properties
-------------   ----                      ------                         ----------
Binary          File                                                     {DestinationPath, Attributes, Checksum, Con.
PowerShell      Archive                   PSDesiredStateConfiguration    {Destination, Path, Checksum, DependsOn...}
PowerShell      Environment               PSDesiredStateConfiguration    {Name, DependsOn, Ensure, Path...}
PowerShell      Group                     PSDesiredStateConfiguration    {GroupName, Credential, DependsOn, Descript.
Binary          Log                       PSDesiredStateConfiguration    {Message, DependsOn}
PowerShell      Package                   PSDesiredStateConfiguration    {Name, Path, ProductId, Arguments...}
PowerShell      Registry                  PSDesiredStateConfiguration    {Key, ValueName, DependsOn, Ensure...}
PowerShell      Script                    PSDesiredStateConfiguration    {GetScript, SetScript, TestScript, Credenti.
PowerShell      Service                   PSDesiredStateConfiguration    {Name, BuiltInAccount, Credential, DependsO.
PowerShell      User                      PSDesiredStateConfiguration    {UserName, DependsOn, Description, Disabled.
PowerShell      WindowsFeature            PSDesiredStateConfiguration    {Name, Credential, DependsOn, Ensure...}
PowerShell      WindowsProcess            PSDesiredStateConfiguration    {Arguments, Path, Credential, DependsOn...}
```
&emsp;&emsp;从上面的结果可以看到微软内置的全部Resource以及它们的属性。当你查看这些Resource产生的源文件（.psd, .psm, .schema.mof文件）时，了解了Resource的运行机制时，你便可以编写自己定义的Resource（下文将主要讨论这一块）,这给DSC提供了更大的灵活性。
