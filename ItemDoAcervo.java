package src;

import java.time.Year;

public abstract class ItemDoAcervo implements Imprimivel {
    private String titulo;
    private int ano;
    private src.StatusLivro status;

    public ItemDoAcervo(String titulo, int ano) {
        setTitulo(titulo);
        setAno(ano);
        setStatus(src.StatusLivro.DISPONIVEL);
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        int anoAtual = Year.now().getValue();
        if (ano > anoAtual) {
            System.out.println("Erro: ano inválido.");
        } else {
            this.ano = ano;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) {
            System.out.println("Erro: título inválido.");
        } else {
            this.titulo = titulo;
        }
    }

    public src.StatusLivro getStatus() {
        return status;
    }

    public void setStatus(StatusLivro status) {
        this.status = status;
    }

    // valores padrão
    public int getPrazo() {
        return 7;
    }

    public double getValorMultaPorDia() {
        return 0.5;
    }
}