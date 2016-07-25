package eu.span.devosijek.memoryanalysis;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewRobotoMedium extends TextView
{
    public TextViewRobotoMedium(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        createFont();
    }

    private void createFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Medium.ttf");
        setTypeface(font);
    }
}
