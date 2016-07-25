package eu.span.dev.osijek.di.scoping.data.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.span.dev.osijek.di.scoping.data.model.Repository;

public class AdapterRepositoriesList extends ArrayAdapter<Repository>
{
    private LayoutInflater inflater;

    public AdapterRepositoriesList(Context context, List<Repository> objects)
    {
        super(context, 0, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        HolderRepository holder;

        if(convertView == null)
        {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new HolderRepository(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (HolderRepository) convertView.getTag();
        }

        Repository repository = getItem(position);
        holder.text1.setText(repository.name);

        return convertView;
    }

    static class HolderRepository
    {
        @Bind(android.R.id.text1)
        TextView text1;

        // Need a constructor to use ButterKnife
        public HolderRepository(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
