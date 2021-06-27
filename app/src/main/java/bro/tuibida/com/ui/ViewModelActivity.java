package bro.tuibida.com.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import bro.tuibida.com.R;
import bro.tuibida.com.entity.User;
import bro.tuibida.com.model.UserModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangshan on 2019-05-16 17:53.
 */
public class ViewModelActivity extends AppCompatActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.btn_click)
    Button btnClick;
    private UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);
        ButterKnife.bind(this);

        initModel();

    }

    private void initModel() {

        //构建ViewModel实例
        userModel = ViewModelProviders.of(this).get(UserModel.class);

        //让TextView观察ViewModel中数据的变化,并实时展示
        userModel.mUserLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvContent.setText(user.toString());
            }
        });


    }

    @OnClick({R.id.btn_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_click:
//                Toast.makeText(this, "点到我了", Toast.LENGTH_SHORT).show();
                //点击按钮  更新User数据  观察TextView变化
                userModel.doSomething();
                break;
        }
    }
}
