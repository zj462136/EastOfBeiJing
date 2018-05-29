package com.example.lenovo.eastofbeijing.ui.dingdan.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.GetCartsBean;
import com.example.lenovo.eastofbeijing.bean.SellerBean;
import com.example.lenovo.eastofbeijing.ui.dingdan.presenter.MakeSureOrderPresenter;
import com.example.lenovo.eastofbeijing.ui.widget.AddSubView;
import com.example.lenovo.eastofbeijing.utils.SharedPreferencesUtils;

import java.util.List;

public class MakeSureOrderAdapter extends BaseExpandableListAdapter {
    private final String token;
    private Context context;
    private List<SellerBean> groupList;
    private List<List<GetCartsBean.DataBean.ListBean>> childList;
    private LayoutInflater inflater;
    private ProgressDialog progressDialog;
    private MakeSureOrderPresenter mPresenter;
    private final String uid;

    public MakeSureOrderAdapter(Context context, List<SellerBean> groupList, List<List<GetCartsBean.DataBean
            .ListBean>> childList, ProgressDialog progressDialog, MakeSureOrderPresenter mPrsenter) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
        this.progressDialog = progressDialog;
        this.mPresenter = mPrsenter;
        inflater = LayoutInflater.from(context);

        //获取UID和token值
        uid = (String) SharedPreferencesUtils.getParam(context, "uid", "");
        token = (String) SharedPreferencesUtils.getParam(context, "token", "");
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.shopcart_seller_item, null);
            groupViewHolder.cbSeller = convertView.findViewById(R.id.cbSeller);
            groupViewHolder.tvSeller = convertView.findViewById(R.id.tvSeller);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        //设置值
        SellerBean sellerBean = groupList.get(groupPosition);
        groupViewHolder.tvSeller.setText(sellerBean.getSellerName());
        groupViewHolder.cbSeller.setChecked(sellerBean.isSelected());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup
                                     parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            childViewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.shopcart_seller_product_item, null);
            childViewHolder.cbProduct = convertView.findViewById(R.id.cbProduct);
            childViewHolder.iv = convertView.findViewById(R.id.iv);
            childViewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            childViewHolder.tvPrice = convertView.findViewById(R.id.tvPrice);
            childViewHolder.tvDel = convertView.findViewById(R.id.tvDel);
            childViewHolder.addSubView = convertView.findViewById(R.id.addSubCard);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        final GetCartsBean.DataBean.ListBean listBean = childList.get(groupPosition).get(childPosition);
        //根据服务器返回的select值，给checkBox设置是否选中
        childViewHolder.cbProduct.setChecked(listBean.getSelected() == 1 ? true : false);
        childViewHolder.tvTitle.setText(listBean.getTitle());
        childViewHolder.tvPrice.setText(listBean.getPrice() + "");
        Glide.with(context).load(listBean.getImages().split("\\|")[0]).into(childViewHolder.iv);
        childViewHolder.addSubView.setNum(listBean.getNum() + "");

        //给二级列表的checkbox设置点击事件
        childViewHolder.cbProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //其实就是更新购物车列表
                progressDialog.show();
                String sellerid = groupList.get(groupPosition).getSellerid();
                int pid = listBean.getPid();
                String num = childViewHolder.addSubView.getNum();
                boolean checked = childViewHolder.cbProduct.isChecked();
                int selected = checked ? 1 : 0;
                mPresenter.updateCarts(uid, sellerid, pid + "", num, selected + "", token);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        CheckBox cbSeller;
        TextView tvSeller;
    }

    class ChildViewHolder {
        CheckBox cbProduct;
        ImageView iv;
        TextView tvTitle;
        TextView tvPrice;
        TextView tvDel;
        AddSubView addSubView;
    }
}
