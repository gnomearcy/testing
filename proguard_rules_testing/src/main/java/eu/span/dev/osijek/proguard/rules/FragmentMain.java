package eu.span.dev.osijek.proguard.rules;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentMain extends BaseFragment
{
    @Bind(R.id.toggleButton)
    ToggleButton btn1;
    @Bind(R.id.toggleButton2)
    ToggleButton btn2;
    @Bind(R.id.toggleButton3)
    ToggleButton btn3;

    @Inject
    public Calculator calculator;

    private static final String argsKey = "Some string";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.main, container, false);
        ButterKnife.bind(this, view);

        btn1.setBackgroundColor(Color.BLUE);
        btn2.setBackgroundColor(Color.RED);
        btn3.setBackgroundColor(Color.GREEN);

        DaggerComponentMain.builder().moduleMain(new ModuleMain()).build().inject(this);

        Bundle arguments = getArguments();
        String arg = arguments.getString(argsKey);
        btn1.setText(arg);

        return view;
    }

    @OnClick({R.id.toggleButton})
    public void clickBtn1(View view)
    {
        if(view instanceof ToggleButton && R.id.toggleButton == view.getId()){
        }
        else
            throw new IllegalStateException("Unsupported Id");
    }

    @OnClick({
            R.id.toggleButton2,
            R.id.toggleButton3})
    public void clickBtns(View view)
    {
        if(view instanceof ToggleButton && R.id.toggleButton2 == view.getId())
            Toast.makeText(getContext(), "Button 2", Toast.LENGTH_SHORT).show();
        else if(view instanceof ToggleButton && R.id.toggleButton2 == view.getId())
            Toast.makeText(getContext(), "Button 3", Toast.LENGTH_SHORT).show();
        else
            throw new IllegalStateException("Unsupported Id");
    }

    public static FragmentMain newInstance()
    {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(argsKey, "Hello world!");
        fragment.setArguments(args);
        return fragment;
    }
}
