package com.example.songxing.shop.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.songxing.shop.Bean.myOrderListBean;
import com.example.songxing.shop.Head;
import com.example.songxing.shop.R;
import com.example.songxing.shop.adapter.myOrderListAdapter;
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
 * Created by songxing on 2019/5/29.
 */

public class myOrderList extends Activity {
    private ArrayList<myOrderListBean> myOrderListBeans=new ArrayList<>();
    private String path="";
    private ListView listView;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorderlist);
        listView=findViewById(R.id.myOrderListListV);
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据加载中。。。");
        dialog.setCancelable(false);

        try{
            HttpUtils httpUtils=new HttpUtils();
            RequestParams params=new RequestParams();
            path="http://songxingxing.natapp1.cc/sell/buyer/order/list?openid=11111111&page=0&size=30";
            params.addBodyParameter("isok", Head.isOk());
           // params.addBodyParameter("openid","qwerrtyuio123asdfccc");
           // params.addBodyParameter("page","0");
           // params.addBodyParameter("size","111");
            dialog.show();
            httpUtils.send(HttpRequest.HttpMethod.GET, path, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    Toast.makeText(myOrderList.this,"数据加载成功",Toast.LENGTH_SHORT).show();
                    try{
                        dialog.dismiss();
                        System.out.println("responseInfo.result==="+responseInfo.result);
                        JSONObject jsonObject=new JSONObject(responseInfo.result);
                        String json_list=jsonObject.optString("data","[]");
                        JSONArray jsonArray=new JSONArray(json_list);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            myOrderListBean bean=new myOrderListBean();
                             String oderId=jsonObject1.optString("oderId","");
                             String buyerNme=jsonObject1.optString("buyerNme","");
                             String buyerPhone=jsonObject1.optString("buyerPhone","");
                             String buyerAddress=jsonObject1.optString("buyerAddress","");
                             String oderAmount=jsonObject1.optString("oderAmount","");
                             String oderStatus=jsonObject1.optString("oderStatus","");
                             String payStatus=jsonObject1.optString("payStatus","");
                             String createTime=jsonObject1.optString("createTime","");
                             bean.setPictureUrl("http://img4.imgtn.bdimg.com/it/u=3565349799,3969415493&fm=26&gp=0.jpg");
                            bean.setOderId(oderId);
                            bean.setBuyerNme(buyerNme);
                            bean.setBuyerAddress(buyerAddress);
                            bean.setBuyerPhone(buyerPhone);
                            bean.setOderAmount(oderAmount);
                            if(oderStatus.equals("0")==true)
                            {
                                bean.setOderStatus("未完结");
                            }
                            else if(oderStatus.equals("1")==true)
                            {
                                bean.setOderStatus("已完结");
                            }
                            else if(oderStatus.equals("2")==true)
                            {
                                bean.setOderStatus("已取消");
                            }

                            if(payStatus.equals("0")==true)
                            {
                                bean.setPayStatus("待支付");
                            }
                            else if(payStatus.equals("1")==true)
                            {
                                bean.setPayStatus("已支付");
                            }
                            bean.setCreateTime(createTime);
                            myOrderListBeans.add(bean);
                        }
                        myOrderListAdapter myOrderListAdapter=new myOrderListAdapter(myOrderListBeans,myOrderList.this);
                        listView.setAdapter(myOrderListAdapter);

                        myOrderListAdapter.setDetailListener(new myOrderListAdapter.onDetailListener() {
                            @Override
                            public void Detail(int i,String orderId) {
                                TextView textView=listView.getChildAt(i).findViewById(R.id.myOrderListItemButton);
                                String Paystatus=textView.getText().toString();
                                if(Paystatus.equals("已支付")){
                                    Intent intent=new Intent(myOrderList.this,OrderDetail.class);
                                    intent.putExtra("orderId", orderId);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent=new Intent(myOrderList.this,OrderDetailAwait.class);
                                    intent.putExtra("orderId", orderId);
                                    startActivity(intent);
                                }
                            }
                        });

                    }catch (Exception e1){
                    }
                }
                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(myOrderList.this,"数据加载失败",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onStart() {
                    super.onStart();
                }
            });

          //点击查看详情



        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
