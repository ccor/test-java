package x.xx.enums;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictLabel {
    String value() default "";
}
