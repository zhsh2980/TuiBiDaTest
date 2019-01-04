package bro.tuibida.com.multidrag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bro.tuibida.com.R;

/**
 * Created by zhangshan on 2019/1/4 11:48.
 */
public class ViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TITLE_ITEM = 1;
    public static final int CONTENT_ITEM = 2;

    private Context mContext;
    List<String> mDatas = new ArrayList<>();

    public ViewTypeAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public List<String> getDataList() {
        return mDatas;
    }

    public void addData(List<String> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder;
        if (TITLE_ITEM == viewType) {
            View v = mInflater.inflate(R.layout.item_title, parent, false);
            holder = new TitleHolder(v);
        } else {
            View v = mInflater.inflate(R.layout.item_content, parent, false);
            holder = new ContentHolder(v);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentHolder) {
            ((ContentHolder) holder).tv.setText(mDatas.get(position));
        } else {
            ((TitleHolder) holder).tv1.setText(mDatas.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
//            if(position % 3 == 0){
//                return CONTENT_ITEM;
//            }else{
//                return TITLE_ITEM;
//            }
        if (position == 0 || position == 2) {
            return TITLE_ITEM;
        } else {
            return CONTENT_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}
