package bro.tuibida.com.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

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

    public static int TASK_CURRENT = 0;//当前的状态
    public final static int TASK_DEFAULT = 0;
    public final static int TASK_EXPAND = 1;
    public final static int TASK_PAY_PART = 2;
    public final static int TASK_FOR_FREE = 3;
    public final static int TASK_WAIT_DELETE = 4;


    @BindView(R.id.iv_left)
    RoundedImageView mIvLeft;
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
    @BindView(R.id.frame_right)
    FrameLayout mFrameRight;
    @BindView(R.id.ll_task_red_root)
    LinearLayout mLlTaskRedRoot;

    boolean isExpand = false;
    @BindView(R.id.iv_close_right)
    ImageView mIvCloseRight;
    @BindView(R.id.btn_middle)
    Button mBtnMiddle;
    @BindView(R.id.frame_arrow_right_click)
    FrameLayout mFrameArrowRightClick;
    @BindView(R.id.frame_close_right_click)
    FrameLayout mFrameCloseRightClick;

    private OnClickListener onClickListener;

    public void setOnSlideListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    //自定义接口
    public interface OnClickListener {
        //补差价
        void onPayPartListener();

        //返现免单
        void onForFreeListener();

        //关闭
        void onCloseListener();
    }


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
        Log.i("bro", "init");
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

    @OnClick({R.id.frame_arrow_right_click, R.id.frame_close_right_click, R.id.btn_middle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frame_arrow_right_click:
                if (isExpand) {
                    //折叠
                    setUiDefault();
                    isExpand = false;
                    TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_180_0).toggle();
                } else {
                    //展开
                    isExpand = true;
                    setUiExpand();
                    TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_0_180).toggle();
                }
                break;
            case R.id.frame_close_right_click:
                // TODO: 2018/11/8 关闭
                if (onClickListener != null) {
                    onClickListener.onCloseListener();
                }
                break;
            case R.id.btn_middle:
                if (TASK_CURRENT == TASK_PAY_PART) {
                    if (onClickListener != null) {
                        onClickListener.onPayPartListener();
                    }
                } else if (TASK_CURRENT == TASK_FOR_FREE) {
                    if (onClickListener != null) {
                        onClickListener.onForFreeListener();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void setTaskDefault() {
        if (isExpand) {
            setUiDefault();
            isExpand = false;
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_180_0).toggle();
        }
    }

    private void setTaskExpand() {
        if (!isExpand) {
            isExpand = true;
            setUiExpand();
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_0_180).toggle();
        }
    }

    private void setTaskPayPart() {
        if (!isExpand) {
            isExpand = true;
            setUiPayPart();
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_0_180).toggle();
        }
    }

    private void setTaskForFree() {
        if (!isExpand) {
            isExpand = true;
            setUiForFree();
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_no).startChangeSize();
        }
    }

    private void setTaskWaitDelete() {
        if (isExpand) {
            isExpand = false;
            setUiWaitDelete();
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_no).startChangeSize();
        }
    }

    private void setUiDefault() {
        setViewWidthHeight(mLlTaskRedRoot, 210, 30);
        setViewWidthHeight(mIvProgress, 60, 7);
        hideRelatedUi();
        mTvGoodName.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        mFrameArrowRightClick.setVisibility(VISIBLE);
    }

    private void setUiExpand() {
        setViewWidthHeight(mIvProgress, 85, 7);
        hideRelatedUi();
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        mFrameArrowRightClick.setVisibility(VISIBLE);
    }

    private void setUiPayPart() {
        hideRelatedUi();
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setText("补差价购买");
        mFrameArrowRightClick.setVisibility(VISIBLE);
    }

    private void setUiForFree() {
        hideRelatedUi();
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setText("点击返现免单");
        mFrameCloseRightClick.setVisibility(VISIBLE);
    }

    private void setUiWaitDelete() {
        hideRelatedUi();
        mTvGoodName.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        mFrameCloseRightClick.setVisibility(VISIBLE);
    }

    //每次变化前,先把共用的 UI 都隐藏了,每种里面单独显示
    private void hideRelatedUi() {
        mIvLeft.setVisibility(GONE);
        mTvGoodName.setVisibility(GONE);
        mTvMiddle.setVisibility(GONE);
        mIvProgress.setVisibility(GONE);
        mFrameCloseRightClick.setVisibility(GONE);
        mBtnMiddle.setVisibility(GONE);
        mIvArrowRight.clearAnimation();
        mFrameArrowRightClick.setVisibility(GONE);
    }

    private void setViewWidthHeight(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = UIUtils.dip2px(width);
        params.height = UIUtils.dip2px(height);
        view.setLayoutParams(params);
    }

    //供外界调用,改变形态
    public void setTask(int taskType) {
        TASK_CURRENT = taskType;
        switch (TASK_CURRENT) {
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

    @OnClick(R.id.btn_middle)
    public void onViewClicked() {
    }
}
