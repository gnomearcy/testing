package hr.span.tmartincic.pizza;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface TestAn
{
    boolean shouldBeUsed();
    String shouldMaybeBeUsed() default "";
}
