package com.jzsec.broker.ui.contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzsec.broker.R;
import com.jzsec.broker.base.listener.OnItemClickListener;
import com.jzsec.broker.data.entity.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaopan on 16/7/27.
 * e-mail: kangqiao610@gmail.com
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.VH>{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Customer> mItems = new ArrayList<>();

    private OnItemClickListener mClickListener;

    public CustomerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setDataList(List<Customer> beans) {
        mItems.clear();
        mItems.addAll(beans);
        notifyDataSetChanged();
    }

    public void refreshMsg(Customer bean) {
        int index = mItems.indexOf(bean);
        if (index < 0) return;

        notifyItemChanged(index);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_customer, parent, false);
        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(holder.getAdapterPosition(), v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Customer item = mItems.get(position);

        holder.tvName.setText(item.name);
        holder.tvMsg.setText(item.message);
        holder.tvTime.setText("昨天");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public Customer getCustomer(int position) {
        return mItems.get(position);
    }


    class VH extends RecyclerView.ViewHolder{
        private ImageView imgAvatar;
        private TextView tvName, tvMsg, tvTime;

        public VH(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
