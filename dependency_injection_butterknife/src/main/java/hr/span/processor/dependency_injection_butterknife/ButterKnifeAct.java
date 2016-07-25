package hr.span.processor.dependency_injection_butterknife;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.List;

import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ButterKnifeAct extends Activity
{
    List<View> images;

    @BindColor(R.color.red) int redColor;

    private ButterKnife.Action<View> fadeInAnimation = new ButterKnife.Action<View>(){
        @Override
        public void apply(View view, int index){
            Animation anim = AnimationUtils.loadAnimation(ButterKnifeAct.this, R.anim.fadeinout);
            view.startAnimation(anim);
            Log.d("ButterKnife", "Inside fade action");

        }
    };

    @OnClick(R.id.btnStartAnim)
    public void startAnimation(View v)
    {
        Log.d("ButterKnife", "v is type " + v.getClass().toString());
        Log.d("ButterKnife", "In start animation click callback");
        ButterKnife.apply(images, fadeInAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        ButterKnife.bind(this);

        Log.d("Rezultat", "Crvena boja -> " + String.valueOf(redColor));
    }
}
