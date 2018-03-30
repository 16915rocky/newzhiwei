package com.chinamobile.newzhiwei.presenter.login;

import android.widget.Toast;

import com.chinamobile.newzhiwei.contract.login.LoginContract;
import com.chinamobile.newzhiwei.db.UserDao;
import com.chinamobile.newzhiwei.model.bean.login.UserBean;
import com.chinamobile.newzhiwei.model.login.LoginModel;
import com.chinamobile.newzhiwei.net.CustomConsumer;
import com.chinamobile.newzhiwei.net.ResponseBean;
import com.google.gson.Gson;
import com.chinamobiles.zhiwei.sdk.utils.AppUtils;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/25.
 */

public class LoginPresenter extends LoginContract.LoginPresenter {
    public static LoginPresenter newInstance() {
        return new LoginPresenter();
    }
    @Override
    public void plogin() {
        if(mIModel==null || mIView==null){
            return;
        }
        mRxManager.register(mIModel.mlogin(mIView.getParamters()).subscribe(new CustomConsumer<ResponseBody>() {
            @Override
            public void onCustomNext(ResponseBean responseBean) {
                if(responseBean!=null){
                    if(responseBean.isSuccess()){
                        UserBean bean = new Gson().fromJson(responseBean.getDATA(), UserBean.class);
                        if (bean!=null){
                            if("登录成功".equals(bean.getContent())){
                                //把用户信息插入到sqlite中,再跳转到主页
                                UserDao userDao = new UserDao(AppUtils.getContext());
                                bean.setPassword(mIView.getPassword());
                                userDao.insertUser(bean);
                                mIView.toMainActivity();
                            }else{

                            }
                        }

                    }else{
                        mIView.toMainActivity();
                    }
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(AppUtils.getContext(),throwable.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public LoginContract.LoginModel getModel() {
        return LoginModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
