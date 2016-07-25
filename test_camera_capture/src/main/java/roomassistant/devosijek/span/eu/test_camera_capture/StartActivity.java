package roomassistant.devosijek.span.eu.test_camera_capture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import roomassistant.devosijek.span.eu.test_camera_capture.custom_media_recorder.MediaRecorderActivity;
import roomassistant.devosijek.span.eu.test_camera_capture.take_video.VideoIntentActivity;

public class StartActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void captureVideo(View view)
    {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), MediaRecorderActivity.class);
        startActivity(i);
    }

    public void previewVideoInfo(View view)
    {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), VideoIntentActivity.class);
        startActivity(i);
    }
}
