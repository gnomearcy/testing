package eu.span.dev.osijek.di.compdepend.model;

public class Dye
{
    private String color;
    private ColorRandomizer randomizer;

    public Dye(ColorRandomizer colorRandomizer)
    {
        this.randomizer = colorRandomizer;
    }
}
