package hr.span.tmartincic.pizza.demo.cookies;

import hr.span.tmartincic.pizza.Factory;
import hr.span.tmartincic.pizza.demo.R;

@Factory(
        type = Cookie.class,
        id = "Kitkat"
)
public class Kitkat implements Cookie
{
    @Override
    public float getSweetnessLevel()
    {
        return 19;
    }
}
