package com.qf.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by lenovo on 2016/8/26.
 */
public class LruUtils {
    private static LruCache<String ,Bitmap> lruCache;
    public static LruCache initLru(){
        int maxSize=4*1034*1024;
        return  lruCache=new LruCache<>(maxSize);
    }
    public static Bitmap getImage(String url){
        if (lruCache!=null){
            return lruCache.get(url);
        }else{
            return null;
        }
    }
    public static void saveImage(String url,Bitmap bitmap){
        if (getImage(url)==null){
            Log.d("refer","一级缓存");
            lruCache.put(url,bitmap);
        }
    }
}
