package hr.span.tmartincic.pizza.demo.cookies;

public interface Cookie
{
    float getSweetnessLevel();

    enum CookieConstants
    {
        KITKAT (1),
        JELLYBEAN (2);

        private int value;
        CookieConstants(int value){this.value = value;}

        public int getValue()
        {
            return this.value;
        }
    }
}

