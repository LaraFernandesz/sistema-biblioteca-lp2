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
    public int getPrazo() {
        return 7; // Revistas: 7 dias
    }

    @Override
    public double getValorMultaPorDiaAtraso() {
        return 1.00; // Revistas: R$ 1,00/dia
    }

    @Override
    public String toString() {
        return "Revista '" + getTitulo() + "' (" + getAno() + ") " + edicao + ". Edição - Status: " + getStatus();
    }
}
