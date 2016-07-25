package dev.toma.eu.smali;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

/**
 *  Custom code just to see how it decompiled to .smali format.
 *  This was used in RoomAssistant project to modify the source code of SuperSu app.
 */
public class MainActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String p = "eu.chainfire.supersu_preferences";
        SharedPreferences prefs = getSharedPreferences(p, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String key = "stringkaobroj";
        String value = "-2123";

        editor.putString(key, value);

        editor.commit();
    }
}
