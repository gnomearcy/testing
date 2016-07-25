package hr.span.tmartincic.testing.esspresso.adapterview;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hr.span.tmartincic.testing.esspresso.R;


public class ActivityAdapterView extends Activity
{
    private static final String tag = "EspressoTesting";
    ListView listContent;
    CheckedAdapter<String> positionAdapter;
    private String[] data = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine","Ten"};

    public int listContentSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(tag, "onCreate - ActivityAdapterView");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_adapter_view);

        //invoke action bar
        ActionBar actionBar = getActionBar();
        actionBar.show();

        //copy array to list
        List<String> dataList = new ArrayList<>();
        Collections.addAll(dataList, data);

        //listview configuration
        listContent = (ListView) findViewById(R.id.adapterview_list);
        listContent.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listContent.setMultiChoiceModeListener(mcml);
//        positionAdapter = new PositionAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        positionAdapter = new CheckedAdapter<>(this, android.R.layout.simple_list_item_1, (ArrayList<String>) dataList);
        listContent.setAdapter(positionAdapter);
//        listContentSize = listContent.getChildCount(); //returns 0 because the list view is not yet drawn on the screen
//        Log.d(tag, "broj djece - " + listContentSize);

        /** Hack to get the information about amount of children the list view has.
         *  This information is needed in the test's "setUp" method.
         * */
        listContent.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                listContentSize = view.getChildCount(); //10
                Log.d(tag, "Visible child count " + view.getChildCount());
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(tag, "onResume - ActivityAdapterView");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_activity_adapter_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.how_many_left_in_list:
                //Log how many items are currently in the list
                int listSize = listContent.getChildCount();
                Log.d(tag, "ListView current size - " + String.valueOf(listSize));
                break;
        }
        return true;
    }

    AbsListView.MultiChoiceModeListener mcml = new AbsListView.MultiChoiceModeListener()
    {
        int numberOfChecked = 0;

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
        {
            Log.d(tag, "OnItemcheckedstatechanged - position - " + String.valueOf(position));
            if(checked)
                numberOfChecked++;
            else
                numberOfChecked--;
            positionAdapter.setChecked(position, checked);
            mode.setTitle(numberOfChecked + " selected");
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
            Log.d(tag, "onCreateActionMode");
            getMenuInflater().inflate(R.menu.contextual_menu, menu);
            numberOfChecked = 0;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {
            Log.d(tag, "onPrepareActionMode");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {
            Log.d(tag, "onActionItemClicked");

            int id = item.getItemId();

            switch(id)
            {
                case R.id.cab_delete:
                    numberOfChecked = 0;
                    Log.d(tag, "Clicked on delete CAB item");
                    positionAdapter.deleteChecked();
//                    mode.setTitle("");
                    mode.finish();
                    break;
            }

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode)
        {
            Log.d(tag, "OnDestroyActionMode");
            //inform the adapter to reset it's internal "checked" map
            positionAdapter.clearSelection();
            numberOfChecked = 0;
        }
    };



}
