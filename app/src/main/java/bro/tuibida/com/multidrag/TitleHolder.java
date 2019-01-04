package bro.tuibida.com.multidrag;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bro.tuibida.com.R;

/**
 * Created by zhangshan on 2019/1/4 11:58.
 */
public class TitleHolder extends RecyclerView.ViewHolder {
    TextView tv1;

    public TitleHolder(View itemView) {
        super(itemView);
        tv1 = (TextView) itemView.findViewById(R.id.adapter_two_1);
    }
}
