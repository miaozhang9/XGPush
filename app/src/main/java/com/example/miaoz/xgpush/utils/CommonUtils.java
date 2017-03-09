package com.example.miaoz.xgpush.utils;

import android.content.Context;

/**
 * Created by miaoz on 2017/3/9.
 */
public class CommonUtils {
    /**
     * 根据手机的分辨率从dp的单位转换成px(像素)
     * */
    public static int dip2px(Context context, float dpValue) {
        if (context == null) {
            return  0 ;
        }
        final  float scale = context.getResources().getDisplayMetrics().density;
        return  (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px(像素)的单位转成为dp
     * */
    public static  int px2dip(Context context, float pxValue) {
        if (context == null) {
            return 0;
        }
        final  float scale = context.getResources().getDisplayMetrics().density;
        return  (int) (pxValue / scale + 0.5f);
    }

    /***
     * 将px值转换为sp值，保证文字大小不变
     *
     */
    public static  int px2sp(Context context, float pxValue) {
        if (context == null) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    /**
     * 将sp值转换为px值，保证文字大小不变
     * */
    public static  int sp2px(Context context, float spValue) {
        if (context == null) {
            return 0;
        }
        final  float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);

    }
}
