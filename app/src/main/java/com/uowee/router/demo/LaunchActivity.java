package com.uowee.router.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.missile.router.Routers;


public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        for (int i = 0; i < container.getChildCount(); i++) {
            final View view = container.getChildAt(i);
            if (view instanceof AppCompatTextView) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Routers.openForResult(LaunchActivity.this, ((AppCompatTextView) view).getText().toString(), 1001);
                    }
                });
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1001) {
            String message = null;
            if (data == null) {
                message = "success";
            } else {
                message = data.getStringExtra("msg");
                message = TextUtils.isEmpty(message) ? "success" : message;
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}
