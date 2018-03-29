package com.chinamobile.newzhiwei.model.home;

import android.support.annotation.NonNull;

import com.chinamobile.newzhiwei.contract.home.HomeContract;
import com.zyw.horrarndoo.sdk.base.BaseModel;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/22.
 */

public class HomeModel extends BaseModel implements HomeContract.IHomeModel{
    @NonNull
    public static HomeModel newInstance() {
        return new HomeModel();
    }

    @Override
    public Flowable<ResponseBody> getAdPictures(Map<String,String> maps) {
        return null;
    }

    @Override
    public Flowable<ResponseBody> getNoticeText(Map<String, String> maps) {
        return null;
    }
}
