package valid.validations;

import valid.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Validator(using = NotNullValidator.class)
public @interface NotNull {

    String message() default "Campo nulo!";
}
