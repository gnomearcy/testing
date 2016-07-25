package eu.span.devosijek.demo.updater;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  Implementation of a external Service application which gets called from
 *  updater_demo_app module. The service will uninstall the calling application,
 *  install the new version and launch the installed version.
 *
 *  Both of these applications were used to test this behaviour for RoomAssistant project.
 *
 *  Make sure to have a file called "demo_v11.apk" located in root of the external storage
 *  (directory containing PICTURES, DOWNLOADS and other directories).
 *
 *  Construct "demo_v11.apk" by compiling "updater_demo_app" with a new value displayed in
 *  app layout's TextView, merely to indicate a new version.
 */
public class Updater extends Service
{
    private final static String tag = "Updater";
    private final static String COMMAND_UNINSTALL = "su -c pm uninstall eu.span.devosijek.demo.demo_app";
    private final static String COMMAND_INSTALL = "su -c pm install /storage/emulated/0/demo_v11.apk";

    private static final String APP_MAIN_ACTIVITY = ".DemoActivity";
    private static final String pkg = "eu.span.devosijek.demo.demo_app";

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
//        super.onCreate();
        Log.d(tag, this.getClass().getSimpleName() + " - onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(tag, this.getClass().getSimpleName() + " - onStartCommand");

        try
        {
            uninstallPackage();
            installPackage();
            launch();
        }
        catch(InterruptedException | IOException e)
        {
            e.printStackTrace();
        }

        /**
         *  Recreate the service if it gets killed
         *  Intent can be null in that case, so don't rely on it
         */
        return Service.START_STICKY;
    }

    private void uninstallPackage() throws InterruptedException, IOException
    {
        Log.d(tag, "Uninstalling package...");
        Process uninstallProcess = Runtime.getRuntime().exec(COMMAND_UNINSTALL);


        BufferedReader r = new BufferedReader(
                new InputStreamReader(uninstallProcess.getInputStream()));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }

        String output = total.toString();

        int exitCode = uninstallProcess.waitFor();

        Log.d(tag, "Uninstall message - " + output + " | Exit code - " + exitCode);
    }

    private void installPackage() throws InterruptedException, IOException
    {
        Log.d(tag, "Installing package...");
        Process uninstallProcess = Runtime.getRuntime().exec(COMMAND_INSTALL);

        BufferedReader r = new BufferedReader(
                new InputStreamReader(uninstallProcess.getInputStream()));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }

        String output = total.toString();

        int exitCode = uninstallProcess.waitFor();

        Log.d(tag, "Install message - " + output + " | Exit code - " + exitCode);
    }

    private void launch()
    {
        Log.d(tag, "Starting updated application...");
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setComponent(new ComponentName(pkg, pkg + APP_MAIN_ACTIVITY));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
