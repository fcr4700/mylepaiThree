package com.qf.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qf.adapter.FragmentAdapter;
import com.qf.fragment.zixun.JxFragment;
import com.qf.fragment.zixun.QcFragment;
import com.qf.lepaiyizu.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/2.
 */
public class ZiXunFragment extends Fragment{
    FragmentAdapter adapter;
    List<Fragment> fragmentList;
    ViewPager viewPager;
    RadioGroup radioGroup;
    TextView textView;
    public static ZiXunFragment newInstance( String name){
        ZiXunFragment f=new ZiXunFragment();
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
        return inflater.inflate(R.layout.fragment_zixun,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerId);
        adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        textView= (TextView) view.findViewById(R.id.tiaoId);
        int widPx=getResources().getDisplayMetrics().widthPixels;
        textView.setWidth(widPx/4);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.jingxuan:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.qicai:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.yingxiang:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.xueyuan:
                        viewPager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams params=
                        (LinearLayout.LayoutParams) textView.getLayoutParams();
                params.leftMargin=(int)((position+positionOffset)*textView.getWidth());
                textView.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                RadioButton button;
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    button = (RadioButton) radioGroup.getChildAt(i);
                    if (i == position) {
                        button.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        button.setTextColor(Color.parseColor("#dedede"));
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }
    private void initData() {
        fragmentList=new ArrayList<>();
        fragmentList.add(new JxFragment().newInstance("JX"));
        fragmentList.add(new QcFragment().newInstance("QC"));
        fragmentList.add(new QcFragment().newInstance("YX"));
        fragmentList.add(new QcFragment().newInstance("XY"));
    }

}
