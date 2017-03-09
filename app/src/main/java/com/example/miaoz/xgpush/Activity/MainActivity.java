package com.example.miaoz.xgpush.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.miaoz.xgpush.R;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerPush();
    }

    //注册推送
    private void registerPush() {

        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(getApplicationContext(), true);
// 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
// 具体可参考详细的开发指南
// 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);

// 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);


// 其它常用的API：
// 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account, XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
// 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
// 反注册（不再接收消息）：unregisterPush(context)
// 设置标签：setTag(context, tagName)
// 删除标签：deleteTag(context, tagName)

        //为测试方便设置，发布上线时设置为false
        XGPushConfig.enableDebug(getApplicationContext(), true);
//注册方法
        XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                Log.v("TPush", "注册成功,Token值为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.v("TPush", "注册失败,错误码为：" + errCode + ",错误信息：" + msg);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
        if(clickedResult != null){
            String title = clickedResult.getTitle();
            Log.v("TPush", "title:" + title);
            String id = clickedResult.getMsgId() + "";
            Log.v("TPush", "id:" + id);
            String content = clickedResult.getContent();
            Log.v("TPush", "content:" + content);
        }
    }
}
