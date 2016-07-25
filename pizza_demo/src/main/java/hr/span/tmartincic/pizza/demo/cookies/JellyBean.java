package hr.span.tmartincic.pizza.demo.cookies;

//import hr.span.tmartincic.pizza.Factory;
import hr.span.tmartincic.pizza.Factory;

@Factory(
        type = Cookie.class,
        id = "JellyBean"
)
public class JellyBean implements Cookie
{
    @Override
    public float getSweetnessLevel()
    {
        return 16;
    }
}
