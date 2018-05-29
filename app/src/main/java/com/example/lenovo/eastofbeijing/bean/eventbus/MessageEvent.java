package com.example.lenovo.eastofbeijing.bean.eventbus;

import com.example.lenovo.eastofbeijing.bean.GetCartsBean;
import com.example.lenovo.eastofbeijing.bean.SellerBean;

import java.util.List;

public class MessageEvent {
    private List<SellerBean> gList;
    private List<List<GetCartsBean.DataBean.ListBean>> cList;

    public List<SellerBean> getgList() {
        return gList;
    }

    public void setgList(List<SellerBean> gList) {
        this.gList = gList;
    }

    public List<List<GetCartsBean.DataBean.ListBean>> getcList() {
        return cList;
    }

    public void setcList(List<List<GetCartsBean.DataBean.ListBean>> cList) {
        this.cList = cList;
    }
}
