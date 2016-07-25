package hr.span.processor.androidannotations.list_view_aa_style;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import hr.span.processor.androidannotations.R;

@EViewGroup(R.layout.customview_layout)
public class CustomView extends LinearLayout
{
    @ViewById
    TextView tvfirstname;
    @ViewById
    TextView tvlastname;

    public CustomView(Context context)
    {
        super(context);
    }

    public void bind(Person person)
    {
        tvfirstname.setText(person.name);
        tvlastname.setText(person.lastname);
    }
}
