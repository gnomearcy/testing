package hr.span.processor.dependency_injection_butterknife.list_magic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTouch;
import hr.span.processor.dependency_injection_butterknife.R;

public class ButterListAct extends ActionBarActivity
{
    ListView list;

    ButterAdapter butterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_list);
        ButterKnife.bind(this);

        list.addHeaderView(constructHeader());
        butterAdapter = new ButterAdapter(this);
        list.setAdapter(butterAdapter);

        ButterPOJO.doSomeWork(new OnResult()
        {
            @Override
            public void onResult(Integer number, boolean success)
            {

            }
        });

        list.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

    }

    private View constructHeader()
    {
        TextView v = (TextView) View.inflate(this, R.layout.list_header, null);
        v.setText("List header");
        return v;
    }

    @OnItemClick(R.id.butterList)
    public void listItemClick(AdapterView<?> parent, View v, int position)
    {
        Toast.makeText(this, "Clicked on pos " + String.valueOf(position) + " on view of type " + v.getClass().getSimpleName(), Toast.LENGTH_LONG).show();

        YoYo.with(Techniques.StandUp)
                .duration(1500)
                .playOn(v);
    }
    @OnTouch(R.id.butterList)
    public boolean listOnTouch(View v, MotionEvent event)
    {
        return false;
    }
}
