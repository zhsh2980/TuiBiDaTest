package bro.tuibida.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bro.tuibida.com.ui.*;
import com.blankj.utilcode.util.ActivityUtils;

import bro.tuibida.com.multidrag.MultiDragActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "haha";
    @BindView(R.id.et_url)
    EditText mEtUrl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

//        initFragment();
    }

    private void initView() {

        mEtUrl.setText("http://m.cnbeta.com/");

    }

    @OnClick({R.id.btn_task_red, R.id.btn_expand,
            R.id.btn_coordinator, R.id.btn_dram
            , R.id.btn_toast, R.id.btn_red_new
            , R.id.btn_multi_drag, R.id.btn_count_down
            , R.id.btn_grid, R.id.btn_switch
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_task_red:
                ActivityUtils.startActivity(RedTaskActivity.class);
                break;
            case R.id.btn_expand:
                ActivityUtils.startActivity(HideViewActivity.class);
                break;
            case R.id.btn_coordinator:
                ActivityUtils.startActivity(CoordinatorActivity.class);
                break;
            case R.id.btn_dram:
                ActivityUtils.startActivity(DramViewHeightActivity.class);
                break;
            case R.id.btn_toast:
                ActivityUtils.startActivity(ToastActivity.class);
                break;
            case R.id.btn_red_new:
                ActivityUtils.startActivity(RedNewActivity.class);
                break;
            case R.id.btn_multi_drag:
                ActivityUtils.startActivity(MultiDragActivity.class);
                break;
            case R.id.btn_count_down:
                ActivityUtils.startActivity(CountDownActivity.class);
                break;
            case R.id.btn_grid:
                ActivityUtils.startActivity(GridActivity.class);
                break;
            case R.id.btn_switch:
                ActivityUtils.startActivity(SwitchActivity.class);
                break;
        }
    }

    @OnClick(R.id.btn_load_url)
    public void onViewClicked() {
        String url = mEtUrl.getText().toString().trim();
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this, "url 不能为空", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(MainActivity.this, DialogActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
