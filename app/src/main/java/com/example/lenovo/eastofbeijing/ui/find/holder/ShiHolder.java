package com.example.lenovo.eastofbeijing.ui.find.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.eastofbeijing.R;
import com.facebook.drawee.view.SimpleDraweeView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class ShiHolder extends RecyclerView.ViewHolder {
    public SimpleDraweeView draweeView1;
    public JCVideoPlayerStandard draweeView2;
    public TextView text_name;
    public TextView text_time;
    public TextView text_title;

    public ShiHolder(View itemView) {
        super(itemView);
        draweeView1 = itemView.findViewById(R.id.drawee_user);
        draweeView2 = itemView.findViewById(R.id.drawee_image);
        text_name = itemView.findViewById(R.id.text_name);
        text_time = itemView.findViewById(R.id.text_time);
        text_title = itemView.findViewById(R.id.text_title);
    }
}
