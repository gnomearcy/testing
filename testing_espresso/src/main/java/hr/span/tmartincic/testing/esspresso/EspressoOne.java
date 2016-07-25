package hr.span.tmartincic.testing.esspresso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EspressoOne extends Activity
{
    private static final String tag = "EspressoTesting";

    Button btn;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(tag, "EspressoOne - onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso_one);

        btn = (Button) findViewById(R.id.espressoone_btn);
        btnStart = (Button) findViewById(R.id.espressoone_starttwo_btn);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(tag, "EspressoOne - change text onClick listener");
                TextView textView = (TextView) findViewById(R.id.espressoone_tv);
                EditText editText = (EditText) findViewById(R.id.espressoone_et);

                String text = editText.getText().toString();
                textView.setText(text);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(tag, "EspressoOne - start activity onClick listener");

                startActivity(new Intent(EspressoOne.this, EspressoTwo.class));
            }
        });
    }
}
