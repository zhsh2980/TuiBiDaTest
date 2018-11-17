package bro.tuibida.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;

import bro.tuibida.com.ui.DialogActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "haha";
    @BindView(R.id.fram)
    FrameLayout mFram;
    @BindView(R.id.et_url)
    EditText mEtUrl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        initFragment();
    }

    private void initFragment() {
        MainFragment fragment = new MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fram, fragment);
        transaction.commit();
    }

    @OnClick({R.id.btn_task_red, R.id.btn_expand, R.id.btn_coordinator})
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
