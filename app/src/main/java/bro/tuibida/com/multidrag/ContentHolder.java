package bro.tuibida.com.multidrag;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bro.tuibida.com.R;

/**
 * Created by zhangshan on 2019/1/4 11:58.
 */
public class ContentHolder extends RecyclerView.ViewHolder {
    TextView tvContent;
    ImageView mCheckBox;

    public ContentHolder(View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tv_content);
        mCheckBox = itemView.findViewById(R.id.check_box);
    }
}
