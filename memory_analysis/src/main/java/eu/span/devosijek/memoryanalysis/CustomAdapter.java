package eu.span.devosijek.memoryanalysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter
{
    Context context;
    ArrayList<POJO> data;

    CustomAdapter(Context context, ArrayList<POJO> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public POJO getItem(int i)
    {
        return data.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.listitem, null);

        POJO obj = getItem(i);

        TextView title = (TextView) v.findViewById(R.id.title);
        TextView description = (TextView) v.findViewById(R.id.description);
        TextView subtext = (TextView) v.findViewById(R.id.subtext);

        title.setText(obj.Title);
        description.setText(obj.Description);
        subtext.setText(obj.Subtext);

        return v;
    }
}
