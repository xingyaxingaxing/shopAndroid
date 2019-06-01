package com.example.songxing.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.songxing.shop.Bean.myOrderListBean;
import com.example.songxing.shop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by songxing on 2019/5/29.
 */

public class myOrderListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<myOrderListBean>myOrderListBeans;
    public myOrderListAdapter(ArrayList<myOrderListBean>myOrderListBeans,Context context) {
        myOrderListAdapter.this.myOrderListBeans=myOrderListBeans;
        myOrderListAdapter.this.context=context;
    }

    @Override
    public int getCount() {
        return myOrderListBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return myOrderListBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            viewHolder=new ViewHolder();
            //对viewHolder的属性进行赋值
            view= LayoutInflater.from(context).inflate(R.layout.myorderlistitem,null);
            viewHolder.imageView=view.findViewById(R.id.myOrderListItemImage);
            viewHolder.textView1=view.findViewById(R.id.myOrderListItemText2);
            viewHolder.textView2=view.findViewById(R.id.myOrderListItemText4);
            viewHolder.textView3=view.findViewById(R.id.myOrderListItemText5);
            viewHolder.textView4=view.findViewById(R.id.myOrderListItemButton);
            view.setTag(viewHolder);
        }
        else {
            //如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder=(myOrderListAdapter.ViewHolder)view.getTag();
        }
        // 设置控件的数据
        Picasso.with(context).load(myOrderListBeans.get(i).getPictureUrl()).into(viewHolder.imageView);
        viewHolder.textView1.setText(myOrderListBeans.get(i).getCreateTime());
        viewHolder.textView2.setText(myOrderListBeans.get(i).getOderAmount());
        viewHolder.textView3.setText(myOrderListBeans.get(i).getOderStatus());
        viewHolder.textView4.setText(myOrderListBeans.get(i).getPayStatus());
        viewHolder.button2=(Button)view.findViewById(R.id.myOrderListItemButton1);
        viewHolder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemDeleteListener.Detail(i,myOrderListBeans.get(i).getOderId());
            }
        });
        return view;
    }
    //订单详情接口
    public interface onDetailListener{
        void Detail(int i,String orderId);
    }
    private onDetailListener mOnItemDeleteListener;
    public void setDetailListener(onDetailListener onItemDeleteListener){
        this.mOnItemDeleteListener=onItemDeleteListener;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        Button button2;
    }
}
