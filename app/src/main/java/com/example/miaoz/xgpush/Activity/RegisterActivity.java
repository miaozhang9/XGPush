package com.example.miaoz.xgpush.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class RegisterActivity extends BaseActivity {
    @Bind(R.id.edt_reg_username)
    EditText edtRegUsername;
    @Bind(R.id.edt_reg_password)
    EditText edtRegPassword;
    @Bind(R.id.edt_reg_email)
    EditText edtRegEmail;
    @Bind(R.id.edt_reg_contact)
    EditText edtRegContact;
    @Bind(R.id.btn_reg_submit)
    Button btnRegSubmit;

    Observer<ResultReturn> observer = new Observer<ResultReturn>() {
        @Override
        public void onCompleted() {
            //Toast.makeText(LoginActivity.this,"onCompleted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            dismissLoadingView();
            Toast.makeText(RegisterActivity.this, "onError:" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(ResultReturn resultReturn) {
            dismissLoadingView();
            Toast.makeText(RegisterActivity.this, resultReturn.getMsg(), Toast.LENGTH_SHORT).show();
            if (resultReturn.isSuccess()) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_reg_submit)
    public  void onClick() {
        String name = edtRegUsername.getText().toString().trim();
        String email = edtRegEmail.getText().toString().trim();
        String password = edtRegPassword.getText().toString().trim();
        String contact = edtRegContact.getText().toString().trim();
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {


        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void registerUser(final String name, final  String email, final String password, final String contact) {
        showLoadingView();
        subscription = Network.getRegisterApi()
                .register(name, email, contact, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    @Override
    public void onClick(View v) {

    }

}
