package hr.span.tmartincic.pizza.demo.pizzas;

import hr.span.tmartincic.pizza.Factory;

@Factory(
        type = Meal.class,
        id = "Tiramisu"
)
public class Tiramisu implements Meal
{
    @Override
    public float getPrice()
    {
        return 4.5f;
    }
}
