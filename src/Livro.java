package src;

public class Livro extends ItemDoAcervo implements Reservavel, Validavel {

    private boolean reservado;
    private String autor;
    private String isbn;

    public Livro(String titulo, String autor, int ano, String isbn) {
        super(titulo, ano);
        setAutor(autor);
        setIsbn(isbn);
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isEmpty()) {
            System.out.println("Erro: autor inválido.");
        } else {
            this.autor = autor;
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean validar() {
        return getTitulo() != null &&
                !getTitulo().isEmpty() &&
                isbn != null &&
                isbn.length() == 13;
    }

    @Override
    public int getPrazo() {
        return 14;
    }

    @Override
    public double getValorMultaPorDia() {
        return 0.75;
    }

    @Override
    public void reservar() {
        this.reservado = true;
    }

    @Override
    public void cancelarReserva() {
        this.reservado = false;
    }

    @Override
    public boolean isReservado() {
        return reservado;
    }

    @Override
    public String formatarParaEtiqueta() {
        return "ETIQUETA - LIVRO: " + getTitulo() + " | Autor: " + autor;
    }

    @Override
    public String toString() {
        return "Livro '" + getTitulo() + "', ISBN: " + isbn +
                ", de " + autor + " (" + getAno() + ") - Status: " + getStatus();
    }
}