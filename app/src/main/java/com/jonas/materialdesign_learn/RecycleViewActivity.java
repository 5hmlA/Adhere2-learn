package com.jonas.materialdesign_learn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.jonas.materialdesign_learn.helper.SnackbarUtil;
import com.jonas.materialdesign_learn.helper.adapter.AwesomeRecvAdapter;
import com.jonas.materialdesign_learn.helper.adapter.DragSwipeCallback;
import com.jonas.materialdesign_learn.helper.adapter.RecyclerHolder;

import java.util.ArrayList;

/**
 * <p> int firstIndex = mLayoutManager.findFirstVisibleItemPosition();//返回当前屏幕第一个显示的item的postion
 * <p> int firstComIndex = mLayoutManager.findFirstCompletelyVisibleItemPosition();//返回当前屏幕第一个完全显示的item的postion
 * <p> int lastIndex = mLayoutManager.findLastVisibleItemPosition();//返回当前屏幕最后一个显示的item的position
 * <p> int lastComIndex = mLayoutManager.findLastCompletelyVisibleItemPosition();//返回当前屏幕最后一个完全显示的item的posion
 * <p> View view = mLayoutManager.findViewByPosition(int postion);//返回对应postion的itemView
 */
public class RecycleViewActivity extends AppCompatActivity {

    private RecyclerView mRcv;
    private AwesomeRecvAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Snackbar snackbar2 = Snackbar.make(toolbar, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null);

        SnackbarUtil.create(snackbar2).setBackground(Color.RED).setMessageViewColor(Color.BLUE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        wifiReceiver receiver = new wifiReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(receiver, intentFilter);


        mRcv = (RecyclerView) findViewById(R.id.rcv_md);
//        mRcv.addItemDecoration(new DividerItemDecoration(8));
        mRcv.setItemAnimator(new DefaultItemAnimator());
        mRcv.setLayoutManager(new LinearLayoutManager(this));
//        mRcv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRcv.setLayoutManager(new GridLayoutManager(this, 2));
        ArrayList<String> data = new ArrayList();
        for (int i = 0; i < 17; i++) {
            data.add("餐" + i);
        }

        //        AwesomeRecvAdapter adapter = new AwesomeRecvAdapter<String>(data, mRcv ) {
//            @Override
//            public void setItemLayouts(SparseArray itemLayoutIds) {
//                itemLayoutIds.append(TYPE_1, R.layout.item_recyc_simple_iv);
//                itemLayoutIds.append(TYPE_2, R.layout.item_recyc_simple_tv);
//            }
//            @Override
//            public int getCustomItemViewType(int position) {
//                return position % 3 == 0 ? TYPE_1 : TYPE_2;
//            }
//        mAdapter = new AwesomeRecvAdapter<String>(data, R.layout.item_recyc_simple_tv) {
        mAdapter = new AwesomeRecvAdapter<String>(data, mRcv, R.layout.item_recyc_simple_tv) {
//        AwesomeRecvAdapter adapter = new AwesomeRecvAdapter<String>(data, mRcv ) {

            private final static int TYPE_1 = 1;
            private final static int TYPE_2 = 0;

//            @Override
//            public void setItemLayouts(SparseArray itemLayoutIds) {
//                itemLayoutIds.append(TYPE_1, R.layout.item_recyc_simple_iv);
//                itemLayoutIds.append(TYPE_2, R.layout.item_recyc_simple_tv);
//            }

//            @Override
//            public int getCustomItemViewType(int position) {
//                return position % 3 == 0 ? TYPE_1 : TYPE_2;
//            }

            @Override
            public void convert(RecyclerHolder holder, final int position, String itemData) {
                holder.setText(R.id.item_recy_tv, itemData);
            }
        };
        mAdapter.setPagesize(5);
//        BaseRecvAdapter adapter = new BaseRecvAdapter<String>(data, R.layout.item_recyc_simple_iv) {
//
//            private final static int TYPE_1 = 1;
//            private final static int TYPE_2 = 2;
//
//            @Override
//            public int getItemViewType(int position) {
//                return position % 3 == 0 ? TYPE_1 : TYPE_2;
//            }
//
//            @Override
//            public void setItemLayouts(SparseArray itemLayoutIds) {
//                itemLayoutIds.append(TYPE_1, R.layout.item_recyc_simple_iv);
//                itemLayoutIds.append(TYPE_2, R.layout.item_recyc_simple_tv);
//            }
//
//            @Override
//            public void convert(RecyclerHolder holder, final int position, String itemData) {
//            }
//        };
        mRcv.setAdapter(mAdapter);
        DragSwipeCallback callback = new DragSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.startDrag();
        itemTouchHelper.attachToRecyclerView(mRcv);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar2.show();
//                -1-new SecureRandom().nextInt(4)
                mAdapter.addItem("妹纸来电话了。", 0);
            }
        });
    }

    public static class wifiReceiver extends BroadcastReceiver {
        NetworkInfo.State wifiState = null;
        NetworkInfo.State mobileState = null;
        public static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (ACTION.equals(intent.getAction())) {
                //获取手机的连接服务管理器，这里是连接管理器类
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

                if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
                    Toast.makeText(context, "手机网络连接成功！", Toast.LENGTH_SHORT).show();
                } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                    Toast.makeText(context, "无线网络连接成功！", Toast.LENGTH_SHORT).show();
                } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                    Toast.makeText(context, "手机没有任何网络...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
