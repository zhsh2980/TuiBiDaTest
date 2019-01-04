package bro.tuibida.com.multidrag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bro.tuibida.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiDragActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_del)
    TextView mTvDel;
    @BindView(R.id.tv_edit_done)
    TextView mTvEditDone;
    private List<String> mDatas;
    private ViewTypeAdapter mTypeAdapter;
    private boolean isEditOrDone = true;//默认是编辑

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_drag);
        ButterKnife.bind(this);
        initData();
        initRecy();
    }

    private void initRecy() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(12));
        mTypeAdapter = new ViewTypeAdapter(this);
        mRecyclerView.setAdapter(mTypeAdapter);
        mTypeAdapter.setData(mDatas);

        RecyItemTouchHelperCallback itemTouchHelperCallback = new RecyItemTouchHelperCallback(mTypeAdapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
//                if (viewHolder instanceof ContentHolder) {
//                    ContentHolder viewHolder1 = (ContentHolder) viewHolder;
//                    String tvString = viewHolder1.tv.getText().toString();
//                    Toast.makeText(MultiDragActivity.this, "逗逗~" + tvString, Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {
                Toast.makeText(MultiDragActivity.this, "长按了", Toast.LENGTH_SHORT).show();
                if (viewHolder instanceof ContentHolder) {
                    ContentHolder viewHolder1 = (ContentHolder) viewHolder;
                    //振动
//                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                    vib.vibrate(70);
                    //开始拖曳这个 item
                    itemTouchHelper.startDrag(viewHolder);
                }
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        mDatas.add("我是标题1");
        mDatas.add("我是当前任务");
        mDatas.add("我是标题2");
        for (int i = 0; i < 24; i++) {
            mDatas.add("我是正文" + i);
        }
    }


    @OnClick(R.id.tv_del)
    public void onViewClicked() {

    }

    @OnClick({R.id.tv_edit_done, R.id.tv_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit_done:
                if (isEditOrDone) {
                    mTvEditDone.setText("完成");
                    mTvDel.setVisibility(View.VISIBLE);

                } else {
                    mTvEditDone.setText("编辑");
                    mTvDel.setVisibility(View.INVISIBLE);
//                    mTvDel.setTextColor(getResources().getColor(R.color.jumei_red));

                }
                isEditOrDone = !isEditOrDone;
                break;
            case R.id.tv_del:

                break;
        }
    }
}
