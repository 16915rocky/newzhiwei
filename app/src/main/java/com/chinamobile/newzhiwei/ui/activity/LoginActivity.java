package com.chinamobile.newzhiwei.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ai.appframe2.complex.util.e.K;
import com.chinamobile.newzhiwei.R;
import com.chinamobile.newzhiwei.contract.login.LoginContract;
import com.chinamobile.newzhiwei.presenter.login.LoginPresenter;
import com.chinamobile.newzhiwei.util.EncryptUtils;
import com.chinamobile.newzhiwei.util.PhoneConfigMessageUtils;
import com.chinamobiles.zhiwei.sdk.base.BasePresenter;
import com.chinamobiles.zhiwei.sdk.base.activity.BaseMVPCompatActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/24.
 */

public class LoginActivity extends BaseMVPCompatActivity<LoginContract.LoginPresenter> implements LoginContract.LoginView {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_login_button)
    TextView tvLoginButton;

    @Override
    public Map<String, String> getParamters() {
        Map<String, String> maps = new HashMap<>();
        String un = etUsername.getText().toString();
        String pd = etPassword.getText().toString();
        try {
            String enUserName = K.j(un);
            String enPassword = K.j(pd);
            maps.put("action", "login");
            maps.put("userName", enUserName);
            maps.put("password", enPassword);
            maps.put("ip", PhoneConfigMessageUtils.getDeviceId());
            maps.put("phone_model", Build.BRAND + Build.MODEL + "");//手机型号
            maps.put("phone_system", "Android" + Build.VERSION.RELEASE + "");//系统版本号
            maps.put("deviceId", "3");
            maps.put("useType", "2");
            maps = EncryptUtils.encrypt(maps);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maps;
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }


    @Override
    public void toMainActivity() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoginMessage() {

    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        tvLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.plogin();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return LoginPresenter.newInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
