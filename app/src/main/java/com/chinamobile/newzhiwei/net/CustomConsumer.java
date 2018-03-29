package com.chinamobile.newzhiwei.net;

import com.chinamobile.newzhiwei.util.EncryptUtils;

import org.json.JSONObject;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/25.
 */

public abstract class CustomConsumer<T> implements Consumer<T> {
    private static final String MSG="MSG";
    private static final String DATA="DATA";
    @Override
    public void accept(T t) throws Exception {
        ResponseBean responseBean = new ResponseBean();
        if(t instanceof ResponseBody){
            ResponseBody responseBody=(ResponseBody)t;
            String str= null;
            try {
                str = new String(responseBody.bytes());
                JSONObject jo = new JSONObject(str);
                if(jo.has(MSG)){
                    responseBean.setMSG(jo.getString(MSG));
                    //检查sessionId( 安全登录 msg)
                    //SecureLoginCheck.checkSessionId(context,responseBean.getMSG());
                }
                if(jo.has(DATA)){
                    //responseBean.setDATA(jo.getString(DATA));
                    //数据解密
                    responseBean.setDATA(EncryptUtils.decodeBase64ForSec(jo.getString(DATA)));
                }
                onCustomNext(responseBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            onCustomNext(responseBean);
        }
    }
    public abstract void onCustomNext(ResponseBean responseBean);
}
