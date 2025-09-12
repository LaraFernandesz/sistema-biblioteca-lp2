public class ItemDoAcervo {
    private String titulo;
    private int ano;
    private StatusLivro status;

    public ItemDoAcervo(String titulo, int ano) {
        setTitulo(titulo);
        setAno(ano);
        status = StatusLivro.DISPONIVEL;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        int ano_atual = 2025;
        if (ano > ano_atual) {
            System.out.println("Erro: ano inválido.");
        } else {
            this.ano = ano;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == "") {
            System.out.println("Erro: título inválido.");
        } else {
            this.titulo = titulo;
        }
    }

    public StatusLivro getStatus() {
        return status;
    }

    public void setStatus(StatusLivro status) {
        this.status = status;
    }

}
