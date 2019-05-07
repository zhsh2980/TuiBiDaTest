package bro.tuibida.com.utils;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.btxg.huluamedia.jni.NativeCallbacks;
import com.btxg.huluamedia.jni.NativeVideoPlayer;
import com.btxg.huluamedia.utils.Sizei;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author sunquan
 * sunquan@bitstarlight.com
 * @date 2018/10/15
 **/
public class XMediaPlayer {
    private PublishSubject<Integer> initEvent = PublishSubject.create();
    private PublishSubject<Integer> startEvent = PublishSubject.create();
    private PublishSubject<Integer> pauseEvent = PublishSubject.create();
    private TextureView textureView;
    private boolean isResume;
    private boolean isPauseByUser;
    private View playIcon;
    private boolean isInit = true;
    private boolean delayOnSurfaceAvaliable;
    private String videoPath;
    private int videoWidth;
    private int videoHeight;
    NativeVideoPlayer mediaPlayer;
    private OnRanderStartListener renderStartListener;
    private Sizei videoSize;
    private SurfaceTexture savedTexture;
    private Surface currSurface;

    public XMediaPlayer(String videoPath, TextureView textureView, final View playIcon, int videoWidth, int videoHeight) {
        this.videoPath = videoPath;
        this.textureView = textureView;
        this.playIcon = playIcon;
        this.videoWidth = videoWidth;
        this.videoHeight = videoHeight;
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playIcon == null) {
                    return;
                }
                if (playIcon.getVisibility() == View.VISIBLE) {
                    isPauseByUser = false;
                    XMediaPlayer.this.resumePlay();
                } else {
                    isPauseByUser = true;
                    XMediaPlayer.this.pausePlay();
                }
            }
        });
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                init();
                savedTexture = surface;
                if (currSurface == null || !currSurface.isValid()) {
                    currSurface = new Surface(surface);
                }

                if (delayOnSurfaceAvaliable) {
                    delayOnSurfaceAvaliable = false;
                    mediaPlayer.setSurface(currSurface);
                    loadVideo();
                }
                if (mediaPlayer != null) {
                    mediaPlayer.setSurface(currSurface);
                    start();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                mediaPlayer.onSurfaceChanged();
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                pausePlay();
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });
    }

    public void setRenderStartListener(OnRanderStartListener renderStartListener) {
        this.renderStartListener = renderStartListener;
    }
//
//    public Observable<Integer> observeInitEvent() {
//        return initEvent.hide();
//    }
//
//    public Observable<Integer> observeStartEvent() {
//        return startEvent.hide();
//    }
//
//    public Observable<Integer> observePauseEvent() {
//        return pauseEvent.hide();
//    }

    private void loadVideo() {
        mediaPlayer.prepare(videoPath, null);
        mediaPlayer.setLoop(true);
        videoSize = mediaPlayer.getPreparedSize();
//        ViewUtils.fitScreen(textureView, videoWidth == 0 ? videoSize.width : videoWidth, videoHeight == 0 ? videoSize.height : videoHeight);
        ViewUtils.fitScreen(textureView, 300 , 500);
    }

    public void init() {
        if (mediaPlayer == null) {
            mediaPlayer = new NativeVideoPlayer();
            setupListener();
            if (textureView.isAvailable()) {
                mediaPlayer.setSurface(new Surface(textureView.getSurfaceTexture()));
                loadVideo();
            } else {
                delayOnSurfaceAvaliable = true;
            }
            initEvent.onNext(1);
        }
    }

    private void setupListener() {
        mediaPlayer.setInfoCallback(new NativeCallbacks.IInfoCallback() {
            @Override
            public void onInfo(int what, int extra) {
                if (renderStartListener == null) return;
                if (what == NativeVideoPlayer.PLAY_INFO_RENDER_START) {
                    renderStartListener.onVideoRenderStart();
                } else if (what == NativeVideoPlayer.PLAY_INFO_REPLAY) {
                    renderStartListener.onVideoSeekRenderingStart();
                }
            }
        });
    }

    private void resumePlay() {
        if (mediaPlayer == null || !mediaPlayer.isPrepared()) {
            return;
        }
        if (isResume) {
            return;
        }
        if (mediaPlayer.isPlaying()) {
            return;
        }
        Log.i("XMediaPlayer","resumePlay");
        mediaPlayer.start();
        startEvent.onNext(1);
        isResume = true;
        if (playIcon != null) {
            playIcon.setVisibility(View.GONE);
        }
    }

    public void start() {
        if (isInit) {
            isInit = false;
            return;
        }
        if (!textureView.isAvailable()) {
            return;
        }
        if (mediaPlayer.isPlaying()) {
            return;
        }
        resumePlay();
        textureView.post(new Runnable() {
            @Override
            public void run() {
                if (isPauseByUser) {
                    XMediaPlayer.this.pausePlay();
                }
            }
        });
    }

    public void onResume() {
        if (!isPauseByUser && !isInit) {
            resumePlay();
        }
        if (savedTexture != null && currSurface != null && currSurface.isValid()) {
            if (textureView.getSurfaceTexture() != savedTexture) {
                textureView.setSurfaceTexture(savedTexture);
            }
        }
        if (isInit) {
            isInit = false;
        }
    }

    public void pausePlay() {
        if (mediaPlayer == null) {
            return;
        }
        if (!mediaPlayer.isPlaying()) {
            return;
        }
        Log.i("XMediaPlayer","pausePlay");
        mediaPlayer.pause();
        pauseEvent.onNext(1);
        if (playIcon != null) {
            playIcon.setVisibility(View.VISIBLE);
        }
        isResume = false;
    }

    public void pauseWhenDrag() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void resumeWhenDrag() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !isPauseByUser) {
            mediaPlayer.start();
        }
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (savedTexture != null) {
            savedTexture.release();
        }
    }

    public void seek(float seekTime) {
        if (mediaPlayer != null) {
            Log.i("mediaplayer_seek", "seekTime = " + seekTime);
            mediaPlayer.seek((long) seekTime);
        }
    }

    public boolean empty() {
        return mediaPlayer == null;
    }

    public long getCurProgress() {
        if (mediaPlayer == null) {
            return -1;
        }
        return mediaPlayer.getCurrentPosition();
    }

    public interface OnRanderStartListener {
        void onVideoRenderStart();

        void onVideoSeekRenderingStart();
    }
}
