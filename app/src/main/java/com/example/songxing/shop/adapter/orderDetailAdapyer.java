package com.example.songxing.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.songxing.shop.Bean.OrderDetailBean;
import com.example.songxing.shop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by songxing on 2019/5/30.
 */

public class orderDetailAdapyer extends BaseAdapter {
    private Context context;
    private ArrayList<OrderDetailBean>orderDetailBeans;
    public orderDetailAdapyer(ArrayList<OrderDetailBean>orderDetailBeans,Context context) {
        orderDetailAdapyer.this.orderDetailBeans=orderDetailBeans;
        orderDetailAdapyer.this.context=context;
    }

    @Override
    public int getCount() {
        return orderDetailBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return orderDetailBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.orderdetailitem,null);

            //对viewHolder的属性进行赋值
            viewHolder.imageView=(ImageView)view.findViewById(R.id.detailItemImg);
            viewHolder.textView=(TextView)view.findViewById(R.id.detailItemText1);
            viewHolder.textView1=(TextView)view.findViewById(R.id.detailItemText3);
            viewHolder.textView5=(TextView)view.findViewById(R.id.detailItemText5);
            //通过setTag将convertView与viewHolder关联
            view.setTag(viewHolder);
        }
        else{
            //如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder=(orderDetailAdapyer.ViewHolder)view.getTag();
        }
        Picasso.with(context).load(orderDetailBeans.get(i).getProductIcon()).into(viewHolder.imageView);
        viewHolder.textView.setText(orderDetailBeans.get(i).getProductPrice());
        viewHolder.textView1.setText(orderDetailBeans.get(i).getProductQuantity());
        viewHolder.textView5.setText(orderDetailBeans.get(i).getProductName());
        return view;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public TextView textView1;
        public TextView textView5;
    }
}
