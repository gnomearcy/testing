package eu.span.devosijek.generics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import eu.span.devosijek.generics.test3.AbstraktnaImpl1;
import eu.span.devosijek.generics.test3.Args1;


public class MainActivity extends AppCompatActivity
{
    final String tag = "Generics";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.generics_textview).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean result = GenericClass.check(GenericClass.Methods.METHOD_1);
                Log.d(tag, result.toString());

                Class<Void> result2 = GenericClass.check(GenericClass.Methods.METHOD_2);

                Log.d(tag, String.valueOf(result2));

                VanjskaKlasa vk = new VanjskaKlasa();

                Class<GenericInterface.ExtendedClass1> klasa = GenericInterface.ExtendedClass1.class;
                vk.vanjskaMetoda(klasa);
            }
        });
    }

    /** Construct a generic method in another class and check if the method can return two possible values */
    private static class GenericClass
    {
        public static <T> T check(Methods method, Object...methodParams)
        {
            T result;

            switch(method)
            {
                case METHOD_1:
                {
                    result = (T) methodBoolean();
                    return result;
                }
                case METHOD_2:
                {
                    result = (T) methodVoid();
                    return result;
                }
            }

            return null;
        }

        private static Boolean methodBoolean()
        {
            return true;
        }

        private static Class<Void> methodVoid()
        {
            return Void.class;
        }

        public enum Methods
        {
            METHOD_1,
            METHOD_2
        }
    }
}
