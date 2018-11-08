package bro.tuibida.com.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import bro.tuibida.com.R;
import bro.tuibida.com.utils.TaskRedAnimUtils;
import bro.tuibida.com.utils.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangshan on 2018/11/8 15:30.
 */
public class TaskRedView extends FrameLayout {

    public final static int TASK_DEFAULT = 0;
    public final static int TASK_EXPAND = 1;
    public final static int TASK_PAY_PART = 2;
    public final static int TASK_FOR_FREE = 3;
    public final static int TASK_WAIT_DELETE = 4;


    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.tv_good_name)
    TextView mTvGoodName;
    @BindView(R.id.frame_left)
    FrameLayout mFrameLeft;
    @BindView(R.id.tv_has_finished_part)
    TextView mTvMiddle;
    @BindView(R.id.iv_progress)
    ImageView mIvProgress;
    @BindView(R.id.frame_middle)
    FrameLayout mFrameMiddle;
    @BindView(R.id.iv_arrow_right)
    ImageView mIvArrowRight;
    @BindView(R.id.rela_right)
    RelativeLayout mFrameRight;
    @BindView(R.id.ll_task_red_root)
    LinearLayout mLlTaskRedRoot;

    boolean isExpand = false;
    @BindView(R.id.iv_close_right)
    ImageView mIvCloseRight;
    @BindView(R.id.btn_middle)
    Button mBtnMiddle;

    public TaskRedView(Context context) {
        this(context, null);
    }

    public TaskRedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskRedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        //长按抖动?
        setOnLongClickLis();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_task_red, this);
        ButterKnife.bind(this);
        setUiDefault();
//        view_white_line = findViewById(R.id.view_white_line);
    }

    private void setOnLongClickLis() {
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // TODO: 2018/11/8  长按抖动

                return true;
            }
        });
    }

    @OnClick({R.id.iv_close_right, R.id.rela_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close_right:
                if (isExpand) {
                    //折叠
                    TaskRedAnimUtils.newInstance(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_180_0).toggle();
                    isExpand = !isExpand;
                    setUiDefault();
                } else {
                    //展开
                    TaskRedAnimUtils.newInstance(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_0_180).toggle();
                    isExpand = !isExpand;
                    setUiExpand();
                }
                break;
            case R.id.rela_right:
                // TODO: 2018/11/8 关闭 
                break;
        }
    }

    private void setTaskDefault() {
        if (isExpand) {
//            TaskRedAnimUtils.newInstance(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_180_0).toggle();
            setUiDefault();
            isExpand = false;
        }
    }

    private void setTaskExpand() {
        if (!isExpand) {
//            TaskRedAnimUtils.newInstance(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_0_180).toggle();
            isExpand = true;
            setUiExpand();
        }
    }

    private void setTaskPayPart() {
        if (!isExpand) {
//            TaskRedAnimUtils.newInstance(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_0_180).toggle();
            isExpand = true;
            setUiPayPart();
        }
    }

    private void setTaskForFree() {
        if (!isExpand) {
//            TaskRedAnimUtils.newInstance(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_no).toggle();
            isExpand = true;
            setUiForFree();
        }
    }

    private void setTaskWaitDelete() {
        if (isExpand) {
//            TaskRedAnimUtils.newInstance(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_no).toggle();
            isExpand = false;
            setUiWaitDelete();
        }
    }

    private void setUiDefault() {
        setViewWidthHeight(mLlTaskRedRoot, 210, 30);
        setViewWidthHeight(mIvProgress, 60, 7);
        hideRelatedUi();
        showView(mTvGoodName);
        showView(mIvProgress);
        showView(mIvArrowRight);
    }

    private void setUiExpand() {
        setViewWidthHeight(mIvProgress, 85, 7);
        hideRelatedUi();
        showView(mIvLeft);
        showView(mTvMiddle);
        showView(mIvProgress);
        showView(mIvArrowRight);
    }

    private void setUiPayPart() {
        hideRelatedUi();
        showView(mIvLeft);
        showView(mTvMiddle);
        showView(mBtnMiddle);
        showView(mIvArrowRight);
    }

    private void setUiForFree() {
        hideRelatedUi();
        showView(mIvLeft);
        showView(mTvMiddle);
        showView(mBtnMiddle);
        showView(mIvCloseRight);
    }

    private void setUiWaitDelete() {
        hideRelatedUi();
        showView(mTvGoodName);
        showView(mIvProgress);
        showView(mIvCloseRight);
    }

    //每次变化前,先把共用的 UI 都隐藏了,每种里面单独显示
    private void hideRelatedUi() {
        hideView(mIvLeft);
        hideView(mTvGoodName);
        hideView(mTvMiddle);
        hideView(mIvProgress);
        hideView(mIvArrowRight);
        hideView(mIvCloseRight);
        hideView(mBtnMiddle);
    }

    private void setViewWidthHeight(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = UIUtils.dip2px(width);
        params.height = UIUtils.dip2px(height);
        view.setLayoutParams(params);
    }

    //供外界调用,改变形态
    public void setTask(int taskType) {
        switch (taskType) {
            case TASK_DEFAULT:
                setTaskDefault();
                break;
            case TASK_EXPAND:
                setTaskExpand();
                break;
            case TASK_PAY_PART:
                setTaskPayPart();
                break;
            case TASK_FOR_FREE:
                setTaskForFree();
                break;
            case TASK_WAIT_DELETE:
                setTaskWaitDelete();
                break;
            default:
                break;
        }

    }

    private void showView(View view) {
        if (view != null) {
            view.setVisibility(VISIBLE);
        }
    }

    private void hideView(View view) {
        if (view != null) {
            view.setVisibility(GONE);
        }
    }


    @OnClick(R.id.btn_middle)
    public void onViewClicked() {
    }
}
