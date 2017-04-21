package com.jonas.materialdesign_learn;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jonas.yun_library.helper.TimeHelper;


public class MainActivity extends ListActivity {

    private String items[] = new String[]{
            "FloatActivity",
            "ScrollingActivity",
            "RecycleViewActivity",
            "CoordinatorLayoutActivity"
    };

    private Class clazz[] = new Class[]{
            FloatActivity.class,
            ScrollingActivity.class,
            RecycleViewActivity.class,
            CoordinatorLayoutActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(TimeHelper.sec2fotmat(24*60*60-1));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, clazz[position]);
        startActivity(intent);
    }
}
