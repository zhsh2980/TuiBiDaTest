package bro.tuibida.com.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bro.tuibida.com.R;
import bro.tuibida.com.holder.TaskRedHolder;
import bro.tuibida.com.utils.TextSpanUtils;
import bro.tuibida.com.utils.ToastUtils;
import bro.tuibida.com.utils.UIUtils;
import bro.tuibida.com.view.MyRelativeLayout;
import bro.tuibida.com.view.TaskRedView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedTaskActivity extends AppCompatActivity implements TaskRedView.OnClickListener {

    @BindView(R.id.task_red_view)
    TaskRedView mTaskRedView;
    @BindView(R.id.tv_text_rich)
    TextView mTvTextRich;
    @BindView(R.id.tx)
    TextView mTx;
    @BindView(R.id.main_layout)
    MyRelativeLayout mMainLayout;
    private TaskRedHolder mTaskRedHolder;

    public static final String TAG = "RedTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_task);
        ButterKnife.bind(this);
        mTaskRedView.setOnClickListener(this);
        mTaskRedHolder = new TaskRedHolder(this, new TaskRedHolder.OnEndListener() {
            @Override
            public void onGuideEnd() {
                Toast.makeText(RedTaskActivity.this, "引导消失了", Toast.LENGTH_SHORT).show();
            }
        });

        initRichText();

        initMyViewLongClick();

        mTaskRedView.setTask(TaskRedView.TASK_DEFAULT);


    }

    private void initMyViewLongClick() {

        mTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "child click");
            }
        });

        mMainLayout.setLongClickListener(new MyRelativeLayout.LongClickListener() {
            @Override
            public void OnLongClick() {
                Log.d(TAG, "parent long click");
            }
        });

    }

    private void initRichText() {

        SpannableStringBuilder stringBuilder = TextSpanUtils.getBuilder(this, "已完成")
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .append("56")
                .setForegroundColor(Color.parseColor("#FFF000"))
                .setTextSize(UIUtils.dip2px(38))
                .append(".3")
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .append("%")
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .create();
        mTvTextRich.setText(stringBuilder);

    }

    @OnClick({R.id.btn_default, R.id.btn_expand, R.id.btn_pay_part, R.id.btn_for_free, R.id.btn_restore})
    public void onViewClicked(View view) {

        mTaskRedView.clearAnimation();

        switch (view.getId()) {
            case R.id.btn_default:
                mTaskRedView.setTask(TaskRedView.TASK_DEFAULT);
                break;
            case R.id.btn_expand:
                mTaskRedView.setTask(TaskRedView.TASK_EXPAND);
                break;
            case R.id.btn_pay_part:
                mTaskRedView.setTask(TaskRedView.TASK_PAY_PART);
                break;
            case R.id.btn_for_free:
                mTaskRedView.setTask(TaskRedView.TASK_FOR_FREE);
                break;
            case R.id.btn_restore:
                mTaskRedView.restoreIvRight();
                break;
        }
    }

    @Override
    public void onPayPartListener() {
        ToastUtils.showShort("补差价购买");
    }

    @Override
    public void onForFreeListener() {
        ToastUtils.showShort("点击返现免单");
    }

    @Override
    public void onCloseListener() {
        ToastUtils.showShort("关闭");
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.push_bottom_in, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("bro", "onDestroy");
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//        overridePendingTransition(0, R.anim.push_bottom_out);
//    }

    //重写finish方法
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.push_bottom_out);
    }

}
