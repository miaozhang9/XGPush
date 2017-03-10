package com.example.miaoz.xgpush.Activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miaoz.xgpush.R;
import com.example.miaoz.xgpush.view.LoadingView;

import rx.Subscription;

/**
 * Created by miaoz on 2017/3/9.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Subscription subscription;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

    }

    protected void unbindDrawables(View view) {
        if (view != null) {
            if (view.getBackground() != null) {
                view.getBackground().setCallback(null);
            }
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                imageView.setImageDrawable(null);
            }
            if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
                if (!(view instanceof AbsSpinner) && !(view instanceof AbsListView)) {
                    ((ViewGroup) view).removeAllViews();
                }
            }
        }
    }

    protected  View inflateSubView(int subId, int inflateId) {
        View noNetSubTree = findViewById(inflateId);
        if (noNetSubTree == null) {
            ViewStub viewStub = (ViewStub) findViewById(subId);
            noNetSubTree = viewStub.inflate();
        }
        noNetSubTree.setVisibility(View.VISIBLE);
        return  noNetSubTree;
    }

    protected void showLoadingView() {
        View view = inflateSubView(R.id.activity_loading_stub, R.id.activity_loading_subTree);
        if (view != null) {
            LoadingView loadingView = (LoadingView) view.findViewById(R.id.loading_view);
            if (loadingView != null) {
                loadingView.showLoading(true, R.string.loading_busy, 0);
            }
        }
    }

    protected void dismissLoadingView() {
        View view = findViewById(R.id.activity_loading_subTree);
        if (view != null) {
            LoadingView loadingView = (LoadingView) view.findViewById(R.id.loading_view);
            if (loadingView != null) {
                if (loadingView != null) {
                    loadingView.hidenLoading();
                }
            }
        }
    }

    protected void showNetErroView(int id) {
        View view = inflateSubView(R.id.activity_net_error_stub,R.id.activity_net_error_stubTree);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            //注意这里是fragment_net_error_subTree
            View refresh = view.findViewById(R.id.activity_net_error_stubTree);
            TextView textView = (TextView) view.findViewById(R.id.error_saying_bg_textview);
            if (textView != null) {
                textView.setText(id);
            }
            if (refresh != null) {
                refresh.setOnClickListener(this);
            }
        }
    }

    protected void dismissNetErroView() {
        View view = findViewById(R.id.activity_net_error_stubTree);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 内容为空时显示
     * */
    protected  void showContentEmptyView() {
        View view = inflateSubView(R.id.activity_empty_stub,R.id.activity_empty_stubTree);
        view.setVisibility(View.VISIBLE);
    }

    protected void dismissContentEmptyView() {
        View view = findViewById(R.id.activity_empty_stubTree);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
        
    }

    @Override
    public void onClick(View view) {

    }
}
