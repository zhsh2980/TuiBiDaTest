package bro.tuibida.com.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import bro.tuibida.com.R;
import bro.tuibida.com.adapter.RecycleViewAdapter;
import bro.tuibida.com.utils.BlurUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorActivity extends AppCompatActivity {

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbarlayout)
    AppBarLayout mAppbarlayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<String> dataList = new ArrayList<String>();
    private RecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        ButterKnife.bind(this);
        initData(1);
        initView();
    }

    private void initData(int page) {

        for (int i = 0; i < 50; i++) {
            dataList.add("第" + page + "页第" + (i + 1) + "条数据");
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolabar替换actionBar,需在清单文件中设置style = noActionBar
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //设置布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        recycleViewAdapter = new RecycleViewAdapter(this, dataList);
        recyclerView.setAdapter(recycleViewAdapter);
        Glide.with(this).load(R.drawable.zhiwu).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                Bitmap doBlurBitmap = BlurUtil.doBlur(bitmap, 15, 10);
                mCollapsingToolbar.setContentScrim(new BitmapDrawable(doBlurBitmap));
            }
        });
//        ImageLoaderCompact.getInstance().asyncFetchBitmapByUrl(postImage.url, new OnFetchBitmapListener() {
//            @Override
//            public void onFetchBitmapSuccess(String s, Bitmap bitmap) {
//
//                Bitmap doBlurBitmap = BlurUtil.doBlur(bitmap, 15, 10);
//                blurBitmapList[position] = doBlurBitmap;
//
//                if (position == 0 && collapsing_toolbar != null) {
//                    collapsing_toolbar.setContentScrim(new BitmapDrawable(doBlurBitmap));
//                }
//            }
//
//            @Override
//            public void onFetchBitmapFailure(String s) {
//
//            }
//        });
    }
}
