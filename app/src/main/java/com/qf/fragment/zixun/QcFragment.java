package com.qf.fragment.zixun;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.qf.adapter.FragmentAdapter;
import com.qf.adapter.ZxQcAdapter;
import com.qf.been.zxqc.QcFgridData;
import com.qf.been.zxqc.QcGridData;
import com.qf.lepaiyizu.R;
import com.qf.util.FHeadlerJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/3.
 */
public class QcFragment extends Fragment{
    PullToRefreshGridView pullToGridView;
    List<Fragment> fragmentList;
    List<QcGridData> dataGrid=new ArrayList<>();
    ZxQcAdapter Qcadapter;
    ViewPager viewPager;
    FragmentAdapter fragmentAdapter;
    ImageView imageView1,imageView2,imageView3;
    String jsonString;
    View headView;
    QcFgridData list;
    private int i=1;
    String URl_STRING;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
              jsonString= (String) msg.obj;
            Gson gson=new Gson();
            list=gson.fromJson(jsonString,QcFgridData.class);
            for (QcGridData gg:list.getList()
                 ) {
                dataGrid.add(gg);
            }
            Qcadapter=new ZxQcAdapter(getActivity(),dataGrid);
            pullToGridView.setAdapter(Qcadapter);
        }
    };
    public static QcFragment newInstance(String name){
        QcFragment f=new QcFragment();
        Bundle bundle=new Bundle();
        bundle.putString("name",name);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zixun_ano,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToGridView= (PullToRefreshGridView) view.findViewById(R.id.gridId);
        pullToGridView.setMode(PullToRefreshBase.Mode.BOTH);
        if (getArguments().getString("name").equals("QC")){
            URl_STRING="http://api.fengniao.com/app_ipad/news_list.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&cid=296&page=";
            String url=URl_STRING+"1";
            FHeadlerJson.getJson(url,handler);
        }else if(getArguments().getString("name").equals("YX")){
            URl_STRING="http://api.fengniao.com/app_ipad/news_list.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&cid=190&page=";
            String url=URl_STRING+"1";
            FHeadlerJson.getJson(url,handler);
        }else if(getArguments().getString("name").equals("XY")){
            URl_STRING="http://api.fengniao.com/app_ipad/news_list.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&cid=192&page=";
            String url=URl_STRING+"1";
            FHeadlerJson.getJson(url,handler);
        }
        initData();
        initView();

        pullToGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                i+=1;
                String url2=URl_STRING+i;
                FHeadlerJson.getJson(url2,handler);
                Qcadapter.notifyDataSetChanged();;
                pullToGridView.onRefreshComplete();
            }
        });
    }

    private void initData() {
        fragmentList=new ArrayList<>();
        fragmentList.add(new HeadFragment().newInstance("one"));
        fragmentList.add(new HeadFragment().newInstance("two"));
        fragmentList.add(new HeadFragment().newInstance("three"));
    }

    private void initView() {
        headView=LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_head,null);
        viewPager= (ViewPager)  headView.findViewById(R.id.headImagePager);
        imageView1= (ImageView)  headView.findViewById(R.id.head1);
        imageView2= (ImageView)  headView.findViewById(R.id.head2);
        imageView3= (ImageView)  headView.findViewById(R.id.head3);
        //pullToGridView.getRefreshableView().add
        fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(fragmentAdapter);

    }
}

