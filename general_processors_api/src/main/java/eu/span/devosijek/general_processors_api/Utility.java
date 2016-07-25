package eu.span.devosijek.general_processors_api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  Annotation used to mark the utility class whose
 *  methods are to be extracted into an Utility interface
 *  for the implement-and-use behaviour.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Utility
{

}
