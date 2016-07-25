package hr.span.tmartincic.pizza.processor;

public class IdAlreadyUsedException extends Exception
{
    /** The exception instance which was raised by "existing" element, stores that element*/
    private FactoryAnnotatedClass existing;

    public IdAlreadyUsedException(FactoryAnnotatedClass existing) {
        this.existing = existing;
    }

    public FactoryAnnotatedClass getExisting() {
        return existing;
    }
}
