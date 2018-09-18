package com.missile.router;

import android.net.Uri;

public class Path {
    private final String value;
    private Path next;

    private Path(String value) {
        this.value = value;
    }

    //循环判断链表下面是否所有的Path的value都相等（这里要排除掉带：的参数），如果相等那就说明匹配到了，
    public static boolean match(Path format, Path link) {
        if (format == null || link == null) {
            return false;
        }

        if (format.length() != link.length()) {
            return false;
        }

        Path x = format;
        Path y = link;
        while (x != null) {
            if (!x.match(y)) {
                return false;
            }
            x = x.next;
            y = y.next;
        }
        return true;
    }


    public static Path create(Uri uri) {
        Path path = new Path(uri.getScheme().concat("://"));
        String urlPath = uri.getPath();
        if (urlPath == null) {
            urlPath = "";
        }
        if (urlPath.endsWith("/")) {
            urlPath = urlPath.substring(0, urlPath.length() - 1);
        }
        parse(path, uri.getHost() + urlPath);
        return path;
    }

    private static void parse(Path schema, String s) {
        String[] components = s.split("/");
        Path curPath = schema; //对象与对象引用
        for (String component : components) {
            Path temp = new Path(component);
            curPath.next = temp;
            curPath = temp;
        }

    }

    public int length() {
        Path path = this;
        int len = 1;
        while (path.next != null) {
            len++;
            path = path.next;
        }
        return len;
    }


    public Path next() {
        return next;
    }

    private boolean match(Path path) {
        return isArgument() || value.equals(path.value);
    }

    public boolean isArgument() {
        return value.startsWith(":");
    }


    public String argument() {
        return value.substring(1);
    }

    public String value() {
        return value;
    }

    public boolean isHttp() {
        String low = value.toLowerCase();
        return low.startsWith("http://") || low.startsWith("https://");
    }
}
