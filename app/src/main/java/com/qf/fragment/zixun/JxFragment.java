package com.qf.fragment.zixun;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.adapter.FragmentAdapter;
import com.qf.adapter.ZxJxAdapter;
import com.qf.been.zxjx.GridData;
import com.qf.been.zxjx.ListData;
import com.qf.lepaiyizu.R;
import com.qf.util.HandlerJson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lenovo on 2016/9/3.
 */
public class JxFragment extends Fragment{
    PullToRefreshListView pullToListView;
    List<ListData> dataListF=new ArrayList<>();
    List<GridData> dataGridF=new ArrayList<>();
    List<ListData> dataList=new ArrayList<>();
    List<GridData> dataGrid=new ArrayList<>();
    List<List> lists=new ArrayList<>();
    List<Fragment> fragmentList;
    ZxJxAdapter adapter;
    ViewPager viewPager;
    FragmentAdapter fragmentAdapter;
    ImageView imageView1,imageView2,imageView3;
    View headView;
    private int i=1;
    private  String URl_STRING="http://api.fengniao.com/app_ipad/news_jingxuan.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&page=";
    public static JxFragment newInstance(String name){
        JxFragment f=new JxFragment();
        Bundle bundle=new Bundle();
        bundle.putString("name",name);
        f.setArguments(bundle);
        return f;
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            lists= (List<List>) msg.obj;

            dataList=lists.get(0);
            for (ListData ll:dataList
                    ) {
                 dataListF.add(ll);
            }
            dataGrid=lists.get(1);
            for (GridData gg:dataGrid
                    ) {
                dataGridF.add(gg);
            }
            Log.d("refer","========"+dataGridF.get(0).toString());
            Log.d("refer","========"+dataGridF.get(1).toString());
            Log.d("refer","========"+dataListF.get(0).toString());
            Log.d("refer","========"+dataListF.get(1).toString());
            Log.d("refer","========"+dataListF.get(2).toString());
            Log.d("refer","========"+dataListF.get(3).toString());
            adapter=new ZxJxAdapter(dataListF,dataGridF,getActivity());
            pullToListView.setAdapter(adapter);
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zixun_jx,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToListView= (PullToRefreshListView) view.findViewById(R.id.fragmentZjx);
        pullToListView.setMode(PullToRefreshBase.Mode.BOTH);

        //initData();
       // initView();
        lists=new ArrayList<>();
        String url=URl_STRING+"1";
        if (getArguments()!=null && getArguments().getString("name").equals("JX")) {
            HandlerJson.getJson(url, handler);
        }
        pullToListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullToListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                i+=1;
                String url2=URl_STRING+i;
                HandlerJson.getJson(url2,handler);
                adapter.notifyDataSetChanged();;
                pullToListView.onRefreshComplete();
            }
        });
    }

//    private void initData() {
//        fragmentList=new ArrayList<>();
//        fragmentList.add(new HeadFragment().newInstance("one"));
//        fragmentList.add(new HeadFragment().newInstance("two"));
//        fragmentList.add(new HeadFragment().newInstance("three"));
//    }

//    private void initView() {
//       headView=LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_head,null);
//        viewPager= (ViewPager)  headView.findViewById(R.id.headImagePager);
//        imageView1= (ImageView)  headView.findViewById(R.id.head1);
//        imageView2= (ImageView)  headView.findViewById(R.id.head2);
//        imageView3= (ImageView)  headView.findViewById(R.id.head3);
//        //pullToListView.getRefreshableView().addHeaderView(headView);
//        fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList);
//        viewPager.setAdapter(fragmentAdapter);
//
//    }
}
