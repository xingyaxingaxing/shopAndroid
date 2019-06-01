package com.example.songxing.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.songxing.shop.Bean.OrderDetailBean;
import com.example.songxing.shop.Bean.OrderDetailUserBean;
import com.example.songxing.shop.Head;
import com.example.songxing.shop.R;
import com.example.songxing.shop.adapter.orderDetailAdapyer;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by songxing on 2019/5/30.
 */
public class OrderDetailAwait extends Activity {
    private ArrayList<OrderDetailBean> orderDetailBeans=new ArrayList<>();
    private ArrayList<OrderDetailUserBean> orderDetailUserBeans=new ArrayList<>();
    private String path="";
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetailawait);
        listView=findViewById(R.id.orderDetailAwaitListView);

        Button buttona=findViewById(R.id.orderDetailButton);

        Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        String oderId=bundle.getString("orderId");

        try{
            HttpUtils httpUtils=new HttpUtils();
            path="http://songxingxing.natapp1.cc/sell/buyer/user/detailList?oderId="+oderId;
           // RequestParams parm=new RequestParams();
           // parm.addBodyParameter("isok", Head.isOk());
           // parm.addBodyParameter("oderId","1556197818804931886");
             httpUtils.send(HttpRequest.HttpMethod.GET, path, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    try{
                        JSONObject jsonObject=new JSONObject(responseInfo.result);
                        String json_list=jsonObject.optString("data","{}");
                        JSONObject jsonObject11=new JSONObject(json_list);

                        String buyerNme=jsonObject11.optString("buyerNme","");
                        String buyerPhone=jsonObject11.optString("buyerPhone","");
                        String buyerAddress=jsonObject11.optString("buyerAddress","");
                        String buyerOpenid=jsonObject11.optString("buyerOpenid","");
                        String oderId=jsonObject11.optString("oderId","");
                        String oderAmount=jsonObject11.optString("oderAmount","");
                        String oderStatus=jsonObject11.optString("oderStatus","");
                        String createTime=jsonObject11.optString("createTime","");
                        OrderDetailUserBean orderDetailBean1=new OrderDetailUserBean();
                        orderDetailBean1.setBuyerNme(buyerNme);
                        orderDetailBean1.setBuyerPhone(buyerPhone);
                        orderDetailBean1.setBuyerAddress(buyerAddress);
                        orderDetailBean1.setBuyerOpenid(buyerOpenid);
                        orderDetailBean1.setOderId(oderId);
                        orderDetailBean1.setOderAmount(oderAmount);
                        orderDetailBean1.setOderStatus(oderStatus);
                        orderDetailBean1.setCreateTime(createTime);

                        orderDetailUserBeans.add(orderDetailBean1);


                        String json_list22=jsonObject11.optString("oderDetailList","[]");
                        JSONArray jsonArray=new JSONArray(json_list22);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            OrderDetailBean orderDetailBean=new OrderDetailBean();
                            String productName=jsonObject1.optString("productName","");
                            String productPrice=jsonObject1.optString("productPrice","");
                            String productQuantity=jsonObject1.optString("productQuantity","");
                            String productIcon=jsonObject1.optString("productIcon","");
                            String productId=jsonObject1.optString("productId","");
                            orderDetailBean.setProductName(productName);
                            orderDetailBean.setProductPrice(productPrice);
                            orderDetailBean.setProductQuantity(productQuantity);
                            orderDetailBean.setProductIcon(productIcon);
                            orderDetailBean.setProductId(productId);
                            orderDetailBeans.add(orderDetailBean);
                        }
                        orderDetailAdapyer orderDetailAdapyer=new orderDetailAdapyer(orderDetailBeans,OrderDetailAwait.this);
                        listView.setAdapter(orderDetailAdapyer);
                    }catch (Exception e){

                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(OrderDetailAwait.this,"数据加载失败",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){

        }


        //支付订单
        buttona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<OrderDetailBean>productBeans=new ArrayList<>();
                //获取ID和数量
                //订单里商品ID和数量
                for(int i=0;i<orderDetailBeans.size();i++){
                    final TextView goodListText=listView.getChildAt(i).findViewById(R.id.detailItemText3);
                    //取商品数量
                    String number = goodListText.getText().toString();
                    OrderDetailBean productBean=new OrderDetailBean();
                    productBean.setProductId(orderDetailBeans.get(i).getProductId());
                    productBean.setProductQuantity(number);
                    productBeans.add(productBean);
                }
                //将id和数量转化为json字符串
                String s = JSON.toJSONString(productBeans);
                String path="http://songxingxing.natapp1.cc/sell/buyer/user/awaitPaid";
                HttpUtils httpUtils=new HttpUtils();//处理网络连接
                final RequestParams params=new RequestParams();//传参数到服务器
                params.addBodyParameter("isok", Head.isOk());// 只包含字符串参数时默认使用BodyParamsEntity

                params.addBodyParameter("oderId",orderDetailUserBeans.get(0).getOderId());
                params.addBodyParameter("name",orderDetailUserBeans.get(0).getBuyerNme());
                params.addBodyParameter("buyerPhone",orderDetailUserBeans.get(0).getBuyerPhone());
                params.addBodyParameter("buyerAddress",orderDetailUserBeans.get(0).getBuyerAddress());
                params.addBodyParameter("buyerOpenid",orderDetailUserBeans.get(0).getBuyerOpenid());
                params.addBodyParameter("oderAmount",orderDetailUserBeans.get(0).getOderAmount());
                params.addBodyParameter("payStatus","1");
                params.addBodyParameter("createTime",orderDetailUserBeans.get(0).getCreateTime());
                params.addBodyParameter("oderStatus",orderDetailUserBeans.get(0).getOderStatus());


                httpUtils.send(HttpRequest.HttpMethod.POST, path,params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        System.out.println( "responseInfo==="+responseInfo.result);
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            String message=jsonObject.optString("msg","");
                            Toast.makeText(OrderDetailAwait.this,message,Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent1=new Intent(OrderDetailAwait.this,goodsActivity.class);
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(OrderDetailAwait.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
