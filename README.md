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
implementation 'com.github.fonuhuolian:XBottomBar:1.0.4'
```

> xml

```
<org.fonuhuolian.xbottombar.XBottomBar
    android:id="@+id/xBottomBar"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:dividerColor="@color/colorPrimary"
    app:selectedTextColor="@color/colorAccent"
    app:unSelectedTextColor="@color/colorPrimaryDark" />
```

> 代码

```
xBottomBar = findViewById(R.id.xBottomBar);

// 初始化底部导航栏(如果不设置fragment必须重写底部导航item的点击事件)
// 不设置fragment，常见于中间的item(发布功能)
xBottomBar
    .addItem(new XBottomItem(R.mipmap.application_normal, R.mipmap.application_press, "主页").setFragment(ApplicationFragment.class))
    .addItem(new XBottomItem(R.mipmap.msg_normal, R.mipmap.msg_press, "消息"))
    .addItem(new XBottomItem(R.mipmap.my_normal, R.mipmap.my_press, "我的").setFragment(MeFragment.class))
    .initialise();

// 设置数字提醒样式 以及 数量
xBottomBar.getTabView(0).setCircleSytle(XBottomCircleStyle.WHITESOLID).setNoticeNum(1);
xBottomBar.getTabView(1).setCircleSytle(XBottomCircleStyle.DOT).setNoticeNum(5);
xBottomBar.getTabView(2).setCircleSytle(XBottomCircleStyle.REDSOLID).setNoticeNum(199);


// 重写底部导航item的点击事件
xBottomBar.overrideListener(1, new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "消息没有fragment", Toast.LENGTH_SHORT).show();
    }
});
```

> 效果

![效果](https://github.com/fonuhuolian/XBottomBar/blob/master/screenshot/a.png?raw=true)
