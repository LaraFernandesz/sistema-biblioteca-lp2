import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private List<ItemDoAcervo> acervo;
    private List<Usuario> listaDeUsuarios;
    private List<Emprestimo> registrosDeEmprestimos;
    private static final int PRAZO_EMPRESTIMO_DIAS = 14;
    private static final double VALOR_MULTA_DIA = 0.75;

    public Biblioteca() {
        this.acervo = new ArrayList<>();
        this.listaDeUsuarios = new ArrayList<>();
        this.registrosDeEmprestimos = new ArrayList<>();
    }

    public void realizarEmprestimo(String idUsuario, String titulo) {
        // 1 - Buscar os objetos usuario e livro
        Usuario usuarioDoEmprestimo = pesquisarUsuarioPorId(idUsuario);
        if(usuarioDoEmprestimo == null) {
            System.out.println("Erro: usuário não cadastrado.");
            return;
        }
        ItemDoAcervo itemDoEmprestimo = pesquisarItemPorTitulo(titulo);
        if(itemDoEmprestimo == null) {
            System.out.println("Erro: item não cadastrado.");
            return;
        }
        // 2 - Validar a regra de negocio (verificar se o livro está disponível)
        if(itemDoEmprestimo.getStatus() == StatusLivro.EMPRESTADO) {
            System.out.println("Erro: livro já emprestrado.");
            return;
        }
        // 3 - Realizar o registro (Criar objeto do tipo Emprestimo e adiciona-lo ao registroDeEmprestimos)
        itemDoEmprestimo.setStatus(StatusLivro.EMPRESTADO);
        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(PRAZO_EMPRESTIMO_DIAS);
        Emprestimo emprestimo = new Emprestimo(itemDoEmprestimo, usuarioDoEmprestimo, dataEmprestimo, dataDevolucaoPrevista);
        registrosDeEmprestimos.add(emprestimo);
        System.out.println("Emprestimo realizado com sucesso!");
        System.out.println("O item '"+itemDoEmprestimo.getTitulo()
                +"' foi emprestado para o usuário " + usuarioDoEmprestimo.getNome()
                +" na data " + emprestimo.getDataEmprestimo()
                +" e tem de ser devolvido em " + emprestimo.getDataDevolucaoPrevista());
    }

    public Emprestimo buscarEmprestimoAtivoPorItem(ItemDoAcervo item) {
        for (Emprestimo emprestimo : registrosDeEmprestimos) {
            if(emprestimo.getItem().getTitulo().equalsIgnoreCase(item.getTitulo())) {
                if(emprestimo.getDataDevolucaoReal() == null) {
                    return emprestimo;
                }
            }
        }
        return null;
    }

    public void realizarDevolucao(String titulo) {
        ItemDoAcervo item = pesquisarItemPorTitulo(titulo);
        if(item == null) {
            System.out.println("Erro: esse item não está cadastrado.");
            return;
        }
        Emprestimo emprestimo = buscarEmprestimoAtivoPorItem(item);
        if(emprestimo == null) {
            System.out.println("Erro: esse emprestimo não existe.");
            return;
        }
        LocalDate hoje = LocalDate.now();
        long dias = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), hoje);

        if(dias > 0) {
            double multa = dias * VALOR_MULTA_DIA;
            System.out.println("Item devolvido. Você precisa pagar uma multa de R$" + multa);
        } else {
            System.out.println("Item devolvido.");
        }
        emprestimo.getItem()    .setStatus(StatusLivro.DISPONIVEL);
        emprestimo.setDataDevolucaoReal(hoje);
    }

    public ItemDoAcervo pesquisarItemPorTitulo(String titulo) {
        for(ItemDoAcervo item : this.acervo) {
            if(item.getTitulo().equalsIgnoreCase(titulo)) {
                return item;
            }
        }
        return null;
    }

    public Usuario pesquisarUsuarioPorId(String id) {
        for(Usuario usuario : this.listaDeUsuarios) {
            if(usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

//    public List<Livro> pesquisarLivroPorTermo(String termo) {
//
//    }

    public void listarAcervo() {
        System.out.println("Itens no Acervo");
        for (var item : acervo) {
            System.out.println(item);
        }
    }

    public void cadastrarItem(ItemDoAcervo item) {
        this.acervo.add(item);
        System.out.println("O item " + item.getTitulo() + " foi cadastrado.");
    }

    public void cadastrarUsuario(Usuario usuario) {
        this.listaDeUsuarios.add(usuario);
        System.out.println("O usuário " + usuario.getNome() + " foi cadastrado.");
    }

    public static void main(String[] args) {
        Livro livroJavaComoProgramar = new Livro("Java Como Programar", "Deitel", 2014);
        Livro livroMemoria = new Livro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881);
        Usuario meuUsuario = new Usuario("Thiago", "123");
        Biblioteca minhaBiblioteca = new Biblioteca();
        minhaBiblioteca.cadastrarItem(livroJavaComoProgramar);
        minhaBiblioteca.cadastrarItem(livroMemoria);
        minhaBiblioteca.cadastrarUsuario(meuUsuario);
        minhaBiblioteca.listarAcervo();
        minhaBiblioteca.realizarEmprestimo("123", "Java Como Programar");
        minhaBiblioteca.listarAcervo();
        minhaBiblioteca.registrosDeEmprestimos.get(0).setDataDevolucaoPrevista(LocalDate.of(2025, 8, 31));
        minhaBiblioteca.realizarDevolucao("Java Como Programar");
        minhaBiblioteca.listarAcervo();
        System.out.println();
        //Adicionando Revista
        Revista revistaVeja = new Revista("Veja - Abril", 2015, 1);
        minhaBiblioteca.cadastrarItem(revistaVeja);
        minhaBiblioteca.listarAcervo();
    }
}
