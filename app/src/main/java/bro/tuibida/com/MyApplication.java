package bro.tuibida.com;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

import bro.tuibida.com.utils.SingleContainer;

/**
 * Created by zhangshan on 2018/10/25 11:33.
 */
public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        Utils.init(this);
        SingleContainer.init(mContext);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }
}
