package bro.tuibida.com.multidrag;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import bro.tuibida.com.R;

/**
 * Created by zhangshan on 2019/1/4 11:58.
 */
public class TitleHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;

    public TitleHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.adapter_two_1);
    }
}
