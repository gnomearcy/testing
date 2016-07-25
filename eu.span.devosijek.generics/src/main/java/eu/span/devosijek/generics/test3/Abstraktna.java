package eu.span.devosijek.generics.test3;

public abstract class Abstraktna<T extends ArgumentsContainer>
{
    public abstract Object call(Float user, T args);
}
