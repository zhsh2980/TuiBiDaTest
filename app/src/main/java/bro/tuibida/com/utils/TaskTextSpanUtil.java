package bro.tuibida.com.utils;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by zhangshan on 2018/11/9 19:35.
 */
public class TaskTextSpanUtil {

    private static Activity mActivity;
    private TaskTextSpanUtil() {
    }

    private static class SingletonInstance {
        private static final TaskTextSpanUtil INSTANCE = new TaskTextSpanUtil();
    }

    public static TaskTextSpanUtil getInstance(Activity activity) {
        mActivity = activity;
        return TaskTextSpanUtil.SingletonInstance.INSTANCE;
    }

    //设置中间的文字
    public void setTvMiddle(TextView textView, String strAll, String strPart) {
        //表示普通的文案
        if (TextUtils.isEmpty(strPart)) {
            textView.setText(strAll);
            return;
        }
        //已完成${progress}%  56.3
        //再花${price}元可得  5.6
        try {
            String str_start = strAll.substring(0, strAll.indexOf("$"));
            String[] moneySplit = strPart.split("\\.");
            String moneyBefore = moneySplit[0];
            String moneyAfter = moneySplit[1];
            String str_end = strAll.substring(strAll.lastIndexOf("}") + 1, strAll.length());
            textView.setText(setSpan(str_start, moneyBefore, "." + moneyAfter, str_end) + "");
        } catch (Exception e) {
            Log.i("bro", " -- setTvMiddle Exception --");
        }
    }

    private SpannableStringBuilder setSpan(String strStart, String moneyBefore, String moneyAfter, String strEnd) {
        SpannableStringBuilder stringBuilder = TextSpanUtils.getBuilder(mActivity, strStart)
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .append(moneyBefore)
                .setForegroundColor(Color.parseColor("#FFF000"))
                .setTextSize(UIUtils.dip2px(38))
                .append(moneyAfter)
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .append(strEnd)
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .create();
        return stringBuilder;
    }

}
