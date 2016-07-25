package roomassistant.devosijek.span.eu.test_camera_capture.take_video;

import android.animation.PropertyValuesHolder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.ArrayList;

import roomassistant.devosijek.span.eu.test_camera_capture.R;

public class VideoCropperActivity extends AppCompatActivity
{
    private String TAG = "VideoCropper";

    private Uri videoUri;
    public static final String URI_TAG = "uri_tag";
    private MediaController videoController;

    private ArrayList<Integer> ids;

    private int STATE_ANIMATING_OUT = 0x1;
    private int STATE_ANIMATING_IN = 0x2;
    private int STATE_VIDEO_PLAYING = 0x4;
    private int STATE_VIDEO_SEEKING = 0x8;

    private int STATE = 0x0;

    private PropertyValuesHolder headerSlideOut;
    private PropertyValuesHolder headerSlideIn;
    private PropertyValuesHolder footerSlideOut;
    private PropertyValuesHolder footerSlideIn;

    // Final values for animations
    private float headerSlideOutStart;
    private float headerSlideOutEnd = 0.0F;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Slide header from header height to 0
//        headerSlideOut = PropertyValuesHolder.ofFloat(View.SCALE_Y, );


//        initalizeIds();
//        videoUri = Uri.parse(getIntent().getStringExtra(URI_TAG));
//        setContentView(instantiateLayout());

//        stretchVideoView();
    }

    private void stretchVideoView()
    {
        setContentView(R.layout.video_view_filled);
        VideoView view = (VideoView) findViewById(R.id.videoViewRelative);
        videoController = new MediaController(this);
        videoController.setAnchorView(view);
        view.setVideoURI(videoUri);
        view.setMediaController(videoController);
    }

    private ViewGroup instantiateLayout()
    {
        // Global container
        RelativeLayout container = new RelativeLayout(this);
        RelativeLayout.LayoutParams paramsContainer = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.setLayoutParams(paramsContainer);
        container.setId(ids.get(0));

        // Video preview wrapper
        FrameLayout videoPreviewContainer = new FrameLayout(this);
        FrameLayout.LayoutParams paramsVideoPreviewContainer = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//                FrameLayout.LayoutParams.MATCH_PARENT, 300);

        videoPreviewContainer.setLayoutParams(paramsVideoPreviewContainer);
        videoPreviewContainer.setId(ids.get(1));

        // Video view and its' controller
        VideoView videoPreview = new VideoView(this);

        videoController = new MediaController(this);
        videoController.setAnchorView(videoPreview);
        videoPreview.setVideoURI(videoUri);
        videoPreview.setMediaController(videoController);
        videoPreview.setId(ids.get(2));

        FrameLayout.LayoutParams videoPreviewParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        videoPreview.setLayoutParams(videoPreviewParams);

        // Add to wrapper
        videoPreviewContainer.addView(videoPreview);

        // Add range seek bar below video preview wrapper
//        RangeSeekBar<Integer> rangeSeekBar = instantiateSeekbar();
//        rangeSeekBar.setId(ids.get(3));
//        RelativeLayout.LayoutParams paramsRangeSeekBar = new RelativeLayout.LayoutParams(-1, -2); //match_parent, wrap_content
//        paramsRangeSeekBar.addRule(RelativeLayout.BELOW, ids.get(1));
//        rangeSeekBar.setLayoutParams(paramsRangeSeekBar);

        container.addView(videoPreviewContainer);
//        container.addView(rangeSeekBar);

        return container;
    }

    private RangeSeekBar<Integer> instantiateSeekbar()
    {
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<>(0, 100, this);
        rangeSeekBar.setNotifyWhileDragging(true);
        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                // handle changed range values
                Log.i(TAG, "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });

        return rangeSeekBar;
    }

    private void initalizeIds()
    {
        ids = new ArrayList<>();
        // Id can be any positive number
        // Ids live in namespace defined by root ViewGroup
        ids.add(277381); //global container
        ids.add(123123); //video preview container
        ids.add(92991);  //video preview
        ids.add(123299); // range seek bar
    }
}
