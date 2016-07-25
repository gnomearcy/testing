package eu.span.dev.osijek.customviews.sliding_view_from_side;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import eu.span.dev.osijek.customviews.R;

public class TestCase2_DimmedSandwich extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_case2__dimmed_sandwich);

        F1 f1 = new F1();
        F2 f2 = new F2();
        getFragmentManager().beginTransaction().add(R.id.sandwhichlayout_bottom_child, f1, "f1")
                .add(R.id.sandwhichlayout_top_child, f2, "f2").commit();
    }

    public static class F1 extends Fragment
    {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.testcase2_fragment1, null);
        }
    }

    public static class F2 extends Fragment
    {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.testcase2_fragment2, null);
        }
    }
}
