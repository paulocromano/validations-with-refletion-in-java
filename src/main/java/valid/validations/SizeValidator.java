package valid.validations;

import valid.Valid;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SizeValidator implements Valid<Size> {

    private Size sizeAnnotation;

    private List<Class<?>> clasesAceitasParaValidacao;


    public SizeValidator() {
        clasesAceitasParaValidacao = Arrays.asList(Collection.class, String.class, Integer.class, Long.class);
    }

    @Override
    public String getMessagemErro() {
        return Objects.nonNull(sizeAnnotation) ? sizeAnnotation.message() : null;
    }

    @Override
    public void setAnnotation(Size size) {
        sizeAnnotation = size;
    }

    @Override
    public boolean campoInvalido(Object object) {
        if (Objects.nonNull(object)) {
            if (!clasesAceitasParaValidacao.contains(object.getClass()))
                throw new IllegalArgumentException("Esta anotação não permite trabalhar com a classe " + object.getClass().getName());

            if (object instanceof Collection)
                return objetoEstaComTamanhoInvalido(((Collection) object).size());
            else if (object instanceof String)
                return objetoEstaComTamanhoInvalido(((String) object).length());
            else if (object instanceof Integer)
                return objetoEstaComTamanhoInvalido(((Integer) object).intValue());
            else if (object instanceof Long)
                return objetoEstaComTamanhoInvalido(((Long) object).longValue());

            return true;
        }

        return false;
    }

    private boolean objetoEstaComTamanhoInvalido(long length) {
        return (sizeAnnotation.min() != 0L && length < sizeAnnotation.min())
            || (sizeAnnotation.max() != -1L && length > sizeAnnotation.max());
    }
}
