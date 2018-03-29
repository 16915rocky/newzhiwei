package com.chinamobile.newzhiwei.contract.login;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseModel;
import com.zyw.horrarndoo.sdk.base.IBaseView;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/24.
 */

public interface LoginContract {

    abstract class LoginPresenter extends BasePresenter<LoginModel,LoginView>{
            public abstract void plogin();
    }
    interface LoginModel extends IBaseModel{
        Flowable<ResponseBody> mlogin(Map<String,String> maps);
    }
    interface LoginView extends IBaseView{
        Map<String,String> getParamters();
        String getPassword();
        void toMainActivity();
        void showLoginMessage();
    }
}
