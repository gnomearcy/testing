package hr.span.processor.dependency_injection_butterknife.list_magic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.ButterKnife;
import hr.span.processor.dependency_injection_butterknife.R;

public class ButterAdapter extends BaseAdapter
{
    private static final String[] CONTENTS =
            "The quick brown fox jumps over the lazy dog".split(" ");

    private final LayoutInflater inflater;

    public ButterAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override public int getCount() {
        return CONTENTS.length;
    }

    @Override public String getItem(int position) {
        return CONTENTS[position];
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return null;
    }
}