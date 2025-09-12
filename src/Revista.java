
public class Revista extends ItemDoAcervo {
    private int edicao;

    public Revista(String titulo, int ano, int edicao) {
        super(titulo, ano);
        this.edicao = edicao;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    @Override
    public String toString() {
        return "Revista '" + getTitulo() + "' (" + getAno() + ") " + edicao + ". Edição - Status: " + getStatus();
    }
}
