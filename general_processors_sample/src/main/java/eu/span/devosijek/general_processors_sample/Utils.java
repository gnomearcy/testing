package eu.span.devosijek.general_processors_sample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import eu.span.devosijek.general_processors_api.Utility;

@Utility
public class Utils
{
    private static final String dateFormat = "dd'.'MM'.'yyyy' 'HH':'mm";
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static final int IMAGE_FOR_WEB_THRESHHOLD = 1280;

    public static boolean isFileOnDevice(String path)
    {
        File file = new File(path);
        return file.exists();
    }

    public static float convertDpToPixel(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     *  Converts the image on a given filepath into
     *  byte array. Byte array is manually written into
     *  multipart request body.
     *  Image is decoded with proper orientation, in JPG format
     *  with best quality.
     *
     *  @param filepath Absolute path of the image on external storage
     */
    public static byte[] convertForWeb(String filepath)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        BitmapFactory.decodeFile(filepath, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        int sampleSize = 1;
        int higher;

        if(imageWidth >= imageHeight)
        {
            higher = imageWidth;
        }
        else
        {
            higher = imageHeight;
        }

        while(higher > IMAGE_FOR_WEB_THRESHHOLD)
        {
            sampleSize *= 2;
            higher = higher / 2;
        }

        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        options.inPreferQualityOverSpeed = true;

        Matrix matrix = setRotation(filepath);

        //If converting the image for API Call results in OOM error
        //return null value which will manifest as an "error happened, try again"
        //situation to the end user.
        try
        {
            Bitmap bmp = BitmapFactory.decodeFile(filepath, options);
            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bmp.recycle();

            return bos.toByteArray();
        }
        catch(OutOfMemoryError exception)
        {
            return null;
        }
    }

    /**
     * Decodes an image on a given filepath into
     * bitmap with desired width and height.
     *
     * @param targetW width of the image view in DP
     * @param targetH height of the image view in DP
     * @param filepath absolute file path to the image on external storage
    */
    public static Bitmap convertForPreview(String filepath, float targetW, float targetH)
    {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inPreferredConfig = Bitmap.Config.RGB_565; //2 bytes per pixel, no alpha
        BitmapFactory.decodeFile(filepath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scale = 1;

        if ((targetW > 0) || (targetH > 0))
        {
            scale = Math.min(photoW/(int)targetW, photoH/(int)targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scale;

        Bitmap result = BitmapFactory.decodeFile(filepath, bmOptions);

        if(result == null)
        {
            return null;
        }

        Matrix matrix = setRotation(filepath);
        result = Bitmap.createBitmap(result, 0, 0, result.getWidth(), result.getHeight(), matrix, true);

        return result;
    }

    /**
     * Function which reads the properties
     * of an image on a given filepath.
     *
     * @return matrix holding rotation value to
     * rotate the image to correct position
     */
    private static Matrix setRotation(String filepath)
    {
        Matrix matrix = new Matrix();
        ExifInterface exif = null;

        try
        {
            exif = new ExifInterface(filepath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if(orientation == ExifInterface.ORIENTATION_ROTATE_90)
            {
                matrix.postRotate(90);
            }
            else if(orientation == ExifInterface.ORIENTATION_ROTATE_180)
            {
                matrix.postRotate(180);
            }
            else if(orientation == ExifInterface.ORIENTATION_ROTATE_270)
            {
                matrix.postRotate(270);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return matrix;
    }

    public static Bitmap convertForPreviewResource(Context context, int resourceId, float targetW, float targetH)
    {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resourceId, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 1;

        if ((targetW > 0) || (targetH > 0))
        {
            scaleFactor = Math.min(photoW/(int)targetW, photoH/(int)targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeResource(context.getResources(), resourceId, bmOptions);
    }

    public static void deleteFromDevice(Context context, String filepath, boolean showMessage)
    {
        if(filepath != null)
        {
            File file = new File(filepath);
            boolean deleted = file.delete();

            if(showMessage)
            {
                if(deleted)
                {
                    Toast.makeText(context,
                            context.getString(R.string.hello_world),
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,
                            context.getString(R.string.hello_world),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     *  Returns the public Pictures directory on external storage
     */
    private static File getAlbumDir(Context context)
    {
        File storageDir = null;

        if (hasSdCard())
        {
            storageDir = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES),
                    context.getString(R.string.hello_world));

            if (storageDir != null)
            {
                if (!storageDir.mkdirs())
                {
                    if (!storageDir.exists())
                    {
                        return null;
                    }
                }
            }
        }

        return storageDir;
    }

    public static boolean hasSdCard()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     *  Creates a new File file on external storage where a new picture is to be saved.
     */
    public static File createImageFile(Context context) throws IOException
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = Utils.getAlbumDir(context);
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }


    public static void showMessage(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isConnectingToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if(info != null)
            {
                for(NetworkInfo networkInfo : info)
                {
                   if(networkInfo.isConnectedOrConnecting())
                   {
                       return true;
                   }
                }
            }
        }

        return false;
    }

    public static void saveToGallery(Bitmap bmp, File filepath)
    {
        OutputStream outStream = null;

        try
        {
            outStream = new FileOutputStream(filepath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(outStream != null)
                {
                    outStream.flush();
                    outStream.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void saveToGallery(Bitmap bmp, String filepath)
    {
        saveToGallery(bmp, new File(filepath));
    }
}
