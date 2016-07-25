package eu.span.devosijek.delete_package;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity
{
    static final String tag = "RAAService";

    private FileOutputStream fosInstall;
    private FileOutputStream fosUninstall;
    private File targetInstall;
    private File targetUninstall;
    private String install = "/install.txt";
    private String uninstall = "/uninstall.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetInstall = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), install);
        targetUninstall = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), uninstall);
        try
        {
            fosInstall = new FileOutputStream(targetInstall);
            fosUninstall = new FileOutputStream(targetUninstall);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

        findViewById(R.id.deletepackage).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                deletePackage();
                try
                {
                    startDaemon();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.installpackage).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    installPackage();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deletePackage()
    {
        Log.d(tag, "delete package method");
        String command = "adb shell pm uninstall eu.span.devosijek.room_assistant_apk";
        try
        {
//            Process su = Runtime.getRuntime().exec(new String[]{"su", "-c", "uninstall eu.span.devosijek.room_assistant_apk"});
            Process su = Runtime.getRuntime().exec(command);
            BufferedReader r = new BufferedReader(new InputStreamReader(su.getInputStream()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            String out = total.toString();
            Log.d(tag, "Out - " + out);

            su.waitFor();
            Log.d(tag, "Uninstall successful.");

        }
        catch(IOException e)
        {
            e.printStackTrace();
            Log.d(tag, "Uninstall failed.");

        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            Log.d(tag, "Uninstall interupted.");
        }
    }

    private void installPackage() throws IOException
    {
        Log.d(tag, "install package method");
        /** Any other command after adb kill-server will trigger new instance of adb server */

        fosInstall.write("Command - ".getBytes());
        fosInstall.write("\r\n".getBytes());

        try
        {
            Process su = Runtime.getRuntime().exec("su -c pm install /storage/emulated/0/roomassistant.apk");

//            os.write("adb version".getBytes());
            /** Use OutputStream to write commands to the process object retrieved from exec() command */

            BufferedReader r = new BufferedReader(new InputStreamReader(su.getInputStream()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            String out = total.toString();
            Log.d(tag, "Out - " + out);

            fosInstall.write("\r\n".getBytes());
            fosInstall.write("-------".getBytes());
            fosInstall.write("\r\n".getBytes());
            fosInstall.write(out.getBytes());
            fosInstall.write("\r\n".getBytes());
            fosInstall.write("-------".getBytes());
            fosInstall.write("\r\n".getBytes());

            int installCode = su.waitFor();
            Log.d(tag, "Install code - " + installCode);

            fosInstall.write("End of method".getBytes());
        }
        catch(IOException e)
        {
            e.printStackTrace();
            fosInstall.write("\r\n".getBytes());
            fosInstall.write("Install failed".getBytes());
            fosInstall.write("\r\n".getBytes());
            fosInstall.write(e.getMessage().getBytes());
            Log.d(tag, "Install failed.");

        }
        catch(InterruptedException e)
        {
            fosInstall.write("\r\n".getBytes());
            fosInstall.write("Install interrupted.".getBytes());
            fosInstall.write("\r\n".getBytes());
            fosInstall.write(e.getMessage().getBytes());
            e.printStackTrace();
            Log.d(tag, "Install interupted.");
        }
    }

    private void startDaemon() throws IOException
    {
        Log.d(tag, "install package method");
        String command = "adb shell pm install /storage/emulated/0/roomassistant.apk";
        String cmdKillDaemon = "adb kill-server";
        String cmdStartDaemon = "adb -P 5038 start-server";

        fosInstall.write("Command - ".getBytes());
        fosInstall.write("\r\n".getBytes());
        fosInstall.write(command.getBytes());

        try
        {
//            Process su = Runtime.getRuntime().exec(new String[]{"su", "-c", "uninstall eu.span.devosijek.room_assistant_apk"});
//            Process kill = Runtime.getRuntime().exec(cmdKillDaemon);
//            int killCode = kill.waitFor();
//            Log.d(tag, "Kill code - " + killCode);
//
//            Process start = Runtime.getRuntime().exec(cmdStartDaemon);
//            int startCode = start.waitFor();
//            Log.d(tag, "Start code - " + startCode);
//
//            Thread.sleep(5000);

            Process su = Runtime.getRuntime().exec(command);
            BufferedReader r = new BufferedReader(new InputStreamReader(su.getInputStream()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            String out = total.toString();
//            Log.d(tag, "Out - " + out);

            fosInstall.write("\r\n".getBytes());
            fosInstall.write(out.getBytes());
            fosInstall.write("\r\n".getBytes());
            fosInstall.write("\r\n".getBytes());

            int installCode = su.waitFor();
            Log.d(tag, "Install successful. Install code - " + installCode);

            fosInstall.write("End of method".getBytes());

//            Thread.sleep(4000);
//            Process retry = Runtime.getRuntime().exec(command);
//            int retryCode = retry.waitFor();
//            Log.d(tag, "Install successful. Retry code - " + retryCode);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            fosInstall.write("\r\n".getBytes());
            fosInstall.write("Install failed".getBytes());
            fosInstall.write("\r\n".getBytes());
            fosInstall.write(e.getMessage().getBytes());
            Log.d(tag, "Install failed.");

        }
        catch(InterruptedException e)
        {
            fosInstall.write("\r\n".getBytes());
            fosInstall.write("Install interrupted.".getBytes());
            fosInstall.write("\r\n".getBytes());
            fosInstall.write(e.getMessage().getBytes());
            e.printStackTrace();
            Log.d(tag, "Install interupted.");
        }
    }
}
