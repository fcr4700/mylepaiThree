package com.qf.lepaiyizu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.qf.adapter.FragmentAdapter;
import com.qf.fragment.TuShangFragment;
import com.qf.fragment.ZiXunFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    FragmentManager manager;
    FragmentTransaction transaction;
    RadioGroup radioGroup;
    Fragment fragment1,fragment2,fragment3,fragment4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        radioGroup.setOnCheckedChangeListener(this);
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        transaction.add(R.id.fragmentId,fragment1);
        transaction.commit();
    }

    private void initView() {
       radioGroup= (RadioGroup) findViewById(R.id.mainRadioTabs);
    }

    private void initData() {
       fragment1= new ZiXunFragment();
       fragment2= new TuShangFragment();
    }
    @Override
    protected void onDestroy() {
        File file=new File(getCacheDir() + File.separator + "image");
        File[] files=file.listFiles();
        for (File ff:files
                ) {
            ff.delete();
        }
        file.delete();
        super.onDestroy();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.mainRadio_news:
                Fragment fragmentZx=fragment1;
                transaction=manager.beginTransaction();
                transaction.replace(R.id.fragmentId,fragmentZx);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.mainRadio_pic:
                Fragment fragmentTs=fragment2;
                transaction=manager.beginTransaction();
                transaction.replace(R.id.fragmentId,fragmentTs);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.mainRadio_bbs:

                break;
            case R.id.mainRadio_set:

                break;
        }
    }
}
