package hr.span.tmartincic.pizza.demo.pizzas;

public interface Meal
{
    float getPrice();

    enum MealConstants
    {
        CALZONE (1),
        MARGHERITA (2),
        TIRAMISU (3),
        CAPRICIOSA (4);

        private int value;
        MealConstants(int value){this.value = value;}

        public int getValue()
        {
            return this.value;
        }
    }
}
