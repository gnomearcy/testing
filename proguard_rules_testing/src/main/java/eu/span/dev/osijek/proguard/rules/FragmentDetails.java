package eu.span.dev.osijek.proguard.rules;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class FragmentDetails extends BaseFragment
{
    private static final String KEY = "detailsKey";
    @Bind(R.id.someButton)
    Button btn;
    @Bind(R.id.imageView)
    ImageView view;
    private boolean changed;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.details, container, false);
        ButterKnife.bind(this, view);
        view.setBackground(null);
        view.setBackgroundColor(Color.RED);
        return view;
    }

    @OnClick(R.id.someButton)
    public void click(View view)
    {
        if(view == btn && R.id.someButton == view.getId())
        {
            if(changed)
                return;
            Toast.makeText(getContext(), "Clicked in details", Toast.LENGTH_SHORT).show();
            this.view.setBackgroundResource(R.mipmap.ic_launcher);
            this.view.setBackgroundColor(Color.MAGENTA);
            changed = true;
        }
        else
            throw new IllegalStateException("Unsupported Id - " + view.getId());
    }

    private boolean btnChanged;
    @OnLongClick(R.id.imageView)
    public boolean longClick(View view)
    {
        if(btnChanged)
        {
            btn.setText("Changed");
            btnChanged = !btnChanged;
        }
        else
        {
            btn.setText("Not changed");
            btnChanged = !btnChanged;
        }

        return true;
    }

    public static FragmentDetails newInstance()
    {
        FragmentDetails f = new FragmentDetails();
        Bundle args = new Bundle();
        args.putString(KEY, "Freshly created.");
        f.setArguments(args);
        return f;
    }
}
