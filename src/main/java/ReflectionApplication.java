import model.Curso;
import valid.TratamentoValidacao;

public class ReflectionApplication {

    public static void main(String[] args) {
        Curso curso = Curso.of(null, "Curso de C++");

        TratamentoValidacao.executarValidacoesEmClasses(curso);
    }
}
