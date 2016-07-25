package eu.span.dev.osijek.di.compdepend.model;

import java.util.Random;

public class Fabric
{
    private Type type;

    public Fabric()
    {
        Type[] types = Type.values();
        Random r = new Random();
        int chosen = r.nextInt(types.length);
        type = types[chosen];
    }

    private enum Type
    {
        LEATHER, WOOL,
    }
}
