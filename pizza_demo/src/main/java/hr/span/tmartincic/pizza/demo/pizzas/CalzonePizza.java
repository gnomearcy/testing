package hr.span.tmartincic.pizza.demo.pizzas;

import hr.span.tmartincic.pizza.Factory;

@Factory(
        id = "Calzone",
        type = Meal.class
)
public class CalzonePizza implements Meal
{
    @Override
    public float getPrice()
    {
        return 4.5f;
    }
}
