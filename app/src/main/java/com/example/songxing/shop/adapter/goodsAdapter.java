package com.example.songxing.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.songxing.shop.Bean.goodsBean;
import com.example.songxing.shop.R;
import com.example.songxing.shop.activity.orderActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by songxing on 2019/5/19.
 */

public class goodsAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<goodsBean> goodsBeans;
    public goodsAdapter(ArrayList<goodsBean> goodsBeans,Context context){
        goodsAdapter.this.goodsBeans = goodsBeans;
        goodsAdapter.this.context = context;
    }

    @Override
    public int getCount() {
        return goodsBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return goodsBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return  i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        //i当前位置，convertView传过来的视图，viewGroup显示的视图
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.goodsitem,null);

            //对viewHolder的属性进行赋值
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.img);
            viewHolder.textView=(TextView)convertView.findViewById(R.id.name);
            viewHolder.textView1=(TextView)convertView.findViewById(R.id.name1);
            viewHolder.textView2=(TextView)convertView.findViewById(R.id.name2);
            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        }
        else{
            //如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder=(ViewHolder)convertView.getTag();
        }

        // 设置控件的数据
        Picasso.with(context).load(goodsBeans.get(i).getPictureurl()).into(viewHolder.imageView);
        viewHolder.textView.setText(goodsBeans.get(i).getName());
        viewHolder.textView1.setText(goodsBeans.get(i).getDescription());
        viewHolder.textView2.setText(goodsBeans.get(i).getPrice());
        viewHolder.button = (Button) convertView.findViewById(R.id.add);
        viewHolder.button1 = (Button) convertView.findViewById(R.id.reduceButton);
        //加商品
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(i);
            }
        });

        //减商品
        viewHolder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListenera.onAdd(i);
            }
        });
        return convertView;
   }
    //加商品接口
     public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }
    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }
    //减商品接口
    public interface onItemDeleteListenera {
        void onAdd(int i);
    }
    private onItemDeleteListenera mOnItemDeleteListenera;

    public void setOnItemDeleteClickListenera(onItemDeleteListenera mOnItemDeleteListenera) {
        this.mOnItemDeleteListenera = mOnItemDeleteListenera;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public TextView textView1;
        public TextView textView2;
        public Button button;
        public Button button1;

    }
}
