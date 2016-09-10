package com.qf.fragment.ts;

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
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.qf.adapter.FragmentAdapter;
import com.qf.adapter.TsAdapter;
import com.qf.adapter.ZxQcAdapter;
import com.qf.been.ts.FtsData;
import com.qf.been.ts.Tsdata;
import com.qf.been.zxqc.QcFgridData;
import com.qf.been.zxqc.QcGridData;
import com.qf.lepaiyizu.R;
import com.qf.util.FHeadlerJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/5.
 */
public class TsFragment extends Fragment {
    PullToRefreshGridView pullToGridView;
    List<Tsdata> dataGrid = new ArrayList<>();
    TsAdapter tsAdapter;
    String jsonString;
    FtsData list;
    private int i = 1;
    String URl_STRING;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            jsonString = (String) msg.obj;
            Gson gson = new Gson();
            Log.d("refer","图上===="+jsonString);
            list = gson.fromJson(jsonString, FtsData.class);
            Log.d("refer","=======111"+list.getList().size());
            for (Tsdata gg:list.getList()
                 ) {
                dataGrid.add(gg);
            }
            tsAdapter = new TsAdapter(getActivity(), dataGrid);
            pullToGridView.setAdapter(tsAdapter);
        }
    };

    public static TsFragment newInstance(String name) {
        TsFragment f = new TsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
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
        return inflater.inflate(R.layout.fragment_zixun_ano, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Tsdata> dataGrid = new ArrayList<>();
        pullToGridView = (PullToRefreshGridView) view.findViewById(R.id.gridId);
        pullToGridView.setMode(PullToRefreshBase.Mode.BOTH);
        if (getArguments().getString("name").equals("RX")){
             URl_STRING="http://api.fengniao.com/app_ipad/pic_bbs_list_v2.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&fid=101&page=";
            String url = URl_STRING + "1";
            FHeadlerJson.getJson(url, handler);
        }
            else if (getArguments().getString("name").equals("FG")) {
            URl_STRING = "http://http://api.fengniao.com/app_ipad/pic_bbs_list_v2.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&fid=125&page=";
            String url = URl_STRING + "1";
            FHeadlerJson.getJson(url, handler);
        } else if (getArguments().getString("name").equals("ST")) {
            URl_STRING = "http://api.fengniao.com/app_ipad/news_list.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&cid=190&page=";
            String url = URl_STRING + "1";
            FHeadlerJson.getJson(url, handler);
        } else if (getArguments().getString("name").equals("SM")) {
            URl_STRING = "http://api.fengniao.com//app_ipad/pic_bbs_list_v2.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&fid=24&page=";
            String url = URl_STRING + "1";
            FHeadlerJson.getJson(url, handler);
        }
        pullToGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                i += 1;
                String url2 = URl_STRING + i;
                FHeadlerJson.getJson(url2, handler);
                tsAdapter.notifyDataSetChanged();
                pullToGridView.onRefreshComplete();
            }
        });
    }
}
