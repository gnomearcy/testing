package hr.span.tmartincic.testing.esspresso.adapterview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class CheckedAdapter<T> extends BaseAdapter
{
    private static final String tag = "EspressoTesting";
    private Map<Integer, Boolean> checkedPositions;
    private List<T> data;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public CheckedAdapter(Context context, int resource, ArrayList<T> objects)
    {
        checkedPositions = new HashMap<>();
        data = objects;
        this.context = context;
        this.resource = resource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setChecked(int position, boolean value)
    {
        if(value == false)
            this.checkedPositions.remove(position);
        else
            this.checkedPositions.put(position, value);
        this.notifyDataSetChanged();
    }

    public boolean getChecked(int position)
    {
        try
        {
            Boolean isChecked = this.checkedPositions.get(position);
            if(isChecked == null)
                return false;
            return true;
        }
        catch (IndexOutOfBoundsException e)
        {
            return false;
        }
    }

    public void clearSelection()
    {
        this.checkedPositions.clear();
        notifyDataSetChanged();
    }

    public void deleteChecked()
    {
        Log.d(tag, "-----------------------------------");
        //assume this array is sorted in ascending order - it is not..
        Object[] validPoss = checkedPositions.keySet().toArray();
        validPoss = sortMethod(validPoss);

//        Log.d(tag, String.format("Positions to remove - %s, %s, %s", validPoss[0], validPoss[1], validPoss[2]));
        int validPossSize = validPoss.length;
        int currValidPoss = 0;
        int iteratorPos = 0;

        Iterator<T> dataIterator = data.iterator();

        while(dataIterator.hasNext())
        {
            //necessary for the iterator.remove() method call
            //return value is ignored
            dataIterator.next();

            if(currValidPoss == validPossSize)
            {
                break;
            }

            if(iteratorPos == (Integer) validPoss[currValidPoss]) //0 != 2
            {
                currValidPoss++;
                dataIterator.remove();
                Log.d("EspressoTesting", "Removing item from position - " + iteratorPos);
            }

            iteratorPos++;
        }

        //notify the adapter to reflect changes in the dataset
        clearSelection();
        Log.d(tag, "-----------------------------------");
    }

    private Object[] sortMethod(Object[] toSort)
    {
        ArrayList<Object> list = new ArrayList<>();

        Collections.addAll(list, toSort);

        Comparator<Object> comp = new Comparator<Object>()
        {
            @Override
            public int compare(Object lhs, Object rhs)
            {
                if((int)lhs > (int) rhs)
                {
                    return 1;
                }
                else if((int)lhs < (int) rhs)
                {
                    return -1;
                }
                else return 0;
            }
        };

        Collections.sort(list, comp);
        return list.toArray();
    }

    @Override
    public int getCount()
    {
        return data.size();
    }

    @Override
    public T getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = inflater.inflate(resource, null);
        TextView text = (TextView) v.findViewById(android.R.id.text1);
        T item = getItem(position);
        text.setText((String)item);

        if(getChecked(position))
        {
            v.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_light));
        }
        else
            v.setBackgroundColor(context.getResources().getColor(android.R.color.background_light));

        return v;
    }
}