# Validação de atributos com Reflexão

O objetivo deste projeto é validar atributos de uma classe através do uso de anotações e reflexão.

As anotações criadas serão utilizadas para validar os atributos de uma classe. 
Estas anotações deverão conter a anotação ```@Validator``` que possui um atributo 
obrigatório chamado ```using```. Este atributo recebe uma ```Class<? extends Valid>```. 
Esta classe que implementa a interface ```Valid``` será a classe que fará a validação.

```
- Exemplo de anotação criada para validação

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Validator(using = NotNullValidator.class)
public @interface NotNull {

    String message() default "Campo nulo!";
}
```

```
- @Validator

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

    Class<? extends Valid> using();
}
```

```
- Interface para as classes que implementarão as regras para validar um atributo

public interface Valid<T extends Annotation> {

    String getMessagemErro();

    void setAnnotation(T t);

    boolean campoInvalido(Object object);
}
```

```
- Exemplo de implementação da Interface Valid para tratar a validação de atributo nulo

public class NotNullValidator implements Valid<NotNull> { ... }
```