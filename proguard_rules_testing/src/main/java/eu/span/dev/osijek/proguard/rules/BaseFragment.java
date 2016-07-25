package eu.span.dev.osijek.proguard.rules;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment
{
    @CallSuper
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        Log.d("Testing123", this.getClass().getSimpleName() + " - onAttach");
        return null;
    }

    @Override
    public void onAttach(Context context)
    {
        Log.d("Testing123", this.getClass().getSimpleName() + " - onAttach");
        MainActivity activity = (MainActivity) getActivity();
        if(activity != null)
            activity.onFragmentManipulation();
        super.onAttach(context);
    }

    @Override
    public void onDetach()
    {
        Log.d("Testing123", this.getClass().getSimpleName() + " - onDetach");
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        Log.d("Testing123", this.getClass().getSimpleName() + " - onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d("Testing123", this.getClass().getSimpleName() + " - onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        // TODO remove this
        Log.d("Testing123", this.getClass().getSimpleName() + " - onDestroy");
        super.onDestroy();
    }
}
