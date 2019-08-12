package org.fonuhuolian.xbottombar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by 佛怒火莲 on 2018/1/8.
 * BottomTab 底部导航栏控件
 */

public class XBottomBar extends LinearLayout {


    // 自定义FragmentTabHost
    private XBottomFragmentTabHost mTabHost;
    // 上下文
    private Context mContext;

    // 自定义的属性
    private int lineColor = 0xffff0000;// 线的颜色
    private int selectedTextColor = 0xffff0000;// 选中的文字颜色
    private int unSelectedTextColor = 0xffff0000;// 未选中的文字颜色
    private boolean isUseAnim;// 是否使用动画

    // 标识
    private int mark = -1;


    public XBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 获取自定义属性
        getAttrs(context, attrs);

        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.x_bottom_bar, this, true);
        this.mTabHost = findViewById(R.id.xBottom_tabHost);

        // 底部导航栏上方的分割线
        View line = findViewById(R.id.xBottom_line);
        line.setBackgroundColor(lineColor);

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
        lineColor = ta.getColor(R.styleable.XBottomBar_dividerColor, lineColor);
        isUseAnim = ta.getBoolean(R.styleable.XBottomBar_isUseClickAnim, true);
        ta.recycle();
    }

    /****************填充底部导航栏数据*****************************/
    public XBottomBar addItem(XBottomItem xItem) {

        // 组装底部导航栏的item
        XBottomBarItem item = new XBottomBarItem(mContext, xItem.getImgResUnSelected(), xItem.getImgResSelected(), xItem.getTitle())
                .setTextColor(unSelectedTextColor, selectedTextColor);

        mTabHost.addTab(mTabHost.newTabSpec(String.valueOf(++mark)).setIndicator(item)
                , xItem.getClss(), xItem.getBundle());

        return this;
    }


    @SuppressLint("ClickableViewAccessibility")
    public XBottomBar initialise() {

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);

        if (!isUseAnim)
            return this;

        // 获得tab的数量
        int childCount = mTabHost.getTabWidget().getTabCount();

        for (int i = 0; i < childCount; i++) {

            final XBottomBarItem tabView = getTabView(i);

            //组合
            final AnimatorSet animatorPress = new AnimatorSet();
            ObjectAnimator scalePressX = ObjectAnimator.ofFloat(tabView, "scaleX", 1, 0.9f);
            ObjectAnimator scalePressY = ObjectAnimator.ofFloat(tabView, "scaleY", 1, 0.9f);
            animatorPress.playTogether(scalePressX, scalePressY);
            animatorPress.setDuration(50);

            final AnimatorSet animatorUp = new AnimatorSet();
            ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(tabView, "scaleX", 0.9f, 1);
            ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(tabView, "scaleY", 0.9f, 1);
            animatorUp.playTogether(scaleUpX, scaleUpY);
            animatorUp.setDuration(50);

            // 监听触摸事件
            tabView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    int action = motionEvent.getAction();

                    switch (action) {
                        case MotionEvent.ACTION_DOWN://0
                            animatorPress.cancel();
                            animatorUp.cancel();
                            animatorPress.start();
                            break;
                        case MotionEvent.ACTION_UP://1
                            animatorPress.cancel();
                            animatorUp.cancel();
                            animatorUp.start();
                            break;
                    }

                    return false;
                }
            });
        }

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

    public void overrideOnClickListener(final int index, final XBottomBarListener listener) {
        getTabView(index).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v, index);
            }
        });
    }

    /****************返回TabView*****************************/

    public XBottomBarItem getTabView(int index) {
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
