package eu.span.devosijek.generics.test3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eu.span.devosijek.generics.R;

public class ActivityBase extends AppCompatActivity
        implements InterfaceImpl
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_test3);

        //Not supported until Java 8
//        InterfaceImpl.method();

        Methods ime = Methods.IS_CONNECTING_TO_INTERNET;

    }
}
