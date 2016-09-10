package com.qf.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.been.ts.Tsdata;
import com.qf.been.zxqc.QcGridData;
import com.qf.lepaiyizu.R;
import com.qf.util.DownloadUtils;
import com.qf.util.LruUtils;
import com.qf.util.SecCacheImageRead;
import com.qf.util.SecCacheImageSave;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2016/9/5.
 */
public class TsAdapter extends BaseAdapter{
    Context context;
    List<Tsdata> dataGrids;
    LruUtils lruUtils;
    File file ;
    File[] files;
    Intent intent;
    Handler handler=new Handler(){};
    ExecutorService executor= Executors.newFixedThreadPool(10);
    public TsAdapter(Context context, List<Tsdata> dataGrids) {
        this.context = context;
        this.dataGrids = dataGrids;
        lruUtils=new LruUtils();
        lruUtils.initLru();
    }

    @Override
    public int getCount() {
        return dataGrids.size();
    }

    @Override
    public Object getItem(int i) {
        return dataGrids.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.item_zx_gridview,null);
            viewHolder.imageView= (ImageView) view.findViewById(R.id.gridImage);
            viewHolder.textView= (TextView) view.findViewById(R.id.gridText);
            view.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        final String imageUrl=dataGrids.get(i).getPic_url();
        final String webUrl=dataGrids.get(i).getWeb_url();
        Uri uri = Uri.parse(imageUrl);
        final String picString = uri.getLastPathSegment();//获取最后一节
        Log.d("refer","picString====="+picString);
        viewHolder.imageView.setTag(picString);
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        viewHolder.textView.setText(dataGrids.get(i).getTitle());
        //设置监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.imageView.getTag()!=null && viewHolder.imageView.getTag().equals(picString)){
                    Uri uri1=Uri.parse(webUrl);
                    intent=new Intent(Intent.ACTION_VIEW,uri1);
                    context.startActivity(intent);
                }
            }
        });
        file = new File("data/data/com.qf.lepaiyizu/cache/image");
        if (file.exists()) {
            files = file.listFiles();
        }
        //一级缓存
        if (lruUtils.getImage(imageUrl) != null && viewHolder.imageView.getTag().equals(imageUrl)) {
            viewHolder.imageView.setImageBitmap(lruUtils.getImage(imageUrl));
            //二级缓存
        } else if (files != null && DownloadUtils.fileList(files, picString)) {

            Bitmap bitmap = SecCacheImageRead.readBitmap(context, picString);
            viewHolder.imageView.setImageBitmap(bitmap);
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = DownloadUtils.getImage(imageUrl);
                    LruUtils.saveImage(imageUrl, bitmap);
                    SecCacheImageSave.saveBitmap(picString, bitmap);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (viewHolder.imageView.getTag() != null && viewHolder.imageView.getTag().equals(picString)) {
                                viewHolder.imageView.setImageBitmap(bitmap);
                            }
                        }
                    });
                }
            });

        }
        return view;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
