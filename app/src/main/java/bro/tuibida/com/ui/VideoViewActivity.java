package bro.tuibida.com.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.VideoView;
import bro.tuibida.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoViewActivity extends AppCompatActivity {

    @BindView(R.id.video_preview)
    VideoView videoPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String url_video_demo = Environment.getExternalStorageDirectory() + "/video_demo.mp4";
//        videoPreview.setVideoPath("file://"+url_video_demo);
        videoPreview.setVideoPath(url_video_demo);
        videoPreview.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                videoPreview.pause();
                seek();
            }
        }, 2000);
    }

    int i = 0;

    private void seek() {
        if (i <= videoPreview.getDuration()) {
            int j = i = i + 1000;
            Log.d("VideoViewActivity", "j:" + j);
            videoPreview.seekTo(j);
            videoPreview.pause();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    seek();
                }
            }, 3000);
        }
    }
}
