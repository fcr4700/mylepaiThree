package com.qf.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.been.head.HeadData;
import com.qf.lepaiyizu.R;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2016/9/5.
 */
public class HeadDapter {
    List<HeadData> dataList;
    Context context;
    ImageView imageView;
    TextView textView;
    Bitmap bitmap;
    String title;
    String url;
    String imagUrl;
    LruUtils lruUtils;
    String picString;
    File file;
    File[] files;
    View view;
    Bundle bundle;
    Handler handler = new Handler() {
    };
    ExecutorService executor = Executors.newFixedThreadPool(10);

    public HeadDapter(List<HeadData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public void setView(final String imagUrl, String title) {
        view = LayoutInflater.from(context).inflate(R.layout.viewpager_head_image, null);
        imageView = (ImageView) view.findViewById(R.id.headImage);
        textView = (TextView) view.findViewById(R.id.headText);
        Log.d("refer","title======"+title);
        textView.setText(title);
        lruUtils = new LruUtils();
        lruUtils.initLru();
        file = new File("data/data/com.qf.lepaiyizu/cache/image");
        if (file.exists()) {
            files = file.listFiles();
        }
        if (imagUrl != null && lruUtils.getImage(imagUrl) != null) {
            imageView.setImageBitmap(lruUtils.getImage(imagUrl));
        } else if (files != null && DownloadUtils.fileList(files, picString)) {
            Bitmap bitmap = SecCacheImageRead.readBitmap(context, picString);
            imageView.setImageBitmap(bitmap);
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Uri uri = Uri.parse(imagUrl);
                    picString = uri.getLastPathSegment();
                    bitmap = DownloadUtils.getImage(imagUrl);
                    LruUtils.saveImage(imagUrl, bitmap);
                    SecCacheImageSave.saveBitmap(picString, bitmap);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            });

        }
    }
}
