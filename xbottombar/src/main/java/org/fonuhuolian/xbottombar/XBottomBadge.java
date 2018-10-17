package org.fonuhuolian.xbottombar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by 佛怒火莲 on 2017/12/18.
 */

public class XBottomBadge extends RelativeLayout {

    // Badge文字
    private TextView badge_tv;
    // Badge样式
    private ImageView badge_style;

    private Context mContext;


    public XBottomBadge(Context context) {
        this(context, null);
    }

    public XBottomBadge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;

        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.x_bottom_badge, this, true);
        this.badge_tv = findViewById(R.id.xBottom_badge_num);
        this.badge_style = findViewById(R.id.xBottom_style);
        this.setVisibility(GONE);

    }

    // 设置Badge样式(只是设置样式)
    public void setCircleStyle(XBottomCircleStyle style) {

        RelativeLayout.LayoutParams params = (LayoutParams) this.badge_style.getLayoutParams();

        switch (style) {

            // 红底白字
            case REDSOLID:
                this.badge_tv.setTextColor(Color.WHITE);
                this.badge_style.setImageResource(R.drawable.badge_red_solid);
                this.badge_tv.setVisibility(VISIBLE);
                params.height = XBottomUtil.dp2px(mContext, 13);
                params.width = XBottomUtil.dp2px(mContext, 13);
                params.topMargin = 0;
                break;
            // 白底红字+描边红
            case WHITESOLID:
                this.badge_tv.setTextColor(Color.RED);
                this.badge_style.setImageResource(R.drawable.badge_white_stoke);
                this.badge_tv.setVisibility(VISIBLE);
                params.height = XBottomUtil.dp2px(mContext, 13);
                params.width = XBottomUtil.dp2px(mContext, 13);
                params.topMargin = 0;
                break;
            case DOT:
                this.badge_tv.setTextColor(Color.WHITE);
                this.badge_style.setImageResource(R.drawable.badge_red_solid);
                this.badge_tv.setVisibility(GONE);
                params.height = XBottomUtil.dp2px(mContext, 8);
                params.width = XBottomUtil.dp2px(mContext, 8);
                params.topMargin = XBottomUtil.dp2px(mContext, 2);
                break;
        }

        this.badge_style.setLayoutParams(params);
    }

    // 设置提醒数字(int)
    public void setBadge_Num_ByInt(int num) {

        if (num == 0) {
            this.setVisibility(GONE);
        } else if (num > 0 && num <= 99) {
            this.setVisibility(VISIBLE);
            this.badge_tv.setText(String.valueOf(num));
        } else if (num > 99) {
            this.setVisibility(VISIBLE);
            this.badge_tv.setText(R.string.max_badge);
        }
    }

}
