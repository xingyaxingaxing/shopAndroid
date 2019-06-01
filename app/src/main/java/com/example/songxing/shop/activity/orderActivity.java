package com.example.songxing.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.songxing.shop.Head;
import com.example.songxing.shop.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songxing on 2019/5/20.
 */

public class orderActivity extends Activity {
    private String jsonstr = "";
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        final Intent intent=getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        String AllPrice=bundle.getString("AllPrice");//getString()返回指定key的值
        jsonstr=bundle.getString("jsonstr");//getString()返回指定key的值
        //String countText=bundle.getString("countText");
        TextView textview=findViewById(R.id.TobePaid);
        textview.setText(AllPrice);
        TextView textViewPaid=findViewById(R.id.textViewPaid);
        TextView textViewCanCle=findViewById(R.id.CanClePaid);
        //LayoutInflater factory = LayoutInflater.from(orderActivity.this);
        //View layout = factory.inflate(R.layout.goodsitem, null);
        //TextView goodListText = (TextView) layout.findViewById(R.id.goodListViewText);
        //goodListText.setText(countText);

        //支付订单
        textViewPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=findViewById(R.id.yourName);
                EditText editText1=findViewById(R.id.phone);
                EditText editText2=findViewById(R.id.aDress);
                String user=editText.getText().toString();
                String phone=editText1.getText().toString();
                String aDress=editText2.getText().toString();

                    String path="http://songxingxing.natapp1.cc/sell/buyer/order/create";
                    HttpUtils httpUtils=new HttpUtils();//处理网络连接
                    final RequestParams params=new RequestParams();//传参数到服务器
                    params.addBodyParameter("isok", Head.isOk());// 只包含字符串参数时默认使用BodyParamsEntity

                    params.addBodyParameter("name",user);
                    params.addBodyParameter("phone",phone);
                    params.addBodyParameter("address",aDress);
                    params.addBodyParameter("openid","11111111");
                    params.addBodyParameter("items",jsonstr);
                    params.addBodyParameter("payStatus","1");
                    //new RequestCallBack<String>请求的回调函数
                    System.out.println( "responseInfo===jsonstr=="+jsonstr);
                    httpUtils.send(HttpRequest.HttpMethod.POST, path,params, new RequestCallBack<String>() {
                        String message="";
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {

                            System.out.println( "responseInfo==="+responseInfo.result);
                            try {
                                JSONObject jsonObject=new JSONObject(responseInfo.result);
                                message=jsonObject.optString("msg","");
                                Toast.makeText(orderActivity.this,message,Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent1=new Intent(orderActivity.this,goodsActivity.class);
                            startActivity(intent1);
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(orderActivity.this,s,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        });

        //取消支付
        textViewCanCle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=findViewById(R.id.yourName);
                EditText editText1=findViewById(R.id.phone);
                EditText editText2=findViewById(R.id.aDress);
                String user=editText.getText().toString();
                String phone=editText1.getText().toString();
                String aDress=editText2.getText().toString();

                String path="http://songxingxing.natapp1.cc/sell/buyer/order/create";
                HttpUtils httpUtils=new HttpUtils();//处理网络连接
                final RequestParams params=new RequestParams();//传参数到服务器
                params.addBodyParameter("isok", Head.isOk());// 只包含字符串参数时默认使用BodyParamsEntity

                params.addBodyParameter("name",user);
                params.addBodyParameter("phone",phone);
                params.addBodyParameter("address",aDress);
                params.addBodyParameter("openid","11111111");
                params.addBodyParameter("items",jsonstr);
                params.addBodyParameter("payStatus","0");
                //new RequestCallBack<String>请求的回调函数
                System.out.println( "responseInfo===jsonstr=="+jsonstr);
                httpUtils.send(HttpRequest.HttpMethod.POST, path,params, new RequestCallBack<String>() {
                    String message="";
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        System.out.println( "responseInfo==="+responseInfo.result);
                        try {
                            JSONObject jsonObject=new JSONObject(responseInfo.result);
                            message=jsonObject.optString("msg","");
                            Toast.makeText(orderActivity.this,message,Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent1=new Intent(orderActivity.this,goodsActivity.class);
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(orderActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}