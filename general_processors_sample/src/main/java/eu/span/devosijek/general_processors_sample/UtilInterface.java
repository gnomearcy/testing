package eu.span.devosijek.general_processors_sample;

import android.app.Activity;
import android.app.Fragment;

public interface UtilInterface
{
    Call call = new Call();

    class Call
    {
        private final Class<? extends android.app.Activity> CLASS_ACTIVITY = android.app.Activity.class;
        private final Class<android.app.Fragment> CLASS_FRAGMENT = android.app.Fragment.class;
        private final Class<android.support.v4.app.Fragment> CLASS_FRAGMENT_SUPPORT = android.support.v4.app.Fragment.class;

        protected static void showMessage(Object context, String message)
        {
            //Perform check if the @Subscriber annotated class is an Activity or a Fragment
            //If it is, perform the check for context in context-dependant methods. If it's a custom
            //Subscriber throw an exception indicating that the Subscriber is not valid or that
            //the Subscriber should provide a context.

            if(context instanceof Activity)
                Utils.showMessage((Activity) context, message);
            if(context instanceof Fragment)
                if(((Fragment) context).getActivity() != null)
                    Utils.showMessage(((Fragment) context).getActivity(), message);
        }

        private static boolean isConnectingToInternet(Object context)
        {
            if(context instanceof Activity)
                return Utils.isConnectingToInternet((Activity) context);
            if(context instanceof Fragment)
                if(((Fragment) context).getActivity() != null)
                    return Utils.isConnectingToInternet(((Fragment) context).getActivity());
            throw new RuntimeException("Unsupported caller.");
        }
    }
}