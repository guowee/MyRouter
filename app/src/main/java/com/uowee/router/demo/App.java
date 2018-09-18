package com.uowee.router.demo;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.missile.router.RouterCallback;
import com.missile.router.RouterCallbackProvider;
import com.missile.router.SimpleRouterCallback;

public class App extends Application implements RouterCallbackProvider {
    @Override
    public RouterCallback provideRouterCallback() {
        return new SimpleRouterCallback(){
            @Override
            public boolean beforeOpen(Context context, Uri uri) {
                return super.beforeOpen(context, uri);
            }

            @Override
            public void notFound(Context context, Uri uri) {
                super.notFound(context, uri);
            }

            @Override
            public void error(Context context, Uri uri, Throwable e) {
                super.error(context, uri, e);
            }
        };
    }
}
