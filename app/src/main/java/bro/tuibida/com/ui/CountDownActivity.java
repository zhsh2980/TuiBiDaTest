package bro.tuibida.com.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import bro.tuibida.com.R;
import bro.tuibida.com.utils.TaskTextSpanUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 倒计时
 */
public class CountDownActivity extends AppCompatActivity {

    @BindView(R.id.tv_time_left)
    TextView mTvTimeLeft;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_stop)
    Button mBtnStop;
    @BindView(R.id.frame_tv_parent)
    FrameLayout mFrameTvParent;

    private CountDownTimer mTimer;
    /*24小时时间戳*/
    private long timeStamp = 186400000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_start, R.id.btn_stop , R.id.frame_tv_parent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startCountDown();
                break;
            case R.id.btn_stop:
                stopCoundDown();
                break;
            case R.id.frame_tv_parent:
                Log.d("TaskTextSpanUtil", "点击了文字");
                break;
        }
    }

    private void stopCoundDown() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    private void pauseCoundDown() {


    }

    private void startCountDown() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = new CountDownTimer(timeStamp, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long day = millisUntilFinished / (1000 * 60 * 60 * 24);/*单位天*/
                long hour = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);/*单位时*/
                long minute = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);/*单位分*/
                long second = (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;/*单位秒*/

                String str = "<font color='#FE4070'>订单中心</font>";

                String strAll = "可在${jump_text}选择继续之前的任务哦";
                String strPart = "订单中心";

                TaskTextSpanUtil.getInstance(CountDownActivity.this).setTvDialogMiddle(mTvTimeLeft, strAll, strPart);


//                mTvTimeLeft.setText(Html.fromHtml(str + "剩余:" + day + "天" + hour + "时" + minute + "分" + second + "秒"));
            }

            @Override
            public void onFinish() {
                //倒计时结束

            }
        };
        mTimer.start();


    }

}
