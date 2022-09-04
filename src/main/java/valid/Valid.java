package valid;

import java.lang.annotation.Annotation;
import java.util.List;

public interface Valid<T extends Annotation> {

    String getMessagemErro();

    void setAnnotation(T t);

    boolean campoInvalido(Object object);
}
