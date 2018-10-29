package org.fonuhuolian.xbottombar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by 佛怒火莲 on 2018/1/6.
 * 底部导航栏 ITEM
 */

@SuppressLint("ViewConstructor")
public class XBottomBarItem extends RelativeLayout {

    private ImageView XBottomIcon;
    private TextView XBottomTitle;
    private XBottomBadge XBottomBadge;


    /**
     * @param context          上下文对象
     * @param imgResUnSelected 未选中状态时的图片
     * @param imgResSelected   选中状态时的图片
     * @param title            文字描述
     */
    public XBottomBarItem(Context context, int imgResUnSelected, int imgResSelected, String title) {
        super(context);

        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.x_bottom_bar_item, this, true);
        XBottomIcon = findViewById(R.id.xBottom_icon);
        XBottomTitle = findViewById(R.id.xBottom_title);
        XBottomBadge = findViewById(R.id.xBottom_badge);

        XBottomIcon.setImageDrawable(XBottomUtil.getStateListDrawable(context, imgResUnSelected, imgResSelected));
        XBottomTitle.setText(title);

        // 默认数字提醒,默认文字颜色
        XBottomBadge.setCircleStyle(XBottomCircleStyle.REDSOLID);
    }


    protected XBottomBarItem setTextColor(int textColorUnSelected, int textColorSelected) {
        XBottomTitle.setTextColor(XBottomUtil.getStateListColor(textColorUnSelected, textColorSelected));
        return this;
    }


    public XBottomBarItem setNoticeNum(int num) {
        XBottomBadge.setBadge_Num_ByInt(num);
        return this;
    }


    // 设置Badge样式
    public XBottomBarItem setCircleSytle(XBottomCircleStyle xBottomCircleStyle) {
        XBottomBadge.setCircleStyle(xBottomCircleStyle);
        return this;
    }

}
