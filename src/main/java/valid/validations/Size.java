package valid.validations;

import valid.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Validator(using = SizeValidator.class)
public @interface Size {

    long min() default 0L;

    long max() default -1;

    String message() default "O campo possui tamanho inválido!";
}
