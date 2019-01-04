package bro.tuibida.com.multidrag;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/8/2 11:37.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class RecyItemTouchHelperCallback extends ItemTouchHelper.Callback {
    RecyclerView.Adapter mAdapter;
    boolean isSwipeEnable;
    private final String TAG = "RecyItemTouchHelper";
    private ArrayList<String> mOldTempList;
    //    boolean isFirstDragUnable;

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        isSwipeEnable = true;
//        isFirstDragUnable = false;
    }

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter, boolean isSwipeEnable, boolean isFirstDragUnable) {
        mAdapter = adapter;
        this.isSwipeEnable = isSwipeEnable;
//        this.isFirstDragUnable = isFirstDragUnable;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (toPosition == 0 || toPosition == 2) {
            return false;
        }

//        Log.i(TAG, "fromPosition: " + fromPosition + "------toPosition: " + toPosition);
        // 创建一个原来的 List 的副本
        mOldTempList = new ArrayList<>(((ViewTypeAdapter) mAdapter).getDataList());
        if (toPosition == 1 || fromPosition == 1) {
            Collections.swap(((ViewTypeAdapter) mAdapter).getDataList(), fromPosition, toPosition);
        } else if (fromPosition < toPosition) {
            //这么写是兼容 GridView
            //上下其实直接交换上下两个元素即可
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(((ViewTypeAdapter) mAdapter).getDataList(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(((ViewTypeAdapter) mAdapter).getDataList(), i, i - 1);
            }
        }
//            mAdapter.notifyItemMoved(fromPosition, toPosition);
        //用 DiffUtil 工具刷新
        setRefreshDiffUtil(mOldTempList, ((ViewTypeAdapter) mAdapter).getDataList());
        return true;
    }

    private void setRefreshDiffUtil(final ArrayList<String> oldTemp, final List<String> datas) {

        // 实现 Callback
        DiffUtil.Callback callback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldTemp.size();
            }

            @Override
            public int getNewListSize() {
                return datas.size();
            }

            // 判断是否是同一个 item
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldTemp.get(oldItemPosition).equals(datas.get(newItemPosition));
            }

            // 如果是同一个 item 判断内容是否相同
            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldTemp.get(oldItemPosition).equals(datas.get(newItemPosition));
            }
        };
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        // 把结果应用到 adapter
        diffResult.dispatchUpdatesTo(mAdapter);
        // 通知刷新了之后，要更新副本数据到最新
        oldTemp.clear();
        oldTemp.addAll(datas);

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();
        mAdapter.notifyItemRemoved(adapterPosition);
        ((ViewTypeAdapter) mAdapter).getDataList().remove(adapterPosition);
    }

//    @Override
//    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
//        }
//        super.onSelectedChanged(viewHolder, actionState);
//    }
//
//    @Override
//    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        super.clearView(recyclerView, viewHolder);
//        viewHolder.itemView.setBackgroundColor(Color.WHITE);
//    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }
//    @Override
//    public boolean isLongPressDragEnabled() {
//        return !isFirstDragUnable;
//    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnable;
    }
}
