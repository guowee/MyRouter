package com.missile.router;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

public class RouterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouterCallback callback = getRouterCallback();
        Uri uri = getIntent().getData();
        if (uri != null) {
            Routers.open(this, uri, callback);
        }
        finish();
    }

    private RouterCallback getRouterCallback() {
        if (getApplication() instanceof RouterCallbackProvider) {
            return ((RouterCallbackProvider) getApplication()).provideRouterCallback();
        }
        return null;
    }
}
