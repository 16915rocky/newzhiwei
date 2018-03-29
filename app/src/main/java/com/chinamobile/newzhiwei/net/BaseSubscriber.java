package com.chinamobile.newzhiwei.net;

import android.content.Context;
import android.widget.Toast;

import com.zyw.horrarndoo.sdk.utils.AppUtils;

import org.json.JSONObject;

import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/14.
 */

public abstract class BaseSubscriber<T> extends DisposableSubscriber<T> {

    private Context context;
    private static final String MSG="MSG";
    private static final String DATA="DATA";



    public BaseSubscriber() {
        super();
        this.context = AppUtils.getContext();
    }



    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(context, "http is start", Toast.LENGTH_SHORT).show();

        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onComplete();
        }
    }
    @Override
    public void onNext(T t) {
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
                   responseBean.setDATA(jo.getString(DATA));
                  //数据解密
                  // responseBean.setDATA(EncryptUtils.decodeBase64ForSec(jsonObj.getString(DATA)));
               }
               onCustomNext(responseBean);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }else{
           onCustomNext(responseBean);
       }
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ExceptionHandle.ResponeThrowable){
            onError((ExceptionHandle.ResponeThrowable)e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {
        Toast.makeText(context, "http is Complete", Toast.LENGTH_SHORT).show();
    }
    public abstract void onError(ExceptionHandle.ResponeThrowable e);
    public abstract void onCustomNext(ResponseBean responseBean);
}
