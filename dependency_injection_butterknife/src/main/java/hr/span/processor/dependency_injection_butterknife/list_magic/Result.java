package hr.span.processor.dependency_injection_butterknife.list_magic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**We don't need annotations of this type after source compilation.
 * They are free to be discarded by the compiler afterwards. */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Result
{

}
