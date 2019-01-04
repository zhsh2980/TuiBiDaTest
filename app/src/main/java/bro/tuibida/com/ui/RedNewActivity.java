package bro.tuibida.com.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import bro.tuibida.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedNewActivity extends AppCompatActivity {

    Handler mHandler = new Handler();
    @BindView(R.id.iv_red_origin)
    ImageView mIvRedOrigin;
    @BindView(R.id.iv_red_move)
    ImageView mIvRedMove;
    @BindView(R.id.main_layout)
    RelativeLayout mMainLayout;
    @BindView(R.id.btn_red_translate)
    Button mBtnRedTranslate;
    @BindView(R.id.btn_cicle_expand)
    Button mBtnCicleExpand;
    @BindView(R.id.btn_pop)
    Button mBtnPop;
    @BindView(R.id.btn_coin)
    Button mBtnCoin;
    @BindView(R.id.btn_3d)
    Button mBtn3d;
    @BindView(R.id.frame_root)
    FrameLayout mFrameRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_new);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.btn_red_translate, R.id.btn_cicle_expand,
            R.id.btn_pop, R.id.btn_coin, R.id.btn_3d})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_red_translate:

                break;
            case R.id.btn_cicle_expand:

                break;
            case R.id.btn_pop:

                break;
            case R.id.btn_coin:

                break;
            case R.id.btn_3d:

                break;
            default:
        }
    }
}
