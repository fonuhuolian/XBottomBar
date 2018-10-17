package org.fonuhuolian.xbottombar;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class XBottomItem {

    // 未选中时的图片
    private int imgResUnSelected;
    // 选中时的图片
    private int imgResSelected;
    // 文字
    private String title;
    // bundle
    private Bundle bundle;
    // fragment
    private Class<? extends Fragment> clss;
    // 数字提醒的样式
    private XBottomCircleStyle xBottomCircleStyle;


    public XBottomItem(int imgResUnSelected, int imgResSelected, String title) {
        this.imgResUnSelected = imgResUnSelected;
        this.imgResSelected = imgResSelected;
        this.title = title;
    }


    public XBottomItem setFragment(Class<? extends Fragment> clss) {
        this.clss = clss;
        return this;
    }

    public XBottomItem setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    public XBottomItem setXBottomCircleStyle(XBottomCircleStyle xBottomCircleStyle) {
        this.xBottomCircleStyle = xBottomCircleStyle;
        return this;
    }

    public int getImgResUnSelected() {
        return imgResUnSelected;
    }

    public int getImgResSelected() {
        return imgResSelected;
    }

    public String getTitle() {
        return title;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public Class<? extends Fragment> getClss() {
        return clss;
    }

    public XBottomCircleStyle getxBottomCircleStyle() {
        return xBottomCircleStyle;
    }
}
