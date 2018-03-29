package com.chinamobile.newzhiwei.contract.home;

import com.zyw.horrarndoo.sdk.base.BasePresenter;
import com.zyw.horrarndoo.sdk.base.IBaseFragment;
import com.zyw.horrarndoo.sdk.base.IBaseModel;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/22.
 */

public interface HomeContract {

    abstract class HomePresenter extends BasePresenter<IHomeModel,IHomeView>{
        /*
        * 加载广告图片
        * */
        public abstract void loadAdPictures();
        /*
        * 加载滚动广播
        * */
        public abstract void loadNoticeText();
    }
    interface IHomeModel extends IBaseModel{
        Flowable<ResponseBody> getAdPictures(Map<String,String> maps);
        Flowable<ResponseBody> getNoticeText(Map<String,String> maps);

    }
    interface IHomeView extends IBaseFragment{
        /*
        * 设置广告图片
        * */
        void setAdPictures(String[] strArr);
        /**
         * 显示网络错误
         */
        void showNetworkError();
        /*
        * 设置滚动广播
        * */
        void setNoticeText(String[] strArr);
    }
}
