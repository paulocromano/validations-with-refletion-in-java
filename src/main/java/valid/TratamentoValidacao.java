package valid;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TratamentoValidacao {

    private static List<JSONObject> jsonErrosDeValidacoes = new ArrayList<>();

    public static void executarValidacoesEmClasses(Object... objects) {
        for (Object object : objects) {
            executarValidacoesEmUmaClasse(object);
        }

        if (!jsonErrosDeValidacoes.isEmpty())
            throw new RuntimeException(jsonErrosDeValidacoes.toString());
    }

    private static void executarValidacoesEmUmaClasse(Object object) {
        Class<?> klass = object.getClass();
        Field[] fields = klass.getDeclaredFields();
        JSONObject jsonErrosFieldsDaClasse = new JSONObject();

        for (Field field : fields) {
            List<JSONObject> jsonErrosDoField = new ArrayList<>();
            field.setAccessible(true);

            for (Annotation annotation : field.getAnnotations()) {
                try {
                    Optional<JSONObject> erroGeradoOptional = validarField(object, field, annotation);
                    if (erroGeradoOptional.isPresent()) jsonErrosDoField.add(erroGeradoOptional.get());
                }
                catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                catch (InstantiationException e) {
                    throw new RuntimeException(e);
                }
            }

            if (!jsonErrosDoField.isEmpty()) jsonErrosFieldsDaClasse.put(field.getName(), jsonErrosDoField);
        }

        if (!jsonErrosFieldsDaClasse.isEmpty()) jsonErrosDeValidacoes.add(gerarJsonComInformacoesDosErrosDosFieldsDaClasse(klass, jsonErrosFieldsDaClasse));
    }

    private static Optional<JSONObject> validarField(Object object, Field field, Annotation annotation) throws IllegalAccessException, InstantiationException {
        Object objectField = field.get(object);
        Validator validatorAnotation = annotation.annotationType().getAnnotation(Validator.class);
        Class<? extends Valid> classValid = validatorAnotation.using();

        Valid valid = (Valid) classValid.newInstance();
        valid.setAnnotation(annotation);

        if (valid.campoInvalido(objectField))
            return Optional.of(gerarJsonComInformacoesDoErroGeradoNoField(object, field, valid));

        return Optional.empty();
    }

    private static JSONObject gerarJsonComInformacoesDosErrosDosFieldsDaClasse(Class<?> klass, JSONObject jsonErrosFieldsDaClasse) {
        JSONObject jsonErrosDaClasse = new JSONObject();

        jsonErrosDaClasse.put("classe", klass.getName());
        jsonErrosDaClasse.put("data e hora", DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm:ss").format(LocalDateTime.now()));
        jsonErrosDaClasse.put("campos", jsonErrosFieldsDaClasse);

        return jsonErrosDaClasse;
    }

    private static JSONObject gerarJsonComInformacoesDoErroGeradoNoField(Object object, Field field, Valid<?> valid) throws IllegalAccessException {
        JSONObject jsonErrosDoField = new JSONObject();

        jsonErrosDoField.put("valor", Objects.nonNull(field.get(object)) ? field.get(object) : "null");
        jsonErrosDoField.put("mensagem", valid.getMessagemErro());

        return jsonErrosDoField;
    }
}
