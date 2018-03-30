package com.chinamobile.newzhiwei.api;


import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/3/12.
 */

public interface BaseApiService {
    public static final String HOST="https://m360.zj.zhiwei.com/ywapp/";

    @FormUrlEncoded
    @POST()
    Flowable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map);

    @GET()
    Flowable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> map);

}
