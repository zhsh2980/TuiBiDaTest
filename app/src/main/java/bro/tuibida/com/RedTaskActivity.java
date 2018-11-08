package bro.tuibida.com;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bro.tuibida.com.utils.ToastUtils;
import bro.tuibida.com.view.TaskRedView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedTaskActivity extends AppCompatActivity  implements TaskRedView.OnClickListener{

    @BindView(R.id.task_red_view)
    TaskRedView mTaskRedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_task);
        ButterKnife.bind(this);
        mTaskRedView.setOnSlideListener(this);
    }

    @OnClick({R.id.btn_default, R.id.btn_expand, R.id.btn_pay_part, R.id.btn_for_free, R.id.btn_wait_delete})
    public void onViewClicked(View view) {
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
            case R.id.btn_wait_delete:
                mTaskRedView.setTask(TaskRedView.TASK_WAIT_DELETE);
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
}
