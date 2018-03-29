package com.chinamobile.newzhiwei.presenter.find;

import com.chinamobile.newzhiwei.contract.find.FindContract;
import com.chinamobile.newzhiwei.model.bean.find.MainPageGridBean;
import com.chinamobile.newzhiwei.model.bean.find.MoreItemListBean;
import com.chinamobile.newzhiwei.model.find.FindModel;
import com.chinamobile.newzhiwei.net.CustomConsumer;
import com.chinamobile.newzhiwei.net.ResponseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/27.
 */

public class FindPresenter extends FindContract.FindPresenter {

    public static FindPresenter newInstance() {
        return new FindPresenter();
    }
    @Override
    public void loadListContent() {
        if(mIView==null || mIModel==null){
            return;
        }
        mIModel.getListContent(mIView.getAllDirParameters()).subscribe(new CustomConsumer<ResponseBody>() {
            @Override
            public void onCustomNext(ResponseBean responseBean) {
                if(responseBean!=null){
                  if(responseBean.isSuccess()){
                      JSONObject jo = null;
                      try {
                          jo = new JSONObject(responseBean.getDATA());
                          Type type1 = new TypeToken<List<MainPageGridBean>>() {
                          }.getType();
                          List<MainPageGridBean> hotDirList = new Gson().fromJson(jo.getJSONArray("hotDir").toString(), type1);
                          List<MainPageGridBean> recentDirList = new Gson().fromJson(jo.getJSONArray("recentDir").toString(), type1);
                          List<MainPageGridBean> hpDirList = new Gson().fromJson(jo.getJSONArray("hpDir").toString(), type1);
                          MoreItemListBean moreItemListBean1 = new MoreItemListBean("最近", recentDirList);
                          MoreItemListBean moreItemListBean2 = new MoreItemListBean("推荐", hotDirList);
                          Type type2 = new TypeToken<List<MoreItemListBean>>() {
                          }.getType();
                          List<MoreItemListBean> fullDirList = new Gson().fromJson(jo.getJSONArray("fullDir").toString(), type2);
                          fullDirList.add(0, moreItemListBean1);
                          fullDirList.add(1, moreItemListBean2);
                          Map<String, Object> maps = new HashMap<>();
                          maps.put("fullDirList",fullDirList);
                          maps.put("hpDirList",hpDirList);
                          mIView.setList(maps);
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                  }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

    }

    @Override
    public FindContract.FindModel getModel() {
        return FindModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
