package hr.span.tmartincic.pizza.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import hr.span.tmartincic.pizza.demo.pizzas.MealFactory;

public class PizzaActivity extends ActionBarActivity
{
    @InjectView(R.id.spinnerId)
    Spinner s;
    @InjectView(R.id.editText)
    EditText et;
    @InjectView(R.id.textViewResult)
    TextView textView;
    @InjectView(R.id.editText2)
    EditText et2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        MealFactory factory = new MealFactory();
//        Meal tiramisu = factory.createMeal(Meal.MealConstants.TIRAMISU);
//        Toast.makeText(this, String.valueOf(tiramisu.getPrice()), Toast.LENGTH_SHORT).show();

        ButterKnife.inject(this);

        String[] names = new String[]{"prvi", "drugi", "treci"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        s.setAdapter(spinnerAdapter);
    }

    public void textChanged(CharSequence sequence)
    {
        Toast.makeText(this, sequence.toString() + " 1", Toast.LENGTH_SHORT).show();
    }

    @OnItemSelected(value = R.id.spinnerId, callback = OnItemSelected.Callback.ITEM_SELECTED)
    public void selected(View view, int position)
    {
        Toast.makeText(this, "OnItemSelected", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.button, R.id.button2})
    public void onClickMethod(View v)
    {
        int viewId = v.getId();

        if(viewId == R.id.button)
            Toast.makeText(this, "prvi", Toast.LENGTH_SHORT).show();
        if(viewId == R.id.button2)
            Toast.makeText(this, "drugi", Toast.LENGTH_SHORT).show();
    }

    //    @Generate(name = "Calculator", type = MultipleInterfaces.Interface2.class)
    public class Calculator implements MultipleInterfaces.Interface2
    {
        @Override
        public float sum(int first, int second)
        {
            return 0;
        }

        @Override
        public double doublesum(double first, double second)
        {
            return 0;
        }

        @Override
        public double squareRoot(double in)
        {
            return 0;
        }

        @Override
        public double distance(double x1, double y1, double x2, double y2)
        {
            return 0;
        }
    }

    public static class MultipleInterfaces
    {
        public interface Interface1
        {
            void onResult(int result);
            void onSuccess();
            boolean onFailure();
        }

        public interface Interface2
        {
            float sum(int first, int second);
            double doublesum(double first, double second);
            double squareRoot(double in);
            double distance(double x1, double y1, double x2, double y2);
        }
    }
}
