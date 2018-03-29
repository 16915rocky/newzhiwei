package com.chinamobile.newzhiwei.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.chinamobile.newzhiwei.BuildConfig;
import com.chinamobile.newzhiwei.api.BaseApiService;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/12.
 */

public class RetrofitClient {
    private static final int DEFAULT_TIMEOUT=20;
    public static final String BASE_URL="https://m360.zj.chinamobile.com/ywapp/";
    //public static final String BASE_URL = "http://10.73.131.139:8898/ywapp/";
    public static String  baseUrl=BASE_URL;
    private static Context mContext;
    private File httpCacheDirectory;
    private Cache cache=null;
    private OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private OkHttpClient.Builder httpBuilder;
    private BaseApiService apiService;
    private HttpLoggingInterceptor httpLoggingInterceptor;
    private RetrofitClient() {
    }
    private RetrofitClient(Context context){
        this(context,baseUrl);
    }
    private RetrofitClient(Context context, String url){
        this(context,url,null);
    }
    private RetrofitClient(Context context, String url, Map<String,String> headers){
        if(TextUtils.isEmpty(url)){
            url=baseUrl;
        }
        if(httpCacheDirectory==null){
            httpCacheDirectory=new File(mContext.getCacheDir(),"rocky_cache");
        }
        try{
            if(cache==null){
                cache=new Cache(httpCacheDirectory,10*1024*1024);
            }
        }catch(Exception e){
            Log.e("OKHttp", "Could not create http cache", e);
        }
         httpBuilder=new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
        //如果不是正式包,添加拦截打印:请求/响应行 + 头 + 体
        if(BuildConfig.DEBUG){
            httpLoggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("RetrofitLog","retrofitBack = "+message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(httpLoggingInterceptor);
        }
        //生成okHttpClient
        okHttpClient=httpBuilder.build();
        //生成retrofit对象
        retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

    private static class SingletonHolder{
        private static  final RetrofitClient INSTANCE=new RetrofitClient(mContext);
    }
    public static RetrofitClient getInstance(Context context){
        if(context!=null){
            mContext=context.getApplicationContext();
        }
        return SingletonHolder.INSTANCE;
    }
    public RetrofitClient createBaseApi(){
        apiService=create(BaseApiService.class);
        return this;
    }

    private <T> T create(final Class<T> service) {
        if(service==null){
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public Flowable post(String url, Map<String,String> parameters){
        return apiService.post(url,parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Flowable<ResponseBody> get(String url, Map<String,String> parameters){
        return apiService.get(url,parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
