package com.example.miaoz.xgpush.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miaoz.xgpush.R;
import com.example.miaoz.xgpush.model.ResultReturn;
import com.example.miaoz.xgpush.network.Network;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by miaoz on 2017/3/10.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.edt_email)
    EditText edtemail;
    @Bind(R.id.edt_password)
    EditText edtPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.tv_response)
    TextView tvResponse;
    @Bind(R.id.btn_pie)
    Button btnPie;

    Observer<ResultReturn> observer = new Observer<ResultReturn>() {
        @Override
        public void onCompleted() {
//            Toast.makeText(LoginActivity.this,"onCompleted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            dismissLoadingView();
            Toast.makeText(LoginActivity.this, "onError:"+e.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(ResultReturn resultReturn) {
            dismissLoadingView();
            Toast.makeText(LoginActivity.this, resultReturn.getMsg(), Toast.LENGTH_SHORT).show();
            if (resultReturn.isSuccess()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }
    };

    private void login(String email, String password) {
        subscription = Network.getLoginApi()
                .login(email,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.btn_pie})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                showLoadingView();
                excuteLogin();
                break;
            case R.id.btn_register:
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            case R.id.btn_pie:
                startActivity(new Intent(LoginActivity.this,PieActivity.class));
        }

    }

    private void excuteLogin() {
        String email = edtemail.getText().toString().trim();
        String pwd = edtPassword.getText().toString().trim();
        login(email, pwd);
    }
}
