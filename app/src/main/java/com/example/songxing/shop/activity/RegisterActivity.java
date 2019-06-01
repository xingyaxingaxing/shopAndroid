package com.example.songxing.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.songxing.shop.Head;
import com.example.songxing.shop.MainActivity;
import com.example.songxing.shop.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by songxing on 2019/5/27.
 */

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button button=findViewById(R.id.registerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=findViewById(R.id.UserName);
                EditText editText1=findViewById(R.id.UserId);
                EditText editText2=findViewById(R.id.registerPassword);
                String UserName=editText.getText().toString();
                String UserId=editText1.getText().toString();
                String registerPassword=editText2.getText().toString();
                String path="http://songxingxing.natapp1.cc/sell/buyer/user/createSave";
                HttpUtils httpUtils=new HttpUtils();
                RequestParams params=new RequestParams();
                params.addBodyParameter("isok", Head.isOk());
                params.addBodyParameter("buyerName",UserName);
                params.addBodyParameter("buyerId",UserId);
                params.addBodyParameter("passWord",registerPassword);

                httpUtils.send(HttpRequest.HttpMethod.POST, path, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Toast.makeText(RegisterActivity.this,responseInfo.result,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(RegisterActivity.this,"注册失败，请重新注册",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
