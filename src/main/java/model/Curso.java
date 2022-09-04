package model;

import valid.validations.NotNull;
import valid.validations.Size;

public class Curso {

    @NotNull(message = "O campo 'ID' está nulo!")
    private Long id;
    @Size(max = 40)
    private String nome;


    private Curso(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static Curso of(Long id, String nome) {
        return new Curso(id, nome);
    }
}
