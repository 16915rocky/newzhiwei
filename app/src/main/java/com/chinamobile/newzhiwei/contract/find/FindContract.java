package com.chinamobile.newzhiwei.contract.find;

import com.chinamobiles.zhiwei.sdk.base.BasePresenter;
import com.chinamobiles.zhiwei.sdk.base.IBaseFragment;
import com.chinamobiles.zhiwei.sdk.base.IBaseModel;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface FindContract {

    abstract class FindPresenter extends BasePresenter<FindModel,FindView>{
        /*
        * 加载列表信息
        * */
        public abstract void loadListContent();
    }

    interface FindView extends IBaseFragment{
        /*
        * 请求目录参数
        * */
        Map<String,String> getAllDirParameters();
        /*
        * 填充列表
        * */
        void setList(Map<String,Object> maps);
    }
    interface FindModel extends IBaseModel{

        Flowable<ResponseBody> getListContent(Map<String,String> maps);
    }
}
