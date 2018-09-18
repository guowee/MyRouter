package com.uowee.router.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Set;


public abstract class DumpExtrasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Set<String> keys = extras.keySet();

            TextView textView = new TextView(this);
            int padding = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
            textView.setPadding(padding, padding, padding, padding);
            textView.setText(getClass().getSimpleName());
            textView.append("\n\n");

            for (String key : keys) {
                textView.append(key + " --> ");
                Object v = extras.get(key);
                if (v != null) {
                    textView.append(v + " --> " + v.getClass().getSimpleName());
                } else {
                    textView.append("null");
                }
                textView.append("\n\n");
            }

            setContentView(textView);
        }
    }
}

