package org.fonuhuolian.xbottombar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by 佛怒火莲 on 2018/1/8.
 * BottomTab 底部导航栏控件
 */

public class XBottomBar extends LinearLayout {


    // 自定义FragmentTabHost
    private XBottomFragmentTabHost mTabHost;
    // 中间的icon
    private ImageView mMiddleIcon;
    private RelativeLayout mMiddleLayout;
    // 分割线
    private View mLine;
    // 中间图片
    private TextView mTempText;
    // 上下文
    private Context mContext;

    // 自定义的属性
    private Drawable lineColor;// 线的颜色
    private Drawable bgDrawable;// 背景
    private int lineHeight;// 分割线高度
    private int selectedTextColor = 0xffff0000;// 选中的文字颜色
    private int unSelectedTextColor = 0xffff0000;// 未选中的文字颜色
    private boolean isUseAnim;// 是否使用动画
    private int iconMarginTop;// 图片距离顶部高度
    private int iconMarginText;// 图片文字距离
    private int textMarginBottom;// 文字距离底部高度
    private int middleIconMarginText;// 中间图片文字距离
    private int contentMargin;// 底部导航栏与内容之间的间距

    // 标识
    private int mark = -1;

    // 是否使用了中间icon
    private boolean isUseMiddle = false;

    public XBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 获取自定义属性
        getAttrs(context, attrs);

        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.x_bottom_bar, this, true);
        mTabHost = findViewById(R.id.xBottom_tabHost);
        this.mMiddleIcon = findViewById(R.id.xBottom_middle_icon);
        this.mMiddleLayout = findViewById(R.id.middleLayout);
        this.mTempText = findViewById(R.id.tempText);

        RelativeLayout.LayoutParams XBottomTitleLayoutParams = (RelativeLayout.LayoutParams) mTempText.getLayoutParams();
        XBottomTitleLayoutParams.topMargin = middleIconMarginText;
        XBottomTitleLayoutParams.bottomMargin = textMarginBottom;
        mTempText.setLayoutParams(XBottomTitleLayoutParams);

        // 底部导航栏上方的分割线
        mLine = findViewById(R.id.xBottom_line);
        mLine.setBackground(lineColor);
        LayoutParams layoutParams = (LayoutParams) mLine.getLayoutParams();
        layoutParams.topMargin = contentMargin;
        mLine.setLayoutParams(layoutParams);

        // 设置分割线高度
        addXBottomDividerHeight(lineHeight);

        if (lineColor instanceof ColorDrawable) {
            // 高度用用户设置的
        } else if (lineColor instanceof BitmapDrawable) {
            // 高度用用户设置的
        } else if (lineColor instanceof NinePatchDrawable) {
            // 高度用.9图的高度 宽度自动去拉伸
            addXBottomDividerHeight(lineColor.getIntrinsicHeight());
        }

        // 导航栏背景色
        mTabHost.setBackground(bgDrawable);

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
        unSelectedTextColor = ta.getColor(R.styleable.XBottomBar_xBottom_unSelectedTextColor, unSelectedTextColor);
        selectedTextColor = ta.getColor(R.styleable.XBottomBar_xBottom_selectedTextColor, selectedTextColor);
        lineColor = ta.getDrawable(R.styleable.XBottomBar_xBottom_dividerColor);
        isUseAnim = ta.getBoolean(R.styleable.XBottomBar_xBottom_isUseClickAnim, true);
        bgDrawable = ta.getDrawable(R.styleable.XBottomBar_xBottom_background);
        lineHeight = (int) ta.getDimension(R.styleable.XBottomBar_xBottom_dividerHeight, 1);
        iconMarginTop = (int) ta.getDimension(R.styleable.XBottomBar_xBottom_icon_margin_top, dip2px(6.5f));
        iconMarginText = (int) ta.getDimension(R.styleable.XBottomBar_xBottom_text_icon_margin, dip2px(2));
        textMarginBottom = (int) ta.getDimension(R.styleable.XBottomBar_xBottom_text_margin_bottom, dip2px(3));
        middleIconMarginText = (int) ta.getDimension(R.styleable.XBottomBar_xBottom_middle_icon_margin_text, iconMarginText);
        contentMargin = (int) ta.getDimension(R.styleable.XBottomBar_XBottom_bar_content_margin, 0);
        ta.recycle();
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /****************填充底部导航栏数据*****************************/
    public XBottomBar addXBottomItem(XBottomItem xItem) {

        // 组装底部导航栏的item
        XBottomBarItem item = new XBottomBarItem(mContext, xItem.getImgResUnSelected(), xItem.getImgResSelected(), xItem.getTitle(), iconMarginTop, iconMarginText, textMarginBottom)
                .setTextColor(unSelectedTextColor, selectedTextColor);

        mTabHost.addTab(mTabHost.newTabSpec(String.valueOf(++mark)).setIndicator(item)
                , xItem.getClss(), xItem.getBundle());

        return this;
    }

    public XBottomBar addXBottomMiddleItemClipChildren(XBottomItem xItem) {

        // 组装底部导航栏的item
        XBottomBarItem item = new XBottomBarItem(mContext, android.R.color.transparent, android.R.color.transparent, xItem.getTitle(), iconMarginTop, iconMarginText, textMarginBottom)
                .setTextColor(unSelectedTextColor, selectedTextColor);

        mTabHost.addTab(mTabHost.newTabSpec(String.valueOf(++mark)).setIndicator(item)
                , xItem.getClss(), xItem.getBundle());

        mMiddleIcon.setImageResource(xItem.getImgResSelected());
        isUseMiddle = true;

        return this;
    }


    public XBottomBar addXBottomDividerHeight(int lineHeight) {
        ViewGroup.LayoutParams layoutParams = mLine.getLayoutParams();
        layoutParams.height = lineHeight;
        mLine.setLayoutParams(layoutParams);
        return this;
    }

    public XBottomBar addXBottomDividerColor(@ColorRes int colorRes) {
        mLine.setBackgroundColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    public XBottomBar addXBottomDividerColor(String color) {
        mLine.setBackgroundColor(Color.parseColor(color));
        return this;
    }

    public XBottomBar addXBottomDividerDrawable(@DrawableRes int drawableRes) {
        mLine.setBackground(mContext.getResources().getDrawable(drawableRes));
        return this;
    }


    public XBottomBar addXBottomBackgroundColor(@ColorRes int colorRes) {
        mTabHost.setBackgroundColor(mContext.getResources().getColor(colorRes));
        return this;
    }

    public XBottomBar addXBottomBackgroundColor(String color) {
        mTabHost.setBackgroundColor(Color.parseColor(color));
        return this;
    }

    public XBottomBar addXBottomBackgroundDrawable(@DrawableRes int drawableRes) {
        mTabHost.setBackground(mContext.getResources().getDrawable(drawableRes));
        return this;
    }


    @SuppressLint("ClickableViewAccessibility")
    public XBottomBar xBottomInitialise() {

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);

        if (!isUseAnim)
            return this;


        // 获得tab的数量
        int childCount = mTabHost.getTabWidget().getTabCount();

        for (int i = 0; i < childCount; i++) {

            final XBottomBarItem tabView = getXBottomTabView(i);

            //组合
            final AnimatorSet animatorPress = new AnimatorSet();
            if (isUseMiddle && i == childCount / 2) {
                ObjectAnimator scalePressX = ObjectAnimator.ofFloat(tabView, "scaleX", 1, 0.9f);
                ObjectAnimator scalePressY = ObjectAnimator.ofFloat(tabView, "scaleY", 1, 0.9f);
                ObjectAnimator scalePressX1 = ObjectAnimator.ofFloat(mMiddleIcon, "scaleX", 1, 0.9f);
                ObjectAnimator scalePressY1 = ObjectAnimator.ofFloat(mMiddleIcon, "scaleY", 1, 0.9f);
                animatorPress.playTogether(scalePressX, scalePressY, scalePressX1, scalePressY1);
            } else {
                ObjectAnimator scalePressX = ObjectAnimator.ofFloat(tabView, "scaleX", 1, 0.9f);
                ObjectAnimator scalePressY = ObjectAnimator.ofFloat(tabView, "scaleY", 1, 0.9f);
                animatorPress.playTogether(scalePressX, scalePressY);
            }
            animatorPress.setDuration(50);

            final AnimatorSet animatorUp = new AnimatorSet();
            if (isUseMiddle && i == childCount / 2) {
                ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(tabView, "scaleX", 0.9f, 1);
                ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(tabView, "scaleY", 0.9f, 1);
                ObjectAnimator scaleUpX1 = ObjectAnimator.ofFloat(mMiddleIcon, "scaleX", 0.9f, 1);
                ObjectAnimator scaleUpY1 = ObjectAnimator.ofFloat(mMiddleIcon, "scaleY", 0.9f, 1);
                animatorUp.playTogether(scaleUpX, scaleUpY, scaleUpX1, scaleUpY1);
            } else {
                ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(tabView, "scaleX", 0.9f, 1);
                ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(tabView, "scaleY", 0.9f, 1);
                animatorUp.playTogether(scaleUpX, scaleUpY);
            }
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

        if (isUseMiddle) {

            if (childCount % 2 != 1) {
                return this;
            }

            final XBottomBarItem tabView = getXBottomTabView(childCount / 2);

            this.post(new Runnable() {
                @Override
                public void run() {

                    int height = mTabHost.getHeight();
                    int height1 = mMiddleLayout.getHeight();
                    final int iconOverHeight = height1 - height;

                    //组合
                    final AnimatorSet animatorPress = new AnimatorSet();
                    if (isUseMiddle) {
                        ObjectAnimator scalePressX = ObjectAnimator.ofFloat(tabView, "scaleX", 1, 0.9f);
                        ObjectAnimator scalePressY = ObjectAnimator.ofFloat(tabView, "scaleY", 1, 0.9f);
                        ObjectAnimator scalePressX1 = ObjectAnimator.ofFloat(mMiddleIcon, "scaleX", 1, 0.9f);
                        ObjectAnimator scalePressY1 = ObjectAnimator.ofFloat(mMiddleIcon, "scaleY", 1, 0.9f);
                        animatorPress.playTogether(scalePressX, scalePressY, scalePressX1, scalePressY1);
                    } else {
                        ObjectAnimator scalePressX = ObjectAnimator.ofFloat(tabView, "scaleX", 1, 0.9f);
                        ObjectAnimator scalePressY = ObjectAnimator.ofFloat(tabView, "scaleY", 1, 0.9f);
                        animatorPress.playTogether(scalePressX, scalePressY);
                    }
                    animatorPress.setDuration(50);

                    final AnimatorSet animatorUp = new AnimatorSet();
                    if (isUseMiddle) {
                        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(tabView, "scaleX", 0.9f, 1);
                        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(tabView, "scaleY", 0.9f, 1);
                        ObjectAnimator scaleUpX1 = ObjectAnimator.ofFloat(mMiddleIcon, "scaleX", 0.9f, 1);
                        ObjectAnimator scaleUpY1 = ObjectAnimator.ofFloat(mMiddleIcon, "scaleY", 0.9f, 1);
                        animatorUp.playTogether(scaleUpX, scaleUpY, scaleUpX1, scaleUpY1);
                    } else {
                        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(tabView, "scaleX", 0.9f, 1);
                        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(tabView, "scaleY", 0.9f, 1);
                        animatorUp.playTogether(scaleUpX, scaleUpY);
                    }
                    animatorUp.setDuration(50);

                    // 监听触摸事件
                    mMiddleIcon.setOnTouchListener(new OnTouchListener() {
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
            });
        }

        return this;
    }


    /****************设置当前选中哪个Tab*****************************/

    public void setXBottomCurrentTab(int index) {
        mTabHost.setCurrentTab(index);
    }

    /****************返回当前选中底部button的index*****************************/

    public int getXBottomCurrentTab() {
        return mTabHost.getCurrentTab();
    }

    /****************重写Tab点击事件(例：登录拦截)*****************************/

    public void setXBottomOnClickListener(final int index, final XBottomBarListener listener) {
        getXBottomTabView(index).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v, index);
            }
        });

        int tabCount = mTabHost.getTabWidget().getTabCount();
        if (tabCount % 2 == 1 && isUseMiddle && index == tabCount / 2) {
            mMiddleIcon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onClick(getXBottomTabView(index), index);
                }
            });
        }
    }

    /****************返回TabView*****************************/

    public XBottomBarItem getXBottomTabView(int index) {
        return (XBottomBarItem) mTabHost.getTabWidget().getChildTabViewAt(index);
    }
}
