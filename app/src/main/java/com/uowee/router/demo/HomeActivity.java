package com.uowee.router.demo;

import android.content.Intent;

import com.missile.router.annotation.Router;

@Router(value = "home")
public class HomeActivity extends DumpExtrasActivity {
    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("msg", "goodbye");
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
