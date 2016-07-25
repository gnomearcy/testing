package hr.span.tmartincic.pizza.demo.pizzas;

import hr.span.tmartincic.pizza.Factory;

@Factory(
        id = "Capriciosa",
        type = Meal.class
)
public class Capriciosa implements Meal
{
    @Override
    public float getPrice()
    {
        return 1.24f;
    }
}
