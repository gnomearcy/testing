package eu.span.devosijek.generics.test1;

public abstract class GenericMetaData<T> implements MetaData<T>
{
    private final T value;

    public GenericMetaData(T value)
    {
        this.value = value;
    }

    @Override
    public T getValue()
    {
        return value;
    }
}


