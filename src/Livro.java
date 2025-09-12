public class Livro extends ItemDoAcervo {
    private String autor;

    public Livro(String titulo, String autor, int ano) {
        super(titulo, ano);
        setAutor(autor);
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            System.out.println("Erro: autor inválido.");
        } else {
            this.autor = autor;
        }
    }

    @Override
    public int getPrazo() {
        return 14; // Livros: 14 dias
    }

    @Override
    public double getValorMultaPorDiaAtraso() {
        return 0.75; // Livros: R$ 0,75/dia
    }

    @Override
    public String toString() {
        return "Livro '" + getTitulo() + "', de " + autor + " (" + getAno() + ") - Status: " + getStatus();
    }
}
