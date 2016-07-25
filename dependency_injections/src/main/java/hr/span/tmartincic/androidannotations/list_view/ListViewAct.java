package hr.span.processor.androidannotations.list_view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.span.processor.androidannotations.R;

@EActivity(R.layout.activity_list_view)
public class ListViewAct extends Activity
{
    @InstanceState
    String savedText;
    @ViewById
    EditText editText;
    @ViewById
    Button saveBtn;
    @ViewById
    Button btnAddToList;
    @ViewById
    ListView customList;

    MyAdapter myAdapter;
    List<Integer> savedPositions;

    @Extra
    String message;

    @Click(R.id.saveBtn)
    void saveBtnClick()
    {
//        savedText = editText.getText().toString();

        ArrayList<String> stringList = new ArrayList<String>();
        Collections.addAll(stringList, "jedan", "dva", "tri");

        Intent i = new Intent();
        i.setClass(this, StartedActivity_.class);
        i.putExtra("someExtra", "something");
        i.putStringArrayListExtra("someList", stringList);
        startActivity(i);

        StartedActivity_.intent(this)
                .someExtra("from builder")
                .someList(stringList)
                .start();
    }

    @AfterViews
    void initAdapter()
    {
        if(savedText != null)
        {
            Log.d("AndroidAnnotations", "Saved text not null - " + savedText);
        }
        else
        {
            Log.d("AndroidAnnotations", "Saved text is null");
        }

        getActionBar().show();
        savedPositions = new ArrayList<Integer>();

        Log.d("AndroidAnnotations", "initAdapter method");
        myAdapter = new MyAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        customList.setAdapter(myAdapter);
        customList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        customList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener()
        {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
            {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu)
            {
                mode.getMenuInflater().inflate(R.menu.menu_list_view, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu)
            {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item)
            {
                switch(item.getItemId())
                {
                    case R.id.action_delete:
                        //nothing
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode)
            {

            }
        });
    }

    @Click(R.id.btnAddToList)
    void btnAddToListClick()
    {
        Log.d("AndroidAnnotations", "dodajem nesto");
        myAdapter.addItem("dodano");
    }

//    @ItemLongClick(R.id.customList)
//    void customListLongClick()
//    {
//        Toast.makeText(this, "list item long click", Toast.LENGTH_SHORT).show();
//    }

    @ItemClick(R.id.customList)
    void customListItemClick(String type)
    {
        Toast.makeText(this, "list itemclick - " + type, Toast.LENGTH_SHORT).show();
    }

    @ItemSelect(R.id.customList)
    void customListItemSelect(boolean isSelected, String object)
    {
        Log.d("AndroidAnnotations", "item select triggered for object - " + object);
    }

    static class MyAdapter extends BaseAdapter
    {
        private List<String> list;
        private Context context;
        private int resource;

        public MyAdapter(Context context, int resource, List<String> list)
        {
            this.context = context;
            this.resource = resource;
            this.list = list;
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public String getItem(int position)
        {
            return list.get(position);
        }

        public void addItem(String object)
        {
            list.add(object);
            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        public void removeItem(int position)
        {
            list.remove(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(resource, null);

            ((TextView)v).setText(getItem(position));

            return v;
        }
    }
}
