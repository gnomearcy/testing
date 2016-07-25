package hr.span.tmartincic.pizza;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Factory
{
    /**
     * The name of the factory
     */
    Class type();

    /**
     * The identifier for determining which item should be instantiated
     */
    String id();
}
