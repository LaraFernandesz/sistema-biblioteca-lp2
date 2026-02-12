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
        Usuario usuarioDoEmprestimo = pesquisarUsuarioPorId(idUsuario);
        if (usuarioDoEmprestimo == null) {
            System.out.println("Erro: usuário não cadastrado.");
            return;
        }

        ItemDoAcervo itemDoEmprestimo = pesquisarItemPorTitulo(titulo);
        if (itemDoEmprestimo == null) {
            System.out.println("Erro: item não cadastrado.");
            return;
        }

        if (itemDoEmprestimo.getStatus() == StatusLivro.EMPRESTADO) {
            System.out.println("Erro: item já emprestado.");
            return;
        }

        // Exercício 3: se for Reservavel, só empresta se NÃO estiver reservado
        if (itemDoEmprestimo instanceof Reservavel) {
            Reservavel r = (Reservavel) itemDoEmprestimo;
            if (r.isReservado()) {
                System.out.println("Erro: item está reservado e não pode ser emprestado.");
                return;
            }
        }

        itemDoEmprestimo.setStatus(StatusLivro.EMPRESTADO);

        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(itemDoEmprestimo.getPrazo());

        Emprestimo emprestimo = new Emprestimo(itemDoEmprestimo, usuarioDoEmprestimo, dataEmprestimo, dataDevolucaoPrevista);
        registrosDeEmprestimos.add(emprestimo);

        System.out.println("Empréstimo realizado com sucesso!");
        System.out.println("O item '" + itemDoEmprestimo.getTitulo()
                + "' foi emprestado para o usuário " + usuarioDoEmprestimo.getNome()
                + " na data " + emprestimo.getDataEmprestimo()
                + " e tem de ser devolvido em " + emprestimo.getDataDevolucaoPrevista());
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
            System.out.println("Erro: esse item não está cadastrado.");
            return;
        }

        Emprestimo emprestimo = buscarEmprestimoAtivoPorItem(item);
        if (emprestimo == null) {
            System.out.println("Erro: esse empréstimo não existe.");
            return;
        }

        LocalDate hoje = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), hoje);

        if (dias > 0) {
            double multa = dias * item.getValorMultaPorDia();
            System.out.println("Item devolvido. Você precisa pagar uma multa de R$" + multa);
        } else {
            System.out.println("Item devolvido.");
        }

        emprestimo.getItem().setStatus(StatusLivro.DISPONIVEL);
        emprestimo.setDataDevolucaoReal(hoje);
    }

    public ItemDoAcervo pesquisarItemPorTitulo(String titulo) {
        for (ItemDoAcervo item : this.acervo) {
            if (item.getTitulo().equalsIgnoreCase(titulo)) {
                return item;
            }
        }
        return null;
    }

    public Usuario pesquisarUsuarioPorId(String id) {
        for (Usuario usuario : this.listaDeUsuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public void listarAcervo() {
        System.out.println("Items no Acervo");
        for (var item : acervo) {
            System.out.println(item);
        }
    }

    // Exercício 4: só cadastra se validar() for true
    public void cadastrarItem(ItemDoAcervo item) {
        if (item instanceof Validavel) {
            Validavel v = (Validavel) item;
            if (!v.validar()) {
                System.out.println("Erro: item inválido. Cadastro não realizado.");
                return;
            }
        }
        this.acervo.add(item);
        System.out.println("O item " + item.getTitulo() + " foi cadastrado.");
    }

    // Exercício 4: só cadastra se validar() for true
    public void cadastrarUsuario(Usuario usuario) {
        if (!usuario.validar()) {
            System.out.println("Erro: usuário inválido. Cadastro não realizado.");
            return;
        }
        this.listaDeUsuarios.add(usuario);
        System.out.println("O usuário " + usuario.getNome() + " foi cadastrado.");
    }

    // Exercício 2
    public void imprimirDocumento(Imprimivel objeto) {
        System.out.println("==================================");
        System.out.println(objeto.formatarParaEtiqueta());
        System.out.println("==================================");
    }

    // MAIN DE TESTES (confere Ex. 1,2,3,4)
    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();

        Livro livroValido = new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881, "1234567890123");
        Livro livroTituloInvalido = new Livro("", "Autor Qualquer", 2020, "1234567890123");
        Livro livroIsbnInvalido = new Livro("Livro com ISBN ruim", "Autor", 2020, "123");

        Usuario alunoValido = new Aluno("Thiago", "123");
        Usuario alunoNomeInvalido = new Aluno("Ana", "999");

        System.out.println("\n=== TESTE EX4: CADASTRO COM VALIDACAO ===");
        biblioteca.cadastrarItem(livroValido);
        biblioteca.cadastrarItem(livroTituloInvalido);
        biblioteca.cadastrarItem(livroIsbnInvalido);

        biblioteca.cadastrarUsuario(alunoValido);
        biblioteca.cadastrarUsuario(alunoNomeInvalido);

        System.out.println("\n=== TESTE EX1/EX2: IMPRIMIVEL + imprimirDocumento ===");
        biblioteca.imprimirDocumento(livroValido);
        biblioteca.imprimirDocumento(alunoValido);

        System.out.println("\n=== TESTE EX3: RESERVA BLOQUEANDO EMPRESTIMO ===");
        biblioteca.realizarEmprestimo("123", "Memórias Póstumas de Brás Cubas");
        biblioteca.realizarDevolucao("Memórias Póstumas de Brás Cubas");

        livroValido.reservar();
        biblioteca.realizarEmprestimo("123", "Memórias Póstumas de Brás Cubas");

        livroValido.cancelarReserva();
        biblioteca.realizarEmprestimo("123", "Memórias Póstumas de Brás Cubas");

        System.out.println("\n=== ACERVO FINAL ===");
        biblioteca.listarAcervo();
    }
}