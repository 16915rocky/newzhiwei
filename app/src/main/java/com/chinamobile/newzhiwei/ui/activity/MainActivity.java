package com.chinamobile.newzhiwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.chinamobile.newzhiwei.R;
import com.chinamobile.newzhiwei.ui.fragment.find.FindRootFragment;
import com.chinamobile.newzhiwei.ui.fragment.home.HomeRootFragment;
import com.chinamobile.newzhiwei.util.StatusBarUtil;
import com.chinamobiles.zhiwei.sdk.base.activity.BaseCompatActivity;
import com.chinamobiles.zhiwei.sdk.helper.BottomNavigationViewHelper;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2018/3/22.
 */

public class MainActivity extends BaseCompatActivity {
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bviv_bar)
    BottomNavigationView bottomNavigationView;
    public static final int FIRST=0;
    public static final int SECOND=1;
    public static final int THIRD=2;
    public SupportFragment[] mFragments=new SupportFragment[3];


    @Override
    protected void initView(Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorLightBlue));
        if(savedInstanceState==null){
            mFragments[FIRST]= HomeRootFragment.newInstance();
            mFragments[SECOND]= FindRootFragment.newInstance();
            mFragments[THIRD]= HomeRootFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container,FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);

        }else{
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()
            // 自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(HomeRootFragment.class);
            mFragments[SECOND] = findFragment(FindRootFragment.class);
            mFragments[THIRD] = findFragment(HomeRootFragment.class);
        }

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_home:
                        showHideFragment(mFragments[FIRST]);
                        break;
                    case R.id.menu_item_find:
                        showHideFragment(mFragments[SECOND]);
                        break;
                    case R.id.menu_item_personal:
                        showHideFragment(mFragments[THIRD]);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


}
