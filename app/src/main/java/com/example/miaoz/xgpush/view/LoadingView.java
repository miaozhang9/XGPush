package com.example.miaoz.xgpush.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miaoz.xgpush.R;
import com.example.miaoz.xgpush.utils.CommonUtils;

/**
 * Created by miaoz on 2017/3/9.
 */
public class LoadingView extends LinearLayout {
    private ImageView progressView;
    private Animation animation;
    private TextView textView;
    private Context mContext;

    public LoadingView(Context context) {
        super(context);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }



    private  void stopLoadingAnimation() {
        progressView.clearAnimation();
        if (animation != null) {
            animation.cancel();
            animation = null;
        }

    }

    private void initView(Context context) {
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        LayoutParams progressLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressLp.setMargins(0,0,0, CommonUtils.dip2px(context,15));
        progressView = new ImageView(context);
        progressView.setLayoutParams(progressLp);
        progressView.setImageResource(R.drawable.ic_loading);
        addView(progressView);

        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color._3c3c3c));
        textView.setTextSize(15);
        textView.setLayoutParams(textLp);
        addView(textView);
        setVisibility(View.GONE);
    }

    /**
     * 显示加载动画
     * @param loadTextStringId 加载文字，如果不定义加载文字，此值为0
     * */
    private void showAnimLoading(int loadTextStringId) {
        setVisibility(View.VISIBLE);
        if (loadTextStringId != 0) {
            String tstring = getResources().getString(loadTextStringId);
            textView.setText(tstring);
        } else {
            textView.setText("努力加载中...");
        }
        progressView.setVisibility(VISIBLE);
        animation = AnimationUtils.loadAnimation(mContext,R.anim.anim_loading);
        progressView.startAnimation(animation);
    }

    /**
     * 显示动画或者静态图片
     * showAnim 显示动画
     * loadtextStringId 显示的文字
     * resId 静态图片
     * */
    public void showLoading(boolean showAnim, int loadTextStringId, int resId) {
        if (showAnim) {
            showAnimLoading(loadTextStringId);
        } else {
            stopLoadingAnimation();
            //静态图片
            setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            if (loadTextStringId > 0) {

                textView.setText(getResources().getString(loadTextStringId));
            } else {
                textView.setText("努力加载中...");
            }
            progressView.setVisibility(GONE);
        }
    }

    public  void hidenLoading() {
        release();
        setVisibility(View.GONE);
    }
    /**
     * 释放资源
     */
    public void release() { stopLoadingAnimation(); }

}
