package com.qf.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qf.been.head.HeadData;
import com.qf.been.zxjx.GridData;
import com.qf.been.zxjx.ListData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2016/9/5.
 */
public class HeadhandlerJson {
    private static List<HeadData> dataList;
    public static ExecutorService executor
            = Executors.newFixedThreadPool(10);

    public static void getJson(final String url, final Handler handler) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dataList=new ArrayList<HeadData>();
                String JsonString = DownloadUtils.getJsonString(url);
                Gson gson = new Gson();
                dataList=gson.fromJson(JsonString,new TypeToken<List<HeadData>>(){}.getType());
                Message message=Message.obtain();
                Log.d("refer","头部视图====="+dataList.size());
                message.obj=dataList;
                handler.sendMessage(message);
            }
        });
    }
}