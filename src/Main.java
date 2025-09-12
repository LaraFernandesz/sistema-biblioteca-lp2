import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Usuário
        Usuario usuario = new Usuario("Thiago", "123");
        biblioteca.cadastrarUsuario(usuario);

        // Itens
        Livro livroJava = new Livro("Java Como Programar", "Deitel", 2014);
        Livro livroBrasCubas = new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881);
        Revista revistaVeja = new Revista("Veja - Abril", 2015, 1);
        DVD dvdReiLeao = new DVD("O Rei Leão", 1994, 89);

        biblioteca.cadastrarItem(livroJava);
        biblioteca.cadastrarItem(livroBrasCubas);
        biblioteca.cadastrarItem(revistaVeja);
        biblioteca.cadastrarItem(dvdReiLeao);

        System.out.println("\n== Acervo inicial ==");
        biblioteca.listarAcervo();

        // Empréstimo do DVD
        System.out.println("\n== Empréstimo do DVD ==");
        biblioteca.realizarEmprestimo("123", "O Rei Leão");

        // Ver data prevista (deve ser hoje + 3 dias)
        Emprestimo empDVD = biblioteca.buscarEmprestimoAtivoPorItem(dvdReiLeao);
        if (empDVD != null) {
            System.out.println("Data prevista de devolução (DVD): " + empDVD.getDataDevolucaoPrevista());

            // Simular devolução com 5 dias de atraso:
            // força a data prevista para 5 dias atrás
            empDVD.setDataDevolucaoPrevista(LocalDate.now().minusDays(5));
            System.out.println("\n== Devolução do DVD com 5 dias de atraso ==");
            biblioteca.realizarDevolucao("O Rei Leão"); // multa esperada: 5 * 2,00 = 10,00
        }

        // Buscar (Exercício 3)
        System.out.println("\n== Busca por termo: 'Machado' ==");
        List<ItemDoAcervo> resultado = biblioteca.buscar("Machado");
        for (ItemDoAcervo item : resultado) {
            System.out.println(item);
        }

        System.out.println("\n== Acervo final ==");
        biblioteca.listarAcervo();
    }
}
