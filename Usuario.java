package src;

import java.util.Objects;

public abstract class Usuario implements Imprimivel, Validavel {
    private String nome;
    private String id;

    public Usuario(String nome, String id) {
        setNome(nome);
        setId(id);
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            System.out.println("Erro: valor inválido.");
        } else {
            this.nome = nome;
        }
    }

    public void setId(String id) {
        if (id == null || id.isEmpty()) {
            System.out.println("Erro: valor inválido.");
        } else {
            this.id = id;
        }
    }

    @Override
    public boolean validar() {
        return nome != null && nome.length() > 3;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
