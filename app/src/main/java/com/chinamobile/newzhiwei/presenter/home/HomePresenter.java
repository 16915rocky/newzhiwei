package com.chinamobile.newzhiwei.presenter.home;

import android.support.annotation.NonNull;

import com.chinamobile.newzhiwei.contract.home.HomeContract;
import com.chinamobile.newzhiwei.model.home.HomeModel;

/**
 * Created by Administrator on 2018/3/22.
 */

public class HomePresenter extends HomeContract.HomePresenter{
    @NonNull
    public static HomePresenter newInstance() {
        return new HomePresenter();
    }
    @Override
    public void loadAdPictures() {
        if(mIModel==null || mIView==null){
            return;
        }
        mIView.setAdPictures(null);
      /*  mRxManager.register(mIModel.getAdPictures().subscribe(new Consumer<String[]>() {
            @Override
            public void accept(String[] strings) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mIView != null) {
                    mIView.showToast("Network error.");
                }else{
                    mIView.showNetworkError();
                }

            }
        }));*/
    }

    @Override
    public void loadNoticeText() {
        String[] strArr=new String[] { "海外助理服务，抢先体验","日本个签1799元三年多次","日本个签1999元五年多次","ADFDAFAAFDS" };
        mIView.setNoticeText(strArr);
    }

    @Override
    public HomeContract.IHomeModel getModel() {
        return HomeModel.newInstance();
    }

    @Override
    public void onStart() {

    }
}
