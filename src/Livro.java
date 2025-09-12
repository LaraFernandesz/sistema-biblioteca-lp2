
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
        if (autor == "") {
            System.out.println("Erro: título inválido.");
        } else {
            this.autor = autor;
        }
    }

    @Override
    public String toString() {
        return "Livro '" + getTitulo() + "', de " + autor + " (" + getAno() + ") - Status: " + getStatus();
    }
}
