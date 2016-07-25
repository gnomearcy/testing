package eu.span.devosijek.generics;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class GenericInterface
{
    interface BaseInterace
    {
        void method(Integer arg);
    }


    public void genericMethod(BaseInterace arg)
    {
    }


    interface ExtendedInterface1<T> extends BaseInterace
    {
        T etendedMethod1();
    }

    public static class ExtendedClass1 implements ExtendedInterface1<ExtendedClass1>
    {

        @Override
        public ExtendedClass1 etendedMethod1()
        {
            return null;
        }

        @Override
        public void method(Integer arg)
        {

        }
    }
    interface ExtendedInterface2<T> extends BaseInterace
    {

    }

    public static class ExtendedClass2 implements ExtendedInterface2<ExtendedClass2>
    {

        @Override
        public void method(Integer arg)
        {

        }
    }
}
