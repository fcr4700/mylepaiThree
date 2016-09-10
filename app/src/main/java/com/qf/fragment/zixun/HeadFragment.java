package com.qf.fragment.zixun;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qf.been.head.HeadData;
import com.qf.lepaiyizu.R;
import com.qf.util.DownloadUtils;
import com.qf.util.HandlerJson;
import com.qf.util.HeadDapter;
import com.qf.util.HeadhandlerJson;
import com.qf.util.LruUtils;
import com.qf.util.SecCacheImageRead;
import com.qf.util.SecCacheImageSave;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2016/9/5.
 */
public class HeadFragment extends Fragment{
    ImageView imageView;
    TextView textView;
    Bitmap bitmap;
    String title;
    String url;
    String imagUrl;
    LruUtils lruUtils;
    String picString;
    File file ;
    File[] files;
    HeadDapter headDapter;
    List<HeadData> dataList=new ArrayList<>();
    Bundle bundle;
//    ExecutorService executor= Executors.newFixedThreadPool(10);
    private String URL_String="http://api.fengniao.com/app_ipad/focus_pic.php?mid=19928?appImei=000000000000000&osType=Android&osVersion=4.1.1 HTTP/1.1";
    public static HeadFragment newInstance(String name){
        HeadFragment f=new HeadFragment();
        Bundle bundle=new Bundle();
        bundle.putString("name",name);
        f.setArguments(bundle);
        return f;
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            dataList= (List<HeadData>) msg.obj;
            if (bundle!=null && bundle.getString("name").equals("one")){
                imagUrl=dataList.get(0).getPic_src();
                title=dataList.get(0).getTitle();
                url=dataList.get(0).getUrl();
                Log.d("refer","handler======"+imagUrl+title);

            }else if(bundle!=null && bundle.getString("name").equals("two")){
                imagUrl=dataList.get(1).getPic_src();
                title=dataList.get(1).getTitle();
                url=dataList.get(1).getUrl();
            }else if(bundle!=null && bundle.getString("name").equals("three")){
                imagUrl=dataList.get(2).getPic_src();
                title=dataList.get(2).getTitle();
                url=dataList.get(2).getUrl();
            }
            headDapter=new HeadDapter(dataList,getActivity());
            headDapter.setView(imagUrl,title);
//            if (imagUrl!=null && lruUtils.getImage(imagUrl) != null){
//                imageView.setImageBitmap(lruUtils.getImage(imagUrl));
//            }else if (files != null && DownloadUtils.fileList(files, picString)){
//                Bitmap bitmap = SecCacheImageRead.readBitmap(getActivity(), picString);
//                imageView.setImageBitmap(bitmap);
//            }else {
//                executor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        Uri uri=Uri.parse(imagUrl);
//                        picString=uri.getLastPathSegment();
//                        bitmap= DownloadUtils.getImage(imagUrl);
//                        LruUtils.saveImage(imagUrl, bitmap);
//                        SecCacheImageSave.saveBitmap(picString, bitmap);
//                    }
//                });
//                textView.setText(title);
//                imageView.setImageBitmap(bitmap);
//            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_head_image,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//         imageView= (ImageView) view.findViewById(R.id.headImage);
//         textView= (TextView) view.findViewById(R.id.headText);
        bundle=getArguments();
        HeadhandlerJson.getJson(URL_String,handler);
    }
}
