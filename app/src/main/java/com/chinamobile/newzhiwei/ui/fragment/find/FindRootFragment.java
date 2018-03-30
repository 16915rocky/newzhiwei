package com.chinamobile.newzhiwei.ui.fragment.find;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.newzhiwei.R;
import com.chinamobile.newzhiwei.contract.find.FindContract;
import com.chinamobile.newzhiwei.global.MyApplication;
import com.chinamobile.newzhiwei.model.bean.find.MainPageGridBean;
import com.chinamobile.newzhiwei.model.bean.find.MoreItemListBean;
import com.chinamobile.newzhiwei.presenter.find.FindPresenter;
import com.chinamobile.newzhiwei.ui.fragment.find.adapter.MoreItemEditGridAdapter;
import com.chinamobile.newzhiwei.ui.fragment.find.adapter.MoreItemListAdapter;
import com.chinamobile.newzhiwei.ui.fragment.find.messageEvent.AddItemEvent;
import com.chinamobile.newzhiwei.ui.widgets.DragGridView;
import com.chinamobile.newzhiwei.util.EncryptUtils;
import com.chinamobiles.zhiwei.sdk.base.BasePresenter;
import com.chinamobiles.zhiwei.sdk.base.fragment.BaseMVPCompatFragment;
import com.chinamobiles.zhiwei.sdk.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/3/27.
 */

public class FindRootFragment extends BaseMVPCompatFragment<FindContract.FindPresenter> implements FindContract.FindView {
    @BindView(R.id.tb_find)
    Toolbar tbFind;
    @BindView(R.id.dgv_edit)
    DragGridView dgvEdit;
    @BindView(R.id.lt_extra_add)
    LinearLayout ltExtraAdd;
    @BindView(R.id.lt_find)
    ListView ltFind;
    @BindView(R.id.tv_edit_button)
    TextView tvEditButton;
    @BindView(R.id.img_edit_button)
    ImageView imgEditButton;
    private MoreItemListAdapter milAdapter;
    private MoreItemEditGridAdapter mieAdapter;
    private List<MoreItemListBean> fullDirList;
    private List<MainPageGridBean> hpDirList;


    public static FindRootFragment newInstance() {
        Bundle args = new Bundle();
        FindRootFragment fragment = new FindRootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public BasePresenter initPresenter() {
        return FindPresenter.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.loadListContent();
        tvEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(milAdapter!=null || mieAdapter!=null){
                    milAdapter.setImgShow(true);
                    mieAdapter.setImgShow(true);
                    milAdapter.notifyDataSetChanged();
                    mieAdapter.notifyDataSetChanged();

                }
                imgEditButton.setImageResource(R.mipmap.ic_arrow_down);
            }
        });
    }

    @Override
    public Map<String, String> getAllDirParameters() {
        Map<String, String> maps = new HashMap<>();
        maps.put("action", "getAllDir");
        maps.put("deviceId", "2");
        maps.put("useType", "1");
        if (MyApplication.userBean != null) {
            maps.put("sessionId", MyApplication.userBean.getSessionId());
        }
        maps = EncryptUtils.encrypt(maps);
        return maps;
    }

    @Override
    public void setList(Map<String, Object> maps) {
        fullDirList=(List<MoreItemListBean>) maps.get("fullDirList");
        hpDirList=(List<MainPageGridBean>) maps.get("hpDirList");
         milAdapter = new MoreItemListAdapter(AppUtils.getContext(), fullDirList, false);
        ltFind.setAdapter(milAdapter);
         mieAdapter = new MoreItemEditGridAdapter(AppUtils.getContext(),hpDirList, false);
        dgvEdit.setAdapter(mieAdapter);
    }
    @Subscribe
    public void onMessageEvent(AddItemEvent event){
        MainPageGridBean mainPageGridBean;
        if(mieAdapter!=null) {
            if(hpDirList.size()<8){
                mainPageGridBean=event.mainPageGridBean;
                hpDirList.add(mainPageGridBean);
                mieAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(mContext,"最多添加8个应用",Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }




}
