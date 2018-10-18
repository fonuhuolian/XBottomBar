package org.fonuhuolian.xbottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by 佛怒火莲 on 2018/1/8.
 * BottomTab 底部导航栏控件
 */

public class XBottomBar extends LinearLayout {


    // 自定义FragmentTabHost
    private XBottomFragmentTabHost mTabHost;
    // 底部导航栏上方的分割线
    private View line;
    // 上下文
    private Context mContext;

    // 自定义的属性
    private int lineColor = 0xffff0000;// 线的颜色
    private int selectedTextColor = 0xffff0000;// 选中的文字颜色
    private int unSelectedTextColor = 0xffff0000;// 未选中的文字颜色

    // 标识
    private int mark = -1;


    public XBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 获取自定义属性
        getAttrs(context, attrs);

        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.x_bottom_bar, this, true);
        this.line = findViewById(R.id.xBottom_line);
        this.mTabHost = findViewById(R.id.xBottom_tabHost);


        this.line.setBackgroundColor(lineColor);

        FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
        mTabHost.setup(mContext, fragmentManager, R.id.tabHostContent);
    }


    /**
     * 得到属性值
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XBottomBar);
        unSelectedTextColor = ta.getColor(R.styleable.XBottomBar_unSelectedTextColor, unSelectedTextColor);
        selectedTextColor = ta.getColor(R.styleable.XBottomBar_selectedTextColor, selectedTextColor);
        lineColor = ta.getColor(R.styleable.XBottomBar_diverColor, lineColor);
        ta.recycle();
    }

    /****************填充底部导航栏数据*****************************/
    public XBottomBar addItem(XBottomItem xItem) {

        // 组装底部导航栏的item
        XBottomBarItem item = new XBottomBarItem(mContext, xItem.getImgResUnSelected(), xItem.getImgResSelected(), xItem.getTitle())
                .setTextColor(unSelectedTextColor, selectedTextColor);

        XBottomCircleStyle style = xItem.getxBottomCircleStyle();

        if (style != null)
            item.setCircleSytle(style);


        mTabHost.addTab(mTabHost.newTabSpec(String.valueOf(++mark)).setIndicator(item)
                , xItem.getClss(), xItem.getBundle());

        return this;
    }


    public XBottomBar initialise() {

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);

        return this;
    }


    /****************设置当前选中哪个Tab*****************************/

    public void setCurrentTab(int index) {

        mTabHost.setCurrentTab(index);
    }

    /****************返回当前选中底部button的index*****************************/

    public int getCurrentTab() {
        return mTabHost.getCurrentTab();
    }

    /****************重写Tab点击事件(例：登录拦截)*****************************/

    public void overrideListener(int index, OnClickListener listener) {
        getTabView(index).setOnClickListener(listener);
    }

    /****************返回TabView*****************************/

    public XBottomBarItem getTabView(int index) {

        mTabHost.getTabWidget().setClipChildren(false);

        return (XBottomBarItem) mTabHost.getTabWidget().getChildTabViewAt(index);

    }


    /****************设置指定下标的数字提醒*****************************/

    public void setNoticeNum(int index, int num) {
        getTabView(index).setNoticeNum(num);
    }


    /****************设置指定下标的数字提醒样式*****************************/

    public void setNoticeStyle(int index, XBottomCircleStyle style) {
        getTabView(index).setCircleSytle(style);
    }
}
