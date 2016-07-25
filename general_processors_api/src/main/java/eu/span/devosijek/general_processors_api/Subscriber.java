package eu.span.devosijek.general_processors_api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Annotation used to denote the Subscriber.
 *  A subscriber is a class implementing the Utility
 *  interface. Some methods of the Utility interface
 *  depend on the Context. If the subscriber is unable to
 *  provide a context to the Utility interface method,
 *  an exception is thrown.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Subscriber
{

}
