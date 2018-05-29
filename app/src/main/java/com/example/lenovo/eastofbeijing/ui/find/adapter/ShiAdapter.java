package com.example.lenovo.eastofbeijing.ui.find.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.ShiPinBean;
import com.example.lenovo.eastofbeijing.ui.find.holder.ShiHolder;

import java.util.List;

public class ShiAdapter extends RecyclerView.Adapter<ShiHolder>{
    Context context;
    List<ShiPinBean.DataBean> data;

    public ShiAdapter(Context context, List<ShiPinBean.DataBean> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public ShiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.shi_layout,null);
        ShiHolder holder=new ShiHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShiHolder holder, int position) {
//        图片展示使用Fresco图片加载框架
        holder.draweeView1.setImageURI(data.get(position).getProfile_image());
        holder.draweeView2.setUp(data.get(position).getVideouri(),position,"视频标题");
        holder.text_name.setText(data.get(position).getName());
        holder.text_time.setText(data.get(position).getPasstime());
        holder.text_title.setText(data.get(position).getText());
    }

    @Override
    public int getItemCount() {
        if (data==null){
            return 0;
        }
        return data.size();
    }
}
