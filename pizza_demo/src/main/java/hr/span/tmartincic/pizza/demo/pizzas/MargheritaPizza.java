package hr.span.tmartincic.pizza.demo.pizzas;

import hr.span.tmartincic.pizza.Factory;

@Factory(
        type = Meal.class,
        id = "Margherita"
)
public class MargheritaPizza implements Meal
{
    @Override
    public float getPrice()
    {
        return 6f;
    }
}
