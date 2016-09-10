package com.qf.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qf.been.zxjx.GridData;
import com.qf.been.zxjx.ListData;
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
 * Created by lenovo on 2016/9/3.
 */
public class ZxJxAdapter extends BaseAdapter{
    List<ListData> dataList;
    List<GridData> dataGrid;
    Context context;
    LruUtils lruUtils;
    File file ;
    File[] files;
    Handler handler=new Handler(){};
    ExecutorService executor= Executors.newFixedThreadPool(10);
    public ZxJxAdapter(List<ListData> dataList, List<GridData> dataGrid, Context context) {
        this.dataList = dataList;
        this.context = context;
        this.dataGrid=dataGrid;
        lruUtils=new LruUtils();
        lruUtils.initLru();
    }

    @Override
    public int getItemViewType(int position) {
        if ((position)%5==0){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return dataList.size()+(dataGrid.size()/2);
    }

    @Override
    public Object getItem(int i) {
        if ((i)%5==0){
            return dataGrid.get(i);
        }else{
            return dataList.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        final ViewHolder1 viewHolder1;
        if (getItemViewType(i)==1) {
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.tem_listview, null);
                viewHolder.textViewb = (TextView) view.findViewById(R.id.listTextB);
                viewHolder.textViews = (TextView) view.findViewById(R.id.listTextS);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.listImage);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final String imagUrl = dataList.get(i-(i/5+1)).getPic_url();
            Uri uri = Uri.parse(imagUrl);
            final String picString = uri.getLastPathSegment();//获取最后一节
            viewHolder.imageView.setTag(picString);
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
            viewHolder.textViewb.setText(dataList.get(i-(i/5+1)).getTitle());
            viewHolder.textViews.setText(dataList.get(i-(i/5+1)).getDate());
            file = new File("data/data/com.qf.lepaiyizu/cache/image");
            if (file.exists()) {
                files = file.listFiles();
            }
            //一级缓存
            if (lruUtils.getImage(imagUrl) != null && viewHolder.imageView.getTag().equals(imagUrl)) {
                viewHolder.imageView.setImageBitmap(lruUtils.getImage(imagUrl));
                //二级缓存
            } else if (files != null && DownloadUtils.fileList(files, picString)) {

                Bitmap bitmap = SecCacheImageRead.readBitmap(context, picString);
                viewHolder.imageView.setImageBitmap(bitmap);
            } else {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = DownloadUtils.getImage(imagUrl);
                        LruUtils.saveImage(imagUrl, bitmap);
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
        }else if (getItemViewType(i)==0) {
            if (view == null) {
                viewHolder1 = new ViewHolder1();
                view = LayoutInflater.from(context).inflate(R.layout.tem_listview2, null);
                viewHolder1.textViewLeft = (TextView) view.findViewById(R.id.listTextleft);
                viewHolder1.textViewRight = (TextView) view.findViewById(R.id.listTextright);
                viewHolder1.imageViewLeft = (ImageView) view.findViewById(R.id.listImageleft);
                viewHolder1.imageViewRight= (ImageView) view.findViewById(R.id.listImageright);
                view.setTag(viewHolder1);
            } else {
                viewHolder1 = (ViewHolder1) view.getTag();
            }
            Log.d("refer","dataGrid.size======"+dataGrid.size());
            final String imagUrl = dataGrid.get((i/5)*2).getPic_url();
            final String imagUrl2=dataGrid.get((i/5)*2+1).getPic_url();
            Uri uri = Uri.parse(imagUrl);
            Uri uri2=Uri.parse(imagUrl2);
            final String picString = uri.getLastPathSegment();//获取最后一节
            final String picString2=uri2.getLastPathSegment();
            viewHolder1.imageViewLeft.setTag(picString);
            viewHolder1.imageViewRight.setTag(picString2);
            viewHolder1.imageViewLeft.setImageResource(R.mipmap.ic_launcher);
            viewHolder1.imageViewRight.setImageResource(R.mipmap.ic_launcher);
            viewHolder1.textViewLeft.setText(dataGrid.get((i/5)*2).getTitle());
            viewHolder1.textViewRight.setText(dataGrid.get((i/5)*2+1).getTitle());
            file = new File("data/data/com.qf.lepaiyizu/cache/image");
            if (file.exists()) {
                files = file.listFiles();
            }
            //一级缓存
            if (lruUtils.getImage(imagUrl) != null && viewHolder1.imageViewLeft.getTag().equals(imagUrl)
                 && lruUtils.getImage(imagUrl2) != null  && viewHolder1.imageViewRight.getTag().equals(imagUrl2)) {
                viewHolder1.imageViewLeft.setImageBitmap(lruUtils.getImage(imagUrl));
                viewHolder1.imageViewRight.setImageBitmap(lruUtils.getImage(imagUrl2));
                //二级缓存
            } else if (files != null && DownloadUtils.fileList(files, picString)
                    && DownloadUtils.fileList(files,picString2)) {

                Bitmap bitmap = SecCacheImageRead.readBitmap(context, picString);
                Bitmap bitmap1=SecCacheImageRead.readBitmap(context,picString2);
                viewHolder1.imageViewLeft.setImageBitmap(bitmap);
                viewHolder1.imageViewRight.setImageBitmap(bitmap1);
            } else {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap= DownloadUtils.getImage(imagUrl);
                        final Bitmap bitmap2  = DownloadUtils.getImage(imagUrl2);
                        LruUtils.saveImage(imagUrl, bitmap);
                        LruUtils.saveImage(imagUrl2,bitmap2);
                        SecCacheImageSave.saveBitmap(picString, bitmap);
                        SecCacheImageSave.saveBitmap(picString2, bitmap2);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (viewHolder1.imageViewLeft.getTag() != null && viewHolder1.imageViewLeft.getTag().equals(picString)
                                    && viewHolder1.imageViewRight.getTag() != null && viewHolder1.imageViewRight.getTag().equals(picString2)    ) {
                                    viewHolder1.imageViewLeft.setImageBitmap(bitmap);
                                    viewHolder1.imageViewRight.setImageBitmap(bitmap2);
                                }
                            }
                        });
                    }
                });

            }
        }
            return view;
    }
    class  ViewHolder{
        ImageView imageView;
        TextView textViewb,textViews;
    }
    class ViewHolder1{
        ImageView imageViewLeft,imageViewRight;
        TextView textViewLeft,textViewRight;
    }
}
