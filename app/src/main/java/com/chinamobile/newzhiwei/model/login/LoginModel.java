package com.chinamobile.newzhiwei.model.login;

import com.chinamobile.newzhiwei.contract.login.LoginContract;
import com.chinamobile.newzhiwei.net.RetrofitClient;
import com.chinamobiles.zhiwei.sdk.base.BaseModel;
import com.chinamobiles.zhiwei.sdk.utils.AppUtils;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/24.
 */

public class LoginModel extends BaseModel implements LoginContract.LoginModel {
    public static LoginModel newInstance() {
        return new LoginModel();
    }
    @Override
    public Flowable<ResponseBody> mlogin(Map<String,String> maps) {
        return RetrofitClient
                .getInstance(AppUtils.getContext())
                .createBaseApi()
                .get("User?",maps);
    }
}
