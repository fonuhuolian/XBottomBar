package org.fonuhuolian.xbottombar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class XBottomUtil {

    /**
     * convert dp to its equivalent px
     * 将dp转换为与之相等的px
     */
    protected static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 获取文字颜色状态列表
     */
    protected static ColorStateList getStateListColor(int textColorUnSelected, int textColorSelected) {

        int[] colors = new int[]{textColorUnSelected, textColorSelected, textColorUnSelected};

        int[][] states = new int[3][];

        states[0] = new int[]{-android.R.attr.state_selected};

        states[1] = new int[]{android.R.attr.state_selected};

        states[2] = new int[]{};

        return new ColorStateList(states, colors);
    }


    /**
     * 获取图片状态列表
     */
    protected static StateListDrawable getStateListDrawable(Context context, int imgResUnSelected, int imgResSelected) {

        StateListDrawable stateListDrawable = new StateListDrawable();

        Drawable unSelected = context.getResources().getDrawable(imgResUnSelected);
        Drawable selected = context.getResources().getDrawable(imgResSelected);

        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selected);
        stateListDrawable.addState(new int[]{-android.R.attr.state_selected}, unSelected);

        return stateListDrawable;
    }

}
