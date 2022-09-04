package valid.validations;

import valid.Valid;

import java.util.Objects;

public class NotNullValidator implements Valid<NotNull> {

    private NotNull notNullAnnotation;

    @Override
    public String getMessagemErro() {
        return Objects.nonNull(notNullAnnotation) ? notNullAnnotation.message() : null;
    }

    @Override
    public void setAnnotation(NotNull notNull) {
        notNullAnnotation = notNull;
    }

    @Override
    public boolean campoInvalido(Object object) {
        return Objects.isNull(object);
    }
}
