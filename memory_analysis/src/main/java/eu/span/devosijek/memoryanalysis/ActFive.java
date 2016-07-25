package eu.span.devosijek.memoryanalysis;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ActFive extends BaseActivity
{
    static LeakClass variable = null;

    CustomAdapter customAdapter;

    class LeakClass
    {
//        String largeStringVariable;
//        Bitmap largeBitmapObject;
//
//        LeakClass()
//        {
//            StringBuilder sb = new StringBuilder();
//
//            int i = 100000;
//            for(int j = 0; j < i; j++)
//            {
//                sb.append(i);
//            }
//
//            largeStringVariable = sb.toString();
//            largeBitmapObject = Bitmap.createBitmap(1500, 2000, Bitmap.Config.ARGB_8888);
//        }

        void doSomething(){
            System.out.println("Print out.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("Testing", this.getClass().getSimpleName() + " onCreate.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_five);

//        variable = new LeakClass();
//        doWithList();

        doContextLeak();
        enableNextActivity();
        loadImage();
    }

//    @Override
//    protected void onDestroy()
//    {
//        super.onDestroy();
//
//        variable = null;
//    }

//    private void doWithList()
//    {
//        int count = 120000;
//        ArrayList<POJO> data = new ArrayList<>();
//        POJO piece;
//
//        for(int i = 0; i < count; i++)
//        {
//            piece = new POJO(i, i, i);
//            data.add(piece);
//        }
//
//        customAdapter = new CustomAdapter(this, data);
//
//        ListView list = (ListView) findViewById(R.id.lista);
//        list.setAdapter(customAdapter);
//    }

//    private void doContextLeak()
//    {
//        StringBuilder sb = new StringBuilder();
//
//        final int count = 20000;
//
//        for(int j = 0; j < count; j++)
//        {
//            sb.append(j);
//        }
//
//        largeStringVariable = sb.toString();
//    }

    private void enableNextActivity()
    {
        findViewById(R.id.actnbtn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Intent i = new Intent();
//                if(useApplicationContext)
//                    i.setClass(getApplicationContext(), ActSix.class);
//                else
//                    i.setClass(ActFive.this, ActSix.class);
//                startActivity(i);

                callNext(ActSix.class);
            }
        });
    }
}
