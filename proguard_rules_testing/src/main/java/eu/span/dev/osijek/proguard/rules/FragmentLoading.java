package eu.span.dev.osijek.proguard.rules;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLoading extends BaseFragment
{
    Handler uiHandler;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(uiHandler == null)
            uiHandler = new Handler(getActivity().getMainLooper());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.loading_fragmnet, container, false);
        uiHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                MainActivity activity = (MainActivity) getActivity();
                if(activity != null)
                    activity.fromLoadingToDetails();
            }
        }, 2000);
        return view;
    }
}
