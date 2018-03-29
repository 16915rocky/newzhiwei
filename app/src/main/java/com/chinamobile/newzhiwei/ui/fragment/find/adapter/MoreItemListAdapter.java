package com.chinamobile.newzhiwei.ui.fragment.find.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.newzhiwei.R;
import com.chinamobile.newzhiwei.model.bean.find.MainPageGridBean;
import com.chinamobile.newzhiwei.model.bean.find.MoreItemListBean;
import com.chinamobile.newzhiwei.ui.widgets.CustomGridView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/30.
 */

public class MoreItemListAdapter extends BaseAdapter {

    private List<MoreItemListBean> mList;
    private Context mContext;
    boolean isImgShow=false;
    private MainPageGridBean compareMb;


    public MoreItemListAdapter(Context context , List<MoreItemListBean> list, boolean isImgShow) {
        this.mList=list;
        this.mContext=context;
        this.isImgShow=isImgShow;

    }

    public void setCompareMb(MainPageGridBean compareMb) {
        this.compareMb = compareMb;
    }

    public void setImgShow(boolean imgShow) {
        isImgShow = imgShow;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder viewHolder;
        if(view==null){
            viewHolder=new MyViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.view_more_item_list_item, null);
            viewHolder.title= (TextView) view.findViewById(R.id.tv_more_item_title);
            viewHolder.customGridView= (CustomGridView) view.findViewById(R.id.gv_more_item_grid);
            view.setTag(viewHolder);
        }else{
            viewHolder= (MyViewHolder) view.getTag();
        }
        viewHolder.title.setText(mList.get(i).getD_name());
        MoreItemGridInListAdapter mIgILAdapter=new MoreItemGridInListAdapter(mContext,mList.get(i).getDirList(),isImgShow,compareMb);
        if(compareMb!=null){
            mIgILAdapter.notifyDataSetChanged();
        }
        if(isImgShow){
            mIgILAdapter.notifyDataSetChanged();

        }
        viewHolder.customGridView.setTag(mList.get(i).getDirList());
        viewHolder.customGridView.setAdapter(mIgILAdapter);
        viewHolder.customGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //Toast.makeText(mContext,"第"+i+"个item",Toast.LENGTH_SHORT).show();
                CustomGridView cgv=(CustomGridView)adapterView;
                if(cgv!=null) {
                    List<MainPageGridBean> list = (List<MainPageGridBean>) cgv.getTag();


                }
            }
        });

        return view;
    }




    class MyViewHolder {
       private CustomGridView customGridView;
       private TextView title;
    }
}
