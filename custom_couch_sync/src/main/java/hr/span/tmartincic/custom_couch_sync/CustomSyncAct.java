package hr.span.tmartincic.custom_couch_sync;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class CustomSyncAct extends ActionBarActivity
{
//    private static final int portNumber = 46267;
//    private static final String apiurl = "http://localhost:" + portNumber + "/api/";
    Button triggerBtn;
    ListView productList;
    Button textBtn;
    private static final String tag = "CustomSyncTag";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_sync);
        triggerBtn = (Button) findViewById(R.id.triggerButton);
        productList = (ListView) findViewById(R.id.productList);
        textBtn = (Button) findViewById(R.id.textButton);

        /**Testing*/

        List<Product> items = new ArrayList<Product>();
        items.add(new Product(1, "tomo", "android", 3.05F));
        items.add(new Product(2, "đuro", "bla bla bla", 1.0F));
        ProductAdapter pa = new ProductAdapter(this, items);
        productList.setAdapter(pa);

        triggerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                API.getAllProducts(new OnResult<ArrayList<Product>>()
                {
                    @Override
                    public void onResult(ArrayList<Product> result)
                    {
//                        Log.d(tag, "CustomSyncAct - onResult callback - I am here");
//                        ProductAdapter pa = new ProductAdapter(CustomSyncAct.this, result);
//                        productList.setAdapter(pa);
                    }
                });
            }
        });

        textBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                API.getText(new OnResult<Text>()
                {
                    @Override
                    public void onResult(Text result)
                    {
                        Toast.makeText(CustomSyncAct.this, "Result - " + result.SomeText, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private class ProductAdapter extends BaseAdapter
    {
        List<Product> products;
        Context context;
        LayoutInflater inflater;

        public ProductAdapter(Context context, List<Product> items)
        {
            this.context = context;
            this.products = items;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount()
        {
            return products.size();
        }

        @Override
        public Product getItem(int position)
        {
            return products.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View v = inflater.inflate(R.layout.list_item_product, null);
            TextView id = (TextView) v.findViewById(R.id.product_id);
            TextView name = (TextView) v.findViewById(R.id.product_name);
            TextView category = (TextView) v.findViewById(R.id.product_category);
            TextView price = (TextView) v.findViewById(R.id.product_price);

            Product product = getItem(position);

            id.setText(String.valueOf(product.Id));
            name.setText(product.Name);
            category.setText(product.Category);
            price.setText(String.valueOf(product.Price));

            return v;
        }
    }

}
