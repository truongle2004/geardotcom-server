package web_ecommerce.core.validation.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnComment {
	
	String value() default "";
}
