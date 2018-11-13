package bro.tuibida.com.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private String TAG = "TaskRedView";

    public int TASK_OLD = -1;//前一个的状态
    public int TASK_CURRENT = -1;//当前的状态

    public final static int TASK_DEFAULT = 0;
    public final static int TASK_EXPAND = 1;
    public final static int TASK_PAY_PART = 2;
    public final static int TASK_FOR_FREE = 3;
    public final static int TASK_WAIT_DELETE = 4;//待删除的状态

    //是否可以抖动
    private boolean canShake = true;

    @BindView(R.id.iv_left)
    RoundedImageView mIvLeft;
    @BindView(R.id.tv_good_name)
    TextView mTvGoodName;
    @BindView(R.id.frame_left)
    FrameLayout mFrameLeft;
    @BindView(R.id.tv_has_finished_part)
    TextView mTvMiddle;
    @BindView(R.id.iv_progress)
    ZzHorizontalProgressBar mIvProgress;
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
    @BindView(R.id.frame_arrow_right)
    FrameLayout mFrameArrowRight;
    @BindView(R.id.frame_close_right)
    FrameLayout mFrameCloseRight;

    private OnClickListener onClickListener;

    int mTouchSlop; //最短滑动距离
    Activity mActivity;

    public void setOnClickListener(OnClickListener onClickListener) {
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
//        setOnLongClickLis();
    }

    private void init(Context context) {
        Log.i("bro", "init");
        LayoutInflater.from(context).inflate(R.layout.view_task_red, this);
        ButterKnife.bind(this);
        mActivity = (Activity) context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//        setUiDefault();
//        view_white_line = findViewById(R.id.view_white_line);
    }

    private void setOnLongClickLis() {
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (!canShake) {
                    return false;
                }
                if (TASK_CURRENT == TASK_FOR_FREE) {
                    return false;
                }
                //哪几种状态支持抖动
                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.add_tab_shake_anim);
                shake.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Log.i("AddCartAnimManager --> ", String.format("|||==>>  onAnimationStart([animation]):%s \n", "抖动动画开始"));

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.i("AddCartAnimManager --> ", String.format("|||==>>  onAnimationStart([animation]):%s \n", "抖动动画结束"));
                        clearAnimation();
                        mFrameArrowRight.setVisibility(GONE);
                        mFrameCloseRight.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                startAnimation(shake);
                //抖动状态不能再次抖动
                canShake = false;
                return true;
            }
        });
    }

    @OnClick({R.id.frame_arrow_right, R.id.frame_close_right,
            R.id.btn_middle})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.frame_arrow_right) {
            if (isExpand) {
                //折叠
                setTaskDefault();
                TASK_OLD = TASK_CURRENT;
                TASK_CURRENT = TASK_DEFAULT;
            } else {
                //展开
                //刚进来第一次
                if (-1 == TASK_OLD) {
                    setTaskExpand();
                    TASK_CURRENT = TASK_EXPAND;
                    TASK_OLD = TASK_DEFAULT;
                }

                if (TASK_OLD == TASK_EXPAND) {
                    setTaskExpand();
                    TASK_CURRENT = TASK_EXPAND;
                }
                if (TASK_OLD == TASK_PAY_PART) {
                    setTaskPayPart();
                    TASK_CURRENT = TASK_PAY_PART;
                }
                if (TASK_OLD == TASK_FOR_FREE) {
                    setTaskForFree();
                    TASK_CURRENT = TASK_FOR_FREE;
                }
            }
        } else if (i == R.id.frame_close_right) {
            //关闭
            if (onClickListener != null) {
                onClickListener.onCloseListener();
            }
        } else if (i == R.id.btn_middle) {
            Log.d("TaskRedView", "点按了");
            //中间按钮的点击
            if (TASK_CURRENT == TASK_PAY_PART) {
                if (onClickListener != null) {
                    onClickListener.onPayPartListener();
                }
            } else if (TASK_CURRENT == TASK_FOR_FREE) {
                if (onClickListener != null) {
                    onClickListener.onForFreeListener();
                }
            }
        }
    }

    /**
     * 变为默认的状态 (动画 + UI)
     */
    private void setTaskDefault() {
        isExpand = false;
        setUiDefault();
        if (TASK_OLD == -1 && TASK_CURRENT != -1) {//原先就是收缩,则箭头不转圈
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_180_0).toggle();
        } else if (TASK_CURRENT == TASK_DEFAULT) {//原先就是收缩,则箭头不转圈
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_no).toggle();
        } else {
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_180_0).toggle();
        }
    }

    /**
     * 变为展开的状态 (动画 + UI)
     */
    private void setTaskExpand() {
        isExpand = true;
        setUiExpand();
        if (TASK_OLD == -1 && TASK_CURRENT != -1) {//原先就是收缩,则箭头不转圈
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_0_180).toggle();
        } else if (TASK_CURRENT == TASK_EXPAND) {//原先就是收缩,则箭头不转圈
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_no).toggle();
        } else {
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_0_180).toggle();
        }
    }

    /**
     * 变为支付剩余的状态 (动画 + UI)
     */
    private void setTaskPayPart() {
//        if (!isExpand) {
        isExpand = true;
        setUiPayPart();
        if (TASK_OLD == -1 && TASK_CURRENT != -1) {//原先就是收缩,则箭头不转圈
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_0_180).toggle();
        } else if (TASK_CURRENT == TASK_PAY_PART) {//原先就是收缩,则箭头不转圈
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_no).toggle();
        } else {
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_0_180).toggle();
        }
    }

    /**
     * 变为返现免单的状态 (动画 + UI)
     */
    private void setTaskForFree() {
//        if (!isExpand) {
        isExpand = true;
        setUiForFree();
        //叉号隐藏,所以,宽度减去32
        TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225 - 32, TaskRedAnimUtils.tog_no).startChangeSize();
//        }
    }

    /**
     * 变为等待删除的状态 (动画 + UI)
     */
    private void setTaskWaitDelete() {
//        if (isExpand) {
        isExpand = false;
        setUiWaitDelete();
        TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_no).startChangeSize();
//        }
    }

    /**
     * 变为默认的状态 (UI)
     */
    private void setUiDefault() {
        setVisibility(VISIBLE);
        setViewWidthHeight(mLlTaskRedRoot, 210, 30);
        setViewWidthHeight(mIvProgress, 60, 7);
        hideRelatedUi();
        mTvGoodName.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        mFrameArrowRight.setVisibility(VISIBLE);
    }

    /**
     * 变为展开的状态 (UI)
     */
    private void setUiExpand() {
        setVisibility(VISIBLE);
        setViewWidthHeight(mIvProgress, 85, 7);
        hideRelatedUi();
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        if (mFrameArrowRight.getVisibility() != VISIBLE) {
            mFrameArrowRight.setVisibility(VISIBLE);
        }
    }

    /**
     * 变为支付剩余的状态 (UI)
     */
    private void setUiPayPart() {
        setVisibility(VISIBLE);
        hideRelatedUi();
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setText("补差价购买");
        if (mFrameArrowRight.getVisibility() != VISIBLE) {
            mFrameArrowRight.setVisibility(VISIBLE);
        }
    }

    /**
     * 变为返现免单的状态 (UI)
     */
    private void setUiForFree() {
        setVisibility(VISIBLE);
        hideRelatedUi();
        mIvArrowRight.clearAnimation();
        mFrameArrowRight.clearAnimation();
        mFrameArrowRight.setVisibility(GONE);
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setText("点击返现免单");
//        mFrameCloseRight.setVisibility(VISIBLE);
    }

    /**
     * 变为等待删除的状态 (UI)
     */
    private void setUiWaitDelete() {
        setVisibility(VISIBLE);
        setViewWidthHeight(mIvProgress, 60, 7);
        hideRelatedUi();
        mIvArrowRight.clearAnimation();
        mFrameArrowRight.clearAnimation();
        mFrameArrowRight.setVisibility(GONE);
        mTvGoodName.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        mFrameCloseRight.setVisibility(VISIBLE);
    }

    //每次变化前,先把共用的 UI 都隐藏了,每种里面单独显示
    private void hideRelatedUi() {
        mIvLeft.setVisibility(GONE);
        mTvGoodName.setVisibility(GONE);
        mTvMiddle.setVisibility(GONE);
        mIvProgress.setVisibility(GONE);
        mFrameCloseRight.setVisibility(GONE);
        mBtnMiddle.setVisibility(GONE);


    }

    private void setViewWidthHeight(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = UIUtils.dip2px(width);
        params.height = UIUtils.dip2px(height);
        view.setLayoutParams(params);
    }

    //供外界调用,改变形态
    public void setTask(int taskType) {
        //记录变换前的状态,点击再次回去
        TASK_OLD = TASK_CURRENT;
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
            default:
                break;
        }

    }

//    //设置中间的文字
//    public void setTvMiddle(String strAll, String strPart) {
//        TaskTextSpanUtil.getInstance().setTvMiddle(mTvMiddle, strAll, strPart);
//    }

    //设置中间按钮的文字
    public void setBtnTextMiddle(String str) {
        if (mBtnMiddle != null) {
            mBtnMiddle.setText(str + "");
        }
    }

    //设置左边文字
    public void setTvLeft(String str) {
        mTvGoodName.setText(str + "");
    }

    //设置中间按钮的文字
    public void setProgress(String progress) {
        try {
            mIvProgress.setProgress(Integer.parseInt(progress));
        } catch (Exception e) {
            Log.i(TAG, " -- setProgress Exception --");
        }
    }

    //显示箭头,隐藏关闭按钮
    public void restoreIvRight() {
        mFrameArrowRight.setVisibility(VISIBLE);
        mFrameCloseRight.setVisibility(GONE);
        canShake = true;
    }

    private boolean isLongClick = false; //是否是长点击事件
    private boolean isRelease = false; //是否已经释放
    private static int LONG_CLICK_TIME = 600;

    private LongClickListener longClickListener;

    //自定义长点击事件接口
    public interface LongClickListener {
        void OnLongClick();
    }

    public void setLongClickListener(LongClickListener l) {
        this.longClickListener = l;
    }

    private Runnable countDownRunnable = new Runnable() {
        @Override
        public void run() {
            isLongClick = true;
            //当用户在LONG_CLICK_TIME时间内没有做抬起滑动等取消动作，则触发longclick事件
            if (!isRelease) {
                return;
            }
            isRelease = true;
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("TaskRedView", "长按了");
                    if (longClickListener != null) {
                        longClickListener.OnLongClick();
                    }
                }
            });
        }
    };

    //记录按下时的坐标
    int downX = 0;
    int downY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                isRelease = false;
                isLongClick = false;
                //延迟LONG_CLICK_TIME毫秒的时间，触发长点击事件
                postDelayed(countDownRunnable, LONG_CLICK_TIME);
                break;
            case MotionEvent.ACTION_MOVE:
                //当横移或纵移的长度大于系统规定的滑动最短距离时，则视为用户取消了longclick事件
                if (Math.abs(event.getX() - downX) < mTouchSlop || Math.abs(event.getY() - downY) < mTouchSlop || isRelease) {
                    break;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                isRelease = true;
                if (isLongClick) {
                    //当已经是longclick事件时，parent则拦截该事件，child不会再收到该事件
                    return true;
                }
                break;
        }

        boolean isDispatch = super.dispatchTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN && !isDispatch) {
            //当down事件返回false时 不触发up事件 所以返回true强制触发UP事件，否则会出现click父布局出现longclick的效果
            return true;
        }
        return isDispatch;
    }


}
