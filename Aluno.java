package src;

public class Aluno extends Usuario {

    public Aluno(String nome, String id) {
        super(nome, id);
    }

    @Override
    public String formatarParaEtiqueta() {
        return "CARTÃO DE ACESSO - Aluno: " + getNome() + " | Matrícula: " + getId();
    }
}