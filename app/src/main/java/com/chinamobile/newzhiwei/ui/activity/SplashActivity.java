package com.chinamobile.newzhiwei.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.appframe2.complex.util.e.K;
import com.chinamobile.newzhiwei.R;
import com.chinamobile.newzhiwei.db.UserDao;
import com.chinamobile.newzhiwei.global.MyApplication;
import com.chinamobile.newzhiwei.model.bean.login.UserBean;
import com.chinamobile.newzhiwei.model.login.LoginModel;
import com.chinamobile.newzhiwei.net.CustomConsumer;
import com.chinamobile.newzhiwei.net.ResponseBean;
import com.chinamobile.newzhiwei.util.EncryptUtils;
import com.chinamobile.newzhiwei.util.PhoneConfigMessageUtils;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.chinamobiles.zhiwei.sdk.base.activity.BaseCompatActivity;
import com.chinamobiles.zhiwei.sdk.helper.RxHelper;
import com.chinamobiles.zhiwei.sdk.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/22.
 */

public class SplashActivity extends BaseCompatActivity {

    private static final String TAG = "RxPermission";
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.ll_skip)
    LinearLayout llSkip;
    private boolean mIsCancle;
    private int mTime = 3;
    private LoginModel  loginModel;
    private UserBean mUserBean;

    @Override
    protected void initView(Bundle savedInstanceState) {
        //注：魅族pro6s-7.0-flyme6权限没有像类似6.0以上手机一样正常的提示dialog获取运行时权限，而是直接默认给了权限
        requestPermissions();

        //
    }
    //读取数据库UserBean信息
    protected void getUserFromDB(){

        UserDao userDao = new UserDao(this);
        mUserBean = userDao.getLastUser();

    }
    //写入数据库UserBean信息
    protected void updateSessionId(UserBean userBean){
        UserDao userDao = new UserDao(this);
        if(userBean!=null){
            if(userBean.getSessionId()!=null || userBean.getPhone()!=null){
                userDao.updateSessionId(userBean.getSessionId(), userBean.getPhone());
            }
        }


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
    @Override
    public void onBackPressedSupport() {
        mIsCancle = true;
        //setIsTransAnim(false);
        finish();
    }
    @OnClick(R.id.ll_skip)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_skip:
                mIsCancle = true;
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(SplashActivity.this);
        //请求权限全部结果
        rxPermission.request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (!granted) {
                           // ToastUtils.showToast("App未能获取全部需要的相关权限，部分功能可能不能正常使用.");
                            System.exit(0);
                        }
                        getUserFromDB();
                        //判断数据库面有无用户信息
                        if(mUserBean==null ){

                            toActivity(LoginActivity.class);
                        }else{
                            if(mUserBean.getPassword()==null || mUserBean.getUserName()==null ){
                                toActivity(LoginActivity.class);
                            }
                            loginRequest();
                        }

                    }
                });
    }

    private void initCountDown() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(3)//计时次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return mTime - aLong;// 3-0 3-2 3-1
                    }
                })
                .compose(RxHelper.<Long>rxSchedulerHelper())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Long value) {
                        //                        Logger.e("value = " + value);
                        String s = String.valueOf(value);
                        if (tvCountDown != null)
                            tvCountDown.setText(StringUtils.isEmpty(s) ? "" : s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        if (!mIsCancle) {
                           /* boolean isFirstOpen = SpUtils.getBoolean(SplashActivity.this, AppConstants.FIRST_OPEN, false);
                           if(!isFirstOpen){
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                                return;
                            }*/
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }
    private void loginRequest(){
         loginModel = LoginModel.newInstance();
         loginModel.mlogin(getParameters()).subscribe(new CustomConsumer<ResponseBody>() {
             @Override
             public void onCustomNext(ResponseBean responseBean) {
                 if(responseBean!=null){
                     if(responseBean.isSuccess()){
                         UserBean bean = new Gson().fromJson(responseBean.getDATA(), UserBean.class);
                         if (bean!=null){
                             if("登录成功".equals(bean.getContent())){
                                 //3秒样式显现
                                 llSkip.setVisibility(View.VISIBLE);
                                 initCountDown();
                                 if(bean.getSessionId()!=null){
                                     MyApplication.userBean.setSessionId(bean.getSessionId());
                                 }
                                 updateSessionId(bean);

                             }else{
                                 toActivity(LoginActivity.class);
                             }
                         }

                     }else{
                         toActivity(LoginActivity.class);
                     }
                 }
             }
         }, new Consumer<Throwable>() {
             @Override
             public void accept(Throwable throwable) throws Exception {

             }
         });
    }
    private Map<String, String> getParameters() {
        Map<String, String> maps = new HashMap<>();
        //请求登陆
        try {
            String enUserName = K.j(mUserBean.getPhone()==null?"":mUserBean.getPhone());
            String enPassword = K.j(mUserBean.getPassword()==null?"":mUserBean.getPassword());
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


    public void toActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this,cls);
        startActivity(intent);
        finish();
    }


}
