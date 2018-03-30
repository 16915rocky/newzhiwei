package com.chinamobile.newzhiwei.ui.fragment.home.child;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chinamobile.newzhiwei.R;
import com.chinamobile.newzhiwei.contract.home.HomeContract;
import com.chinamobiles.zhiwei.sdk.base.BasePresenter;
import com.chinamobiles.zhiwei.sdk.base.fragment.BaseMVPCompatFragment;

/**
 * Created by Administrator on 2018/3/22.
 */

public class HomeFragment extends BaseMVPCompatFragment<HomeContract.HomePresenter> implements HomeContract.IHomeView{

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
         mPresenter.loadAdPictures();
    }

    @Override
    public void setAdPictures(String[] strArr) {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void setNoticeText(String[] strArr) {

    }
}
