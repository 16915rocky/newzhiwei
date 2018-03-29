package com.chinamobile.newzhiwei.model.find;

import com.chinamobile.newzhiwei.contract.find.FindContract;
import com.chinamobile.newzhiwei.net.RetrofitClient;
import com.zyw.horrarndoo.sdk.utils.AppUtils;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/27.
 */

public class FindModel implements FindContract.FindModel {

    public static FindModel newInstance() {
        return new FindModel();
    }
    @Override
    public Flowable<ResponseBody> getListContent(Map<String, String> maps) {
        return  RetrofitClient
                 .getInstance(AppUtils.getContext())
                 .createBaseApi()
                 .get("DirectoryManager?",maps);
    }
}
