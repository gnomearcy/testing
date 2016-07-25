package roomassistant.devosijek.span.eu.test_camera_capture.take_video;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;

public class VideoHandler
{
    // Given the Uri
    public static void cropVideo(Uri uri)
    {

    }

    public static VideoInfo getVideoInfo(Context context, Uri path)
    {
        MediaMetadataRetriever mr = new MediaMetadataRetriever();
        mr.setDataSource(context, path);
        VideoInfo videoInfo = new VideoInfo();

        videoInfo.setBitrate(Double.valueOf(mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE))); //9149356
        videoInfo.setDuration(Double.valueOf(mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))); //11522
        videoInfo.setHasAudio(mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO)); //yes
        videoInfo.setHasVideo(mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)); //yes
        videoInfo.setVideoType(mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)); //video/3gpp
        videoInfo.setVideoWidth(Integer.valueOf(mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH))); //1280
        videoInfo.setVideoHeight(Integer.valueOf(mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT))); //720

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            videoInfo.setVideoRotation(Integer.valueOf(mr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION))); //90
        }

        mr.release();
        return videoInfo;
    }

    public static class VideoInfo
    {
        private static final int KNOWN_BITRATE = 0x1;
        private static final int KNOWN_WIDTH = 0x2;
        private static final int KNOWN_HEIGHT = 0x4;
        private static final int KNOWN_DURATION = 0x8;

        private int STATE = 0x0;
//        private boolean isCalculable = false;

        private Double bitrate;
        private Double duration;
        private String hasAudio;
        private String hasVideo;
        private String videoType;
        private Integer videoWidth;
        private Integer videoHeight;
        private Integer videoRotation;
        private double videoSize;

        // Readable representation of video information
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder = writeField(builder, "Bitrate", bitrate);
            builder = writeField(builder, "Duration", duration);
            builder = writeField(builder, "Has audio", hasAudio);
            builder = writeField(builder, "Has video", hasVideo);
            builder = writeField(builder, "Video type", videoType);
            builder = writeField(builder, "Video width", videoWidth);
            builder = writeField(builder, "Video height", videoHeight);
            builder = writeField(builder, "Video rotation", videoRotation);
            builder = writeField(builder, "Video size", videoSize);

            return builder.toString();
        }

        private StringBuilder writeField(StringBuilder builder, String name, Object value)
        {
            builder.append(name).append(" - ");
            builder.append(value == null ? "unknown" : value);
            builder.append("\n");
            return builder;
        }

        public boolean isCalculable()
        {
            return STATE == 0xF;
        }

        public void setBitrate(Double bitrate)
        {
            this.bitrate = bitrate;

            if(bitrate != null)
            {
                STATE = STATE | KNOWN_BITRATE;
            }
        }

        public Double getBitrate()
        {
            return bitrate;
        }

        public void setDuration(Double duration)
        {
            this.duration = duration;

            if(duration != null)
            {
                STATE = STATE | KNOWN_DURATION;
            }
        }

        public Double getDuration()
        {
            return duration;
        }

        public void setHasAudio(String hasAudio)
        {
            this.hasAudio = hasAudio;
        }

        public String isHasAudio()
        {
            return hasAudio;
        }

        public void setHasVideo(String hasVideo)
        {
            this.hasVideo = hasVideo;
        }

        public String isHasVideo()
        {
            return hasVideo;
        }

        public void setVideoType(String videoType)
        {
            this.videoType = videoType;
        }

        public String getVideoType()
        {
            return videoType;
        }

        public void setVideoWidth(Integer videoWidth)
        {
            this.videoWidth = videoWidth;

            if(videoWidth != null)
            {
                STATE = STATE | KNOWN_WIDTH;
            }
        }

        public Integer getVideoWidth()
        {
            return videoWidth;
        }

        public void setVideoHeight(Integer videoHeight)
        {
            this.videoHeight = videoHeight;

            if(videoHeight != null)
            {
                STATE = STATE | KNOWN_HEIGHT;
            }
        }

        public Integer getVideoHeight()
        {
            return videoHeight;
        }

        public void setVideoRotation(Integer videoRotation)
        {
            this.videoRotation = videoRotation;
        }

        public Integer getVideoRotation()
        {
            return videoRotation;
        }

        private void setVideoSize(Float videoSize)
        {
            this.videoSize = videoSize;
        }

        // Return the video size in [MB]
        public double getVideoSize()
        {
            if(isCalculable())
            {
                // Calculate
                double duration = getDuration();    // In [ms]
                double bitrate = getBitrate();      // In [bit/s]

                // Convert bitrate from [bit/s] to [bit/ms]
                bitrate = bitrate / 1000.0F;
                double totalBits = (bitrate * duration); // [bit/ms] * [ms] = [bit]

                // Convert to MB
                // To byte
                totalBits = totalBits / 8;
                // To MB
                totalBits = totalBits / ( 1024 * 1024 );

                this.videoSize = totalBits;
            }

            return videoSize;
        }
    }
}
