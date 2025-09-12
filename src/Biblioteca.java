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

    // === Exercício 2: Emprestar/Devolver usando polimorfismo ===
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

        itemDoEmprestimo.setStatus(StatusLivro.EMPRESTADO);
        LocalDate dataEmprestimo = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(itemDoEmprestimo.getPrazo()); // polimorfismo

        Emprestimo emprestimo = new Emprestimo(itemDoEmprestimo, usuarioDoEmprestimo, dataEmprestimo, dataDevolucaoPrevista);
        registrosDeEmprestimos.add(emprestimo);

        System.out.println("Empréstimo realizado com sucesso!");
        System.out.println("O item '" + itemDoEmprestimo.getTitulo()
                + "' foi emprestado para o usuário " + usuarioDoEmprestimo.getNome()
                + " na data " + emprestimo.getDataEmprestimo()
                + " e deve ser devolvido até " + emprestimo.getDataDevolucaoPrevista());
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
            double multa = dias * item.getValorMultaPorDiaAtraso(); // polimorfismo
            System.out.println("Item devolvido. Você precisa pagar uma multa de R$" + String.format("%.2f", multa));
        } else {
            System.out.println("Item devolvido.");
        }
        item.setStatus(StatusLivro.DISPONIVEL);
        emprestimo.setDataDevolucaoReal(hoje);
    }

    // === Exercício 3: buscar(String termo) ===
    // - Busca no título (qualquer ItemDoAcervo, case-insensitive)
    // - Se for Livro, também busca no autor
    public List<ItemDoAcervo> buscar(String termo) {
        List<ItemDoAcervo> resultado = new ArrayList<>();
        if (termo == null) return resultado;

        String chave = termo.toLowerCase();

        for (ItemDoAcervo item : acervo) {
            boolean bateu = false;

            // Título
            if (item.getTitulo() != null && item.getTitulo().toLowerCase().contains(chave)) {
                bateu = true;
            }

            // Autor (se for Livro)
            if (!bateu && item instanceof Livro) {
                Livro l = (Livro) item;
                if (l.getAutor() != null && l.getAutor().toLowerCase().contains(chave)) {
                    bateu = true;
                }
            }

            if (bateu) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    // Utilitários
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
}
