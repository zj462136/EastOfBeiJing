package com.example.lenovo.eastofbeijing.ui.listofgoods;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lenovo.eastofbeijing.R;
import com.example.lenovo.eastofbeijing.bean.ProductsBean;

import java.util.List;

public class ProDuctListAdapter extends RecyclerView.Adapter<ProductListHolder> {
    private List<ProductsBean.DataBean> listAll;
    private Context context;
    private OnListItemClickListener onListItemClickListener;

    public ProDuctListAdapter(Context context, List<ProductsBean.DataBean> listAll) {
        this.context = context;
        this.listAll = listAll;
    }

    @Override
    public ProductListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.product_list_item_layout, null);
        ProductListHolder productListHolder = new ProductListHolder(view);
        return productListHolder;
    }

    @Override
    public void onBindViewHolder(ProductListHolder holder, final int position) {
        final ProductsBean.DataBean dataBean = listAll.get(position);
        holder.product_list_title.setText(dataBean.getTitle());
        holder.product_list_price.setText("¥" + dataBean.getBargainPrice());
        Glide.with(context).load(dataBean.getImages().split("\\|")[0]).into(holder.product_list_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onListItemClickListener != null) {
                    onListItemClickListener.OnItemClick(dataBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAll.size();
    }

    public interface OnListItemClickListener {
        void OnItemClick(ProductsBean.DataBean dataBean);
    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }
}
