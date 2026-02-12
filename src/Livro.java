package src;

public class Livro extends ItemDoAcervo implements Reservavel {
    private boolean reservado;
    private String autor;

    public Livro(String titulo, String autor, int ano) {
        super(titulo, ano);
        setAutor(autor);
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isEmpty()) {
            System.out.println("Erro: título inválido.");
        } else {
            this.autor = autor;
        }
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
    public String toString() {
        return "Livro '" + getTitulo() + "', de " + autor + " (" + getAno() + ") - Status: " + getStatus();
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
}
