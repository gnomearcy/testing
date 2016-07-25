package eu.span.dev.osijek.proguard.rules;

public class WrongCalculator implements Calculator
{
    Summator helperInAddition;

    public WrongCalculator(Summator wrongNumber)
    {
        this.helperInAddition = wrongNumber;
    }


    @Override
    public int add(int a,
                   int b)
    {
        return helperInAddition.addUp(a, b);
    }

    @Override
    public int divide(int a,
                      int b)
    {
        return 0;
    }
}
