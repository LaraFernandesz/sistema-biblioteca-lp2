package src;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private List<ItemDoAcervo> acervo;
    private List<Usuario> listaDeUsuarios;
    private List<Emprestimo> registrosDeEmprestimos;

    public Biblioteca() {
        this.acervo = new ArrayList<>();
        this.listaDeUsuarios = new ArrayList<>();
        this.registrosDeEmprestimos = new ArrayList<>();
    }

    public void realizarEmprestimo(String idUsuario, String titulo) {

        // 1 - Buscar usuário
        Usuario usuarioDoEmprestimo = pesquisarUsuarioPorId(idUsuario);
        if (usuarioDoEmprestimo == null) {
            System.out.println("Erro: usuário não cadastrado.");
            return;
        }

        // 2 - Buscar item
        ItemDoAcervo itemDoEmprestimo = pesquisarItemPorTitulo(titulo);
        if (itemDoEmprestimo == null) {
            System.out.println("Erro: item não cadastrado.");
            return;
        }

        // 3 - Verificar se já está emprestado
        if (itemDoEmprestimo.getStatus() == StatusLivro.EMPRESTADO) {
            System.out.println("Erro: item já emprestado.");
            return;
        }

        // 4 - EXERCÍCIO 3 → Verificar se é Reservável e está reservado
        if (itemDoEmprestimo instanceof Reservavel) {
            Reservavel r = (Reservavel) itemDoEmprestimo;
            if (r.isReservado()) {
                System.out.println("Erro: item está reservado e não pode ser emprestado.");
                return;
            }
        }

        // 5 - Registrar empréstimo
        itemDoEmprestimo.setStatus(StatusLivro.EMPRESTADO);

        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(itemDoEmprestimo.getPrazo());

        Emprestimo emprestimo = new Emprestimo(
                itemDoEmprestimo,
                usuarioDoEmprestimo,
                dataEmprestimo,
                dataDevolucaoPrevista
        );

        registrosDeEmprestimos.add(emprestimo);

        System.out.println("Empréstimo realizado com sucesso!");
        System.out.println("O item '" + itemDoEmprestimo.getTitulo()
                + "' foi emprestado para o usuário "
                + usuarioDoEmprestimo.getNome()
                + " na data " + emprestimo.getDataEmprestimo()
                + " e deve ser devolvido em "
                + emprestimo.getDataDevolucaoPrevista());
    }

    public Emprestimo buscarEmprestimoAtivoPorItem(ItemDoAcervo item) {
        for (Emprestimo emprestimo : registrosDeEmprestimos) {
            if (emprestimo.getItem().getTitulo().equalsIgnoreCase(item.getTitulo())) {
                if (emprestimo.getDataDevolucaoReal() == null) {
                    return emprestimo;
                }
            }
        }
        return null;
    }

    public void realizarDevolucao(String titulo) {

        ItemDoAcervo item = pesquisarItemPorTitulo(titulo);
        if (item == null) {
            System.out.println("Erro: item não cadastrado.");
            return;
        }

        Emprestimo emprestimo = buscarEmprestimoAtivoPorItem(item);
        if (emprestimo == null) {
            System.out.println("Erro: empréstimo não encontrado.");
            return;
        }

        LocalDate hoje = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(
                emprestimo.getDataDevolucaoPrevista(),
                hoje
        );

        if (dias > 0) {
            double multa = dias * item.getValorMultaPorDia();
            System.out.println("Item devolvido. Multa: R$" + multa);
        } else {
            System.out.println("Item devolvido.");
        }

        emprestimo.getItem().setStatus(StatusLivro.DISPONIVEL);
        emprestimo.setDataDevolucaoReal(hoje);
    }

    public ItemDoAcervo pesquisarItemPorTitulo(String titulo) {
        for (ItemDoAcervo item : acervo) {
            if (item.getTitulo().equalsIgnoreCase(titulo)) {
                return item;
            }
        }
        return null;
    }

    public Usuario pesquisarUsuarioPorId(String id) {
        for (Usuario usuario : listaDeUsuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public void listarAcervo() {
        System.out.println("Items no Acervo");
        for (ItemDoAcervo item : acervo) {
            System.out.println(item);
        }
    }

    public void cadastrarItem(ItemDoAcervo item) {
        acervo.add(item);
        System.out.println("O item " + item.getTitulo() + " foi cadastrado.");
    }

    public void cadastrarUsuario(Usuario usuario) {
        listaDeUsuarios.add(usuario);
        System.out.println("O usuário " + usuario.getNome() + " foi cadastrado.");
    }

    // EXERCÍCIO 2 → Polimorfismo via interface
    public void imprimirDocumento(Imprimivel objeto) {
        System.out.println("==================================");
        System.out.println(objeto.formatarParaEtiqueta());
        System.out.println("==================================");
    }

    public static void main(String[] args) {

        Livro livroJava = new Livro("Java Como Programar", "Deitel", 2014);
        Livro livroMemoria = new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881);
        Usuario usuario = new Aluno("Thiago", "123");

        Biblioteca biblioteca = new Biblioteca();

        Revista revista = new Revista("Veja - Abril", 2015, 1);

        biblioteca.cadastrarItem(revista);
        biblioteca.cadastrarItem(livroJava);
        biblioteca.cadastrarItem(livroMemoria);

        biblioteca.cadastrarUsuario(usuario);

        // Exercício 2 → imprimir via interface
        biblioteca.imprimirDocumento(livroMemoria);
        biblioteca.imprimirDocumento(usuario);

        biblioteca.listarAcervo();

        // Exercício 3 → teste de reserva
        livroMemoria.reservar();
        biblioteca.realizarEmprestimo("123", "Memórias Póstumas de Brás Cubas");

        livroMemoria.cancelarReserva();
        biblioteca.realizarEmprestimo("123", "Memórias Póstumas de Brás Cubas");

        biblioteca.listarAcervo();
    }
}