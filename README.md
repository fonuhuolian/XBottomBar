# XBottomBar
底部导航栏

> 添加依赖

`root build.gradle `
```
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```
`module build.gradle `
```
implementation 'com.github.fonuhuolian:XBottomBar:1.1.6'
```

> 混淆
```
-dontwarn org.fonuhuolian.xbottombar.**
-keep class org.fonuhuolian.xbottombar.**{*;}
```

> Notice
```
基本都是以xbottom为自定义属性和方法
```

> xml
```
需要注意的是：当分割线为图片时
app:xBottom_dividerColor="@mipmap/bbbb"
如果没设置dividerColor属性，高度也会失效
高度属性会失效，会用图片本身的高度去适应 所以图片请用.9图进行拉伸适配
app:xBottom_dividerHeight="1px"
```

```
<org.fonuhuolian.xbottombar.XBottomBar
    android:id="@+id/xBottomBar"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:xBottom_dividerColor="@color/colorPrimary"
    app:xBottom_selectedTextColor="@color/colorAccent"
    app:xBottom_unSelectedTextColor="@color/colorPrimaryDark" />
```

> 代码

```
xBottomBar = findViewById(R.id.xBottomBar);

// 初始化底部导航栏(如果不设置fragment必须重写底部导航item的点击事件)
// 不设置fragment，常见于中间的item(发布功能)
xBottomBar
    .addXBottomItem(new XBottomItem(R.mipmap.application_normal, R.mipmap.application_press, "主页").setFragment(ApplicationFragment.class))
    .addXBottomItem(new XBottomItem(R.mipmap.msg_normal, R.mipmap.msg_press, "消息"))
    .addXBottomItem(new XBottomItem(R.mipmap.my_normal, R.mipmap.my_press, "我的").setFragment(MeFragment.class))
    .xBottomInitialise();

// 设置数字提醒样式 以及 数量
xBottomBar.getXBottomTabView(0).setCircleSytle(XBottomCircleStyle.WHITESOLID).setNoticeNum(1);
xBottomBar.getXBottomTabView(1).setCircleSytle(XBottomCircleStyle.DOT).setNoticeNum(5);
xBottomBar.getXBottomTabView(2).setCircleSytle(XBottomCircleStyle.REDSOLID).setNoticeNum(199);


// 重写底部导航item的点击事件
xBottomBar.setXBottomOnClickListener(1, new XBottomBarListener() {
    @Override
    public void onClick(View v, int index) {
        Toast.makeText(MainActivity.this, "消息没有fragment", Toast.LENGTH_SHORT).show();
    }
});

// 设置显示哪个Tab的内容（多用于重写点击事件时搭配使用）
xBottomBar.setXBottomCurrentTab(0);
```

> 效果

![效果](https://github.com/fonuhuolian/XBottomBar/blob/master/screenshot/a.png?raw=true)
