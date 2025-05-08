
# EasyApp
适用于Jetpack Compose的快速开发模板，旨在减少集成以上功能所需要的时间，用更多的时间专注于业务功能的开发。

#### 目前已实现以下功能的快速开发

 - 封装路由集
 - 系统工具类
 - 自定义吐司
 - 网络工具类
 - 视频播放器
 - 默认加载页
 - 默认提示页
 - 默认登录页
 - 默认注册页
 - 主页面框架
 - 全局异常捕获器
 - 腾讯QQ登录集成

#### 如何配置腾讯QQ登录的SDK

在 `AndroidManifest.xml` 里填写以下配置：

```xml
<intent-filter>
    <data android:scheme="0" /> 
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
</intent-filter>
```

在 `MainActivity.kt` 里填写以下配置：
```kt
private fun initTencent() {
    val authorities = "com.android.easyApp.provider"
    Tencent.createInstance("0", this.applicationContext, authorities);
}
```

在 `AppConfig.kt` 里填写以下配置：
```kt
const val tencentAppId: String = "0"
```

以上配置中的 `0` 均替换为你的appid

本模板使用了 [WeUI](hhttps://gitee.com/chengdongqing/weui) 作为主要的UI组件库，暂不提供开发文档，如需使用本模板请翻阅项目的源代码，我们在里面提供了注释以方便阅读。