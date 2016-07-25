package eu.span.devosijek.generics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

import static eu.span.devosijek.generics.GenericInterface.*;

public class VanjskaKlasa
{
    public static void vanjskaMetoda(Class<? extends GenericInterface.BaseInterace> arg)
    {
        Class<ExtendedClass1> klasa = ExtendedClass1.class;
        final String ex1 = klasa.getSimpleName();

        switch(arg.getClass().getSimpleName())
        {

        }

        Class<?>[] klase = arg.getClasses();
        TypeVariable<? extends Class<? extends GenericInterface.BaseInterace>>[] typeVar = arg.getTypeParameters();

        for(TypeVariable<? extends Class<? extends GenericInterface.BaseInterace>> neznam : typeVar)
        {
            System.out.println(neznam.getClass().getSimpleName());
        }
        for(Class<?> k : klase)
        {
            System.out.println(k.getSimpleName());
            Method[] metode = arg.getDeclaredMethods();
            try
            {
                metode[0].invoke(arg);
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch(InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
    }
}
