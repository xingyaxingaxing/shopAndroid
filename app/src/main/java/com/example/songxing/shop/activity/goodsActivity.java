package com.example.songxing.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.songxing.shop.Bean.ProductBean;
import com.example.songxing.shop.Bean.goodsBean;
import com.example.songxing.shop.Head;
import com.example.songxing.shop.R;
import com.example.songxing.shop.adapter.goodsAdapter;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by songxing on 2019/5/19.
 */

public class goodsActivity extends Activity  {
    private Context context=null;
    private Context context1=null;
    private ArrayList<goodsBean> goodsBeans=new ArrayList<>();
    private String path="";
    private ListView listView;

    double a=0.00;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods);
        listView=findViewById(R.id.goodsList);

        try{
            path="http://songxingxing.natapp1.cc/sell/buyer/product/list";
            HttpUtils httpUtils=new HttpUtils();//处理网络连接
            final RequestParams params=new RequestParams();//传参数到服务器
            params.addBodyParameter("isok", Head.isOk());// 只包含字符串参数时默认使用BodyParamsEntity
            //new RequestCallBack<String>请求的回调函数
            httpUtils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
                @Override
                public void onFailure(HttpException e, String s) {
                    System.out.println("出错："+s);
                    Toast.makeText(goodsActivity.this,"数据加载失败！",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Toast.makeText(goodsActivity.this,"数据加载成功！",Toast.LENGTH_SHORT).show();
                    try{
                        JSONObject jsonObject=new JSONObject(responseInfo.result);
                        String js_list0=jsonObject.optString("data","[]");
                        JSONArray array0 = new JSONArray(js_list0);


                        //遍历json[]中的[]
                        for(int ii=0;ii<array0.length();ii++) {
                            JSONObject jsonObject11 = array0.getJSONObject(ii);
                            String js_list12 = jsonObject11.optString("foods", "[]");
                            JSONArray array = new JSONArray(js_list12);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                goodsBean bean = new goodsBean();
                                String name = jsonObject1.optString("name", "");
                                String pictureurl = jsonObject1.optString("icon", "");
                                String price = jsonObject1.optString("price", "");
                                String description = jsonObject1.optString("description", "");
                                String prodictId = jsonObject1.optString("id", "");
                                bean.setName(name);
                                bean.setDescription(description);
                                bean.setPictureurl(pictureurl);
                                bean.setPrice(price);
                                bean.setProdictId(prodictId);
                                goodsBeans.add(bean);
                            }
                        }
                        //遍历json[]中的[]


                        goodsAdapter goodsAdapter = new goodsAdapter(goodsBeans, goodsActivity.this);
                        listView.setAdapter(goodsAdapter);

                        //ListView item 中的add按钮的点击事件
                        goodsAdapter.setOnItemDeleteClickListener(new goodsAdapter.onItemDeleteListener() {
                            @Override
                            public void onDeleteClick(int i) {
                                a = a + Double.parseDouble(goodsBeans.get(i).getPrice());
                                String b = String.valueOf(a);
                                TextView textView = findViewById(R.id.countMoney);
                                textView.setText(b);
                                //获取当前item下里面的控件值
                                final TextView goodListText = listView.getChildAt(i).findViewById(R.id.goodListViewText);
                                //取商品数量和重新设置控件值
                                String number = goodListText.getText().toString();
                                int m = 0;
                                try {
                                    i = Integer.parseInt(number);
                                    m = i + 1;
                                } catch (Exception e) {
                                }
                                final String countText = String.valueOf(m);
                                goodsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        goodListText.setText(countText);
                                    }
                                });

                            }
                        });


                        //ListView item 中的减商品按钮的点击事件
                        goodsAdapter.setOnItemDeleteClickListenera(new goodsAdapter.onItemDeleteListenera() {
                            @Override
                            public void onAdd(int i) {
                                if (a >= Double.parseDouble(goodsBeans.get(i).getPrice())) {
                                    final TextView goodListText = listView.getChildAt(i).findViewById(R.id.goodListViewText);
                                    //取商品数量和重新设置控件值
                                    String number = goodListText.getText().toString();
                                    int k = Integer.parseInt(number);
                                    if (k > 0) {
                                        a = a - Double.parseDouble(goodsBeans.get(i).getPrice());
                                        String b = String.valueOf(a);
                                        TextView textView = findViewById(R.id.countMoney);
                                        textView.setText(b);
                                        //获取当前item下里面的控件值
                                        int m = 0;
                                        try {
                                            m = k - 1;
                                        } catch (Exception e) {
                                        }
                                        final String countText = String.valueOf(m);
                                        goodsActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                goodListText.setText(countText);
                                            }
                                        });
                                    } else {
                                        Toast.makeText(goodsActivity.this, "商品不能再少了哦", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(goodsActivity.this, "商品不能再少了哦", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        //显示待支付的价格
                        Button button=findViewById(R.id.goodsButton);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ArrayList<ProductBean>productBeans=new ArrayList<>();
                                //获取ID和数量
                                //订单里商品ID和数量
                                int amount=0;
                                  for(int i=0;i<goodsBeans.size();i++){
                                  final TextView goodListText=listView.getChildAt(i).findViewById(R.id.goodListViewText);
                                    //取商品数量和重新设置控件值
                                    String number = goodListText.getText().toString();
                                    int n=Integer.parseInt(number);
                                    if(n!=0){
                                        ProductBean productBean=new ProductBean();
                                        productBean.setProductId(goodsBeans.get(i).getProdictId());
                                        productBean.setProductQuantity(String.valueOf(n));
                                        productBeans.add(productBean);
                                        System.out.println("m======"+productBeans);
                                    }
                                    amount=amount+n;
                                }
                                if(amount==0)
                                {
                                    goodsActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(goodsActivity.this,"商品不能为空",Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                else {
                                    //将id和数量转化为json字符串
                                    String s = JSON.toJSONString(productBeans);
                                    String bb=String.valueOf(a);
                                    Intent intent=new Intent(goodsActivity.this,orderActivity.class);
                                    intent.putExtra("AllPrice", bb);
                                    intent.putExtra("jsonstr", s);
                                    startActivity(intent);
                                }

                            }
                        });
                        //我的订单
                        Button button1=findViewById(R.id.goodsButton1);
                        button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(goodsActivity.this,myOrderList.class);
                                startActivity(intent);
                            }
                        });
                    }catch (Exception e){
                    }
                }

                @Override
                public void onStart() {
                    super.onStart();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
