package com.example.songxing.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.songxing.shop.activity.RegisterActivity;
import com.example.songxing.shop.activity.goodsActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.ActivityLogin);
        Button button1=findViewById(R.id.ActivityRegister);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=findViewById(R.id.yourID);
                EditText editText1=findViewById(R.id.PassWord);
                String buyerId=editText.getText().toString();
                String passWord=editText1.getText().toString();
                String path="http://songxingxing.natapp1.cc/sell/buyer/user/login";
                HttpUtils httpUtils=new HttpUtils();
                RequestParams params=new RequestParams();
                params.addBodyParameter("isok",Head.isOk());
                params.addBodyParameter("buyerId",buyerId);
                params.addBodyParameter("passWord",passWord);

                httpUtils.send(HttpRequest.HttpMethod.POST, path, params, new RequestCallBack<String>() {
                    String Resultmessage = "";
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(responseInfo.result);
                            Resultmessage =jsonObject.optString("msg","");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(Resultmessage.equals("登录成功")==true){
                            Intent intent = new Intent(MainActivity.this, goodsActivity.class);
                            startActivity(intent);//传过来的context相当于MainActivity
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,responseInfo.result,Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(MainActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                    }

                });

            }
});
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}