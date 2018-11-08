package bro.tuibida.com;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "haha";
    @BindView(R.id.fram)
    FrameLayout mFram;

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

    @OnClick({R.id.btn_task_red,R.id.btn_expand, R.id.btn_coordinator})
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

}
