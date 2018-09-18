package com.missile.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Routers {
    public static String KEY_RAW_URL = "com.missile.router.KeyRawUrl";
    private static List<Mapping> mappings = new ArrayList<>();

    static void map(String format, Class<? extends Activity> activity, MethodInvoker method, ExtraTypes extraTypes) {
        mappings.add(new Mapping(format, activity, method, extraTypes));
    }

    public static boolean open(Context context, String url) {
        return open(context, Uri.parse(url));
    }

    public static boolean open(Context context, String url, RouterCallback callback) {
        return open(context, Uri.parse(url), callback);
    }

    public static boolean open(Context context, Uri uri) {
        return open(context, uri, getGlobalCallback(context));
    }

    public static boolean open(Context context, Uri uri, RouterCallback callback) {
        return open(context, uri, -1, callback);
    }

    public static boolean openForResult(Activity activity, String url, int requestCode) {
        return openForResult(activity, Uri.parse(url), requestCode);
    }

    public static boolean openForResult(Activity activity, String url, int requestCode, RouterCallback callback) {
        return openForResult(activity, Uri.parse(url), requestCode, callback);
    }

    public static boolean openForResult(Activity activity, Uri uri, int requestCode) {
        return openForResult(activity, uri, requestCode, getGlobalCallback(activity));
    }

    public static boolean openForResult(Activity activity, Uri uri, int requestCode, RouterCallback callback) {
        return open(activity, uri, requestCode, callback);
    }

    private static boolean open(Context context, Uri uri, int requestCode, RouterCallback callback) {
        boolean success = false;
        if (callback != null) {
            if (callback.beforeOpen(context, uri)) {
                return false;
            }
        }

        try {
            success = doOpen(context, uri, requestCode);
        } catch (Throwable e) {
            e.printStackTrace();
            if (callback != null) {
                callback.error(context, uri, e);
            }
        }

        if (callback != null) {
            if (success) {
                callback.afterOpen(context, uri);
            } else {
                callback.notFound(context, uri);
            }
        }

        return success;
    }

    private static boolean doOpen(Context context, Uri uri, int requestCode) {
        initIfNeed();
        Path path = Path.create(uri);

        for (Mapping mapping : mappings) {
            if (mapping.match(path)) {
                if (mapping.getActivity() == null) {
                    // Method
                    mapping.getMethod().invoke(context, mapping.parseExtras(uri));
                    return true;
                }

                Intent intent = new Intent(context, mapping.getActivity());
                intent.putExtras(mapping.parseExtras(uri));
                intent.putExtra(KEY_RAW_URL, uri.toString());
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                if (requestCode >= 0) {
                    if (context instanceof Activity) {
                        ((Activity) context).startActivityForResult(intent, requestCode);
                    } else {
                        throw new RuntimeException("can not startActivityForResult context" + context);
                    }
                } else {
                    context.startActivity(intent);
                }
                return true;
            }
        }

        return false;
    }

    private static RouterCallback getGlobalCallback(Context context) {
        if (context.getApplicationContext() instanceof RouterCallbackProvider) {
            return ((RouterCallbackProvider) context.getApplicationContext()).provideRouterCallback();
        }
        return null;
    }


    private static void initIfNeed() {
        if (!mappings.isEmpty()) {
            return;
        }
        RouterInit.init();
        sort();
    }

    private static void sort() {
        Collections.sort(mappings, new Comparator<Mapping>() {
            @Override
            public int compare(Mapping lhs, Mapping rhs) {
                return lhs.getFormat().compareTo(rhs.getFormat()) * -1;
            }
        });
    }
}
