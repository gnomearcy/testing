package roomassistant.devosijek.span.eu.test_camera_capture.take_video;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

import roomassistant.devosijek.span.eu.test_camera_capture.R;

public class VideoIntentActivity extends AppCompatActivity
{
    private static String VIDEO_PATH;
    Button btn;
    TextView text;
    Button attach;
    VideoView preview;

    private final static int VIDEO_CAPTURE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_intent);

        VIDEO_PATH = Environment.getExternalStorageDirectory() + "/DCIM/100MEDIA/Test.mp4";

        text = (TextView) findViewById(R.id.video_information);
        btn = (Button) findViewById(R.id.capture_video);
        attach = (Button) findViewById(R.id.attack_video);
        preview = (VideoView) findViewById(R.id.videopreview);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                captureVideo();
            }
        });

        attach.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                attachVideo();
            }
        });
    }


    private void videoCropper()
    {
        // launch activit
    }

    private void attachVideo()
    {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("video/*");
        startActivityForResult(i, 123);
    }
    // http://stackoverflow.com/questions/3853783/max-duration-for-capture-video-on-android
    // For your information it does not run on HTC devices. welcome to the android world.
    //
    private void captureVideo()
    {
        Intent i = new Intent();
        i.setAction(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        i.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
        i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // 1 = high quality
        startActivityForResult(i, VIDEO_CAPTURE);
    }

    private void attachPhoto()
    {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, 22222);
    }

    boolean doit = false;
    boolean toggle = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == VIDEO_CAPTURE)
            {
                //read data where it is saved
                Uri uri = data.getData();
//                printInfo(uri);
            }
            if(requestCode == 123)
            {
                /**
                 *  Video Cropper activity
                 *  Launch the activity to crop a video
                 */
//                Intent cropper = new Intent();
//                cropper.setClass(getApplicationContext(), VideoCropperActivity.class);
//                cropper.putExtra(VideoCropperActivity.URI_TAG, data.getData().toString());
//                startActivity(cropper);

                printInfo(data.getData());

                Uri uri = data.getData();

//                preview.setVisibility(View.VISIBLE);
//                /**
//                 *  Supported formats;
//                 *  H.263
//                    H.264 AVC
//                    MPEG-4 SP
//                    VP8
//                 */
//
//                MediaMetadataRetriever mr = new MediaMetadataRetriever();
//                mr.setDataSource(this, uri);
//                String type = mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
//
//                MediaController controller = new MediaController(this);
//                preview.setVideoURI(uri);
//                controller.setAnchorView(preview);
//                preview.setMediaController(controller);
//                preview.start();
//                preview.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
//                {
//                    @Override
//                    public void onCompletion(MediaPlayer mp)
//                    {
//                        Toast.makeText(VideoIntentActivity.this, "gotovo", Toast.LENGTH_LONG).show();
//                    }
//                });

            }

            if(requestCode == 22222)
            {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bmp = BitmapFactory.decodeFile(picturePath);
                preview.setBackgroundDrawable(new BitmapDrawable(bmp));
            }
        }
        else
        {
            Toast.makeText(this, "Something failed.", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String extractMediaPath(Uri uri)
    {
        String[] filePathColumn = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(
                uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String mediaPath = cursor.getString(columnIndex);
        cursor.close();

        return mediaPath;
    }
    private void printInfo(Uri path)
    {
        MediaMetadataRetriever mmdr = new MediaMetadataRetriever();
        mmdr.setDataSource(this, path);
        ArrayList<String> infos = new ArrayList<String>();

        infos.add("Bitrate - " + mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)); //9149356
        infos.add("Duration - " + mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)); //11522
        infos.add("Has audio - " + mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO)); //yes
        infos.add("Has video - " + mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)); //yes
        infos.add("Video type - " + mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)); //video/3gpp
        infos.add("Width - " + mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)); //1280
        infos.add("Height - " + mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)); //720
        infos.add("Rotation - " + mmdr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)); //90
        String p = extractMediaPath(path);
        File f = new File(p);

        double s = f.length(); // Bytes? -> yes
        s = s / (1024 * 1024);

        // Calculate size
        float size = Float.valueOf(infos.get(0).split(" ")[2]) / 1000.0F;
        size = size * Float.valueOf(infos.get(1).split(" ")[2]);
        size = size / (8 * 1024 * 1024);

        infos.add("Size - " + String.valueOf(size) + " MB");
        infos.add("Given length - " + String.valueOf(s) + " MB");

        mmdr.release();

        StringBuilder builder = new StringBuilder();
        for(String line : infos)
        {
            builder.append(line).append("\n");
        }

        text.setText(builder.toString());
    }
}

