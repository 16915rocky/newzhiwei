package com.chinamobile.newzhiwei.ui.fragment.find.messageEvent;

import com.chinamobile.newzhiwei.model.bean.find.MainPageGridBean;

/**
 * Created by Administrator on 2018/3/29.
 */

public class AddItemEvent {
    public final MainPageGridBean  mainPageGridBean;

    public AddItemEvent(MainPageGridBean mainPageGridBean) {
        this.mainPageGridBean=mainPageGridBean;
    }
}
