package com.chinamobile.newzhiwei.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.chinamobile.newzhiwei.R;
import com.chinamobile.newzhiwei.contract.home.HomeContract;
import com.chinamobile.newzhiwei.presenter.home.HomePresenter;
import com.youth.banner.Banner;
import com.chinamobiles.zhiwei.sdk.base.BasePresenter;
import com.chinamobiles.zhiwei.sdk.base.fragment.BaseMVPCompatFragment;
import com.chinamobiles.zhiwei.sdk.utils.AppUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/22.
 */

public class HomeRootFragment extends BaseMVPCompatFragment<HomeContract.HomePresenter> implements HomeContract.IHomeView {


    @BindView(R.id.banner_main)
    Banner bannerMain;
    private static final int NOTICE_CODE = 0x1;
    @BindView(R.id.vf_home_ad)
    ViewFlipper vfHomeAd;


    private String[] strArrNotice;
    private int mSwitcherCount = 0;


    public static HomeRootFragment newInstance() {
        Bundle args = new Bundle();
        HomeRootFragment fragment = new HomeRootFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return HomePresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }
    /*
    *初始化
    * */
    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        mPresenter.loadAdPictures();
        mPresenter.loadNoticeText();
    }


    @Override
    public void setAdPictures(String[] strArr) {
        String[] images = new String[]{
                "http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg",
                "http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg",
                "http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg",
                "http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg"};
        bannerMain.setBannerStyle(Banner.CIRCLE_INDICATOR);
        bannerMain.setIndicatorGravity(Banner.RIGHT);
        bannerMain.isAutoPlay(true);
        bannerMain.setDelayTime(3000);
        bannerMain.setImages(images);
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void setNoticeText(String[] strArr) {

        View view1 = LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.view_home_ad_item, null);
        TextView tvAdNum1 = (TextView) view1.findViewById(R.id.tv_ad_num1);
        TextView tvAdNum2 = (TextView) view1.findViewById(R.id.tv_ad_num2);
        tvAdNum1.setText(strArr[0]);
        tvAdNum2.setText(strArr[1]);
        vfHomeAd.addView(view1);
        View view2 = LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.view_home_ad_item, null);
        TextView tvAdNum21 = (TextView) view2.findViewById(R.id.tv_ad_num1);
        TextView tvAdNum22 = (TextView) view2.findViewById(R.id.tv_ad_num2);
        tvAdNum21.setText(strArr[2]);
        tvAdNum22.setText(strArr[3]);
        vfHomeAd.addView(view2);
    }



}
