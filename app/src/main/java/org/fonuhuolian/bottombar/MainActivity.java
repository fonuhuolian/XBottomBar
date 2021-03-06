package org.fonuhuolian.bottombar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.fonuhuolian.xbottombar.XBottomBar;
import org.fonuhuolian.xbottombar.XBottomBarListener;
import org.fonuhuolian.xbottombar.XBottomCircleStyle;
import org.fonuhuolian.xbottombar.XBottomItem;

public class MainActivity extends AppCompatActivity implements XBottomBarListener {

    private XBottomBar xBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xBottomBar = findViewById(R.id.xBottomBar);

        // 初始化底部导航栏
        xBottomBar
                .addXBottomItem(new XBottomItem(R.mipmap.application_normal, R.mipmap.application_press, "主页").setFragment(ApplicationFragment.class))
                .addXBottomItem(new XBottomItem(R.mipmap.msg_normal, R.mipmap.msg_normal, "消息"))
                .addXBottomItem(new XBottomItem(R.mipmap.my_normal, R.mipmap.my_press, "我的").setFragment(MeFragment.class))
                .xBottomInitialise();

        // 设置数字提醒样式 以及 数量
        xBottomBar.getXBottomTabView(0).setCircleSytle(XBottomCircleStyle.WHITESOLID).setNoticeNum(1);
        xBottomBar.getXBottomTabView(1).setCircleSytle(XBottomCircleStyle.DOT).setNoticeNum(5);
        xBottomBar.getXBottomTabView(2).setCircleSytle(XBottomCircleStyle.REDSOLID).setNoticeNum(199);


        // 重写底部导航item的点击事件
        xBottomBar.setXBottomOnClickListener(1, this);
    }


    @Override
    public void onClick(View v, int index) {
        if (index == 1)
            Toast.makeText(this, "无fragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 重写底部导航item的点击事件
        xBottomBar.setXBottomOnClickListener(1, this);
    }
}
