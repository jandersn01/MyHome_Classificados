import java.util.Arrays;
import java.util.List;
import model.anuncio.Anuncio;
import model.imovel.Imovel;
import model.usuario.Usuario;
import padrao_Facade.Fachada;
import padrao_Singleton.ConfiguracaoSistema;
import repositorio.CSVDataLoader;

/**
 * Classe principal para executar o sistema MyHome Classificados.
 * Demonstra a integração de todos os 10 padrões de projeto implementados.
 *
 * Padrões demonstrados:
 * 1. Singleton - ConfiguracaoSistema
 * 2. Factory Method - Criação de imóveis
 * 3. Prototype - Clonagem de imóveis com PrototypeRegistry
 * 4. Builder - Criação de anúncios
 * 5. State - Ciclo de vida do anúncio
 * 6. Observer - Notificações de mudança de estado
 * 7. Strategy - Tipos de notificação e moderação
 * 8. Chain of Responsibility - Validação de anúncios
 * 9. Decorator - Filtros de busca combinados
 * 10. Facade - Orquestração de todos os padrões
 *
 * Equipe: Janderson e Maria Eduarda
 * Disciplina: Padrões de Projeto de Software - IFPB
 *
 * Para executar no Eclipse: Run As > Java Application
 * Para executar no VS Code/Cursor: Clique em "Run" acima do método main
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           MyHome Classificados - Sistema de Imoveis          ║");
        System.out.println("║     Demonstracao dos 10 Padroes de Projeto Implementados      ║");
        System.out.println("║          Equipe: Janderson e Maria Eduarda - IFPB            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        // ==================== 1. SINGLETON (RF07) ====================
        demonstrarSingleton();

        // ==================== 2. CARREGAR DADOS CSV (E1) ====================
        Fachada fachada = demonstrarCarregamentoCSV();

        // ==================== 3. FACTORY METHOD (RF01) ====================
        demonstrarFactoryMethod(fachada);

        // ==================== 4. PROTOTYPE (RF02) ====================
        demonstrarPrototype(fachada);

        // ==================== 5. BUILDER (RF08) ====================
        demonstrarBuilder(fachada);

        // ==================== 6. STATE + OBSERVER (RF04/RF05) ====================
        demonstrarStateObserver(fachada);

        // ==================== 7. CHAIN OF RESPONSIBILITY + STRATEGY (RF03) ====================
        demonstrarModeracaoChain(fachada);

        // ==================== 8. DECORATOR (RF06) ====================
        demonstrarDecoratorBusca(fachada);

        // ==================== RESUMO FINAL ====================
        exibirResumoFinal(fachada);
    }

    /**
     * Demonstra o padrão Singleton com ConfiguracaoSistema.
     */
    private static void demonstrarSingleton() {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 1. SINGLETON - Configuracoes do Sistema (RF07)                 │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        System.out.println(config.getConfiguracoesInfo());

        // Demonstra que é a mesma instância
        ConfiguracaoSistema config2 = ConfiguracaoSistema.getInstance();
        System.out.println("Mesma instancia? " + (config == config2));
        System.out.println();
    }

    /**
     * Demonstra o carregamento de dados via CSV.
     */
    private static Fachada demonstrarCarregamentoCSV() {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 2. CSV DATA LOADER - Povoamento Automatico (E1)                │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        CSVDataLoader loader = new CSVDataLoader();
        List<Usuario> usuarios = loader.carregarUsuarios("data/usuarios.csv");
        List<Anuncio> anuncios = loader.carregarAnuncios("data/anuncios.csv", usuarios);

        System.out.println(loader.getInfoDadosCarregados(usuarios, anuncios));

        // Configura a fachada com os dados carregados
        Fachada fachada = loader.getFachada();
        fachada.setUsuarios(usuarios);
        fachada.setAnuncios(anuncios);

        // Lista alguns usuarios carregados
        System.out.println("Usuarios carregados:");
        for (Usuario u : usuarios) {
            System.out.println("  - " + u.getNome() + " (" + u.getEmail() + ")");
        }
        System.out.println();

        return fachada;
    }

    /**
     * Demonstra o padrão Factory Method para criação de imóveis.
     */
    private static void demonstrarFactoryMethod(Fachada fachada) {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 3. FACTORY METHOD - Criacao de Imoveis (RF01)                  │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        // Cria diferentes tipos de imoveis via factory
        Imovel casa = fachada.criarImovel("casa", "Casa Nova", 300000,
            "Casa com 3 quartos", "Manaira", 150);
        Imovel apto = fachada.criarImovel("apartamento", "Apto Luxo", 450000,
            "Apartamento de luxo", "Beira Mar", 120);
        Imovel sala = fachada.criarImovel("sala comercial", "Sala Empresarial", 250000,
            "Sala comercial no centro", "Centro", 80);

        System.out.println("Imoveis criados via Factory Method:");
        System.out.println("  - " + casa.getTipoImovel() + ": " + casa.getTitulo());
        System.out.println("  - " + apto.getTipoImovel() + ": " + apto.getTitulo());
        System.out.println("  - " + sala.getTipoImovel() + ": " + sala.getTitulo());
        System.out.println();
    }

    /**
     * Demonstra o padrão Prototype com PrototypeRegistry.
     */
    private static void demonstrarPrototype(Fachada fachada) {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 4. PROTOTYPE - Clonagem de Imoveis (RF02)                      │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        // Lista protótipos disponíveis
        System.out.println("Prototipos pre-configurados disponiveis:");
        System.out.println(fachada.listarPrototipos());

        // Clona um protótipo
        Imovel casaPadrao = fachada.criarImovelDePrototipo("casa-padrao");
        if (casaPadrao != null) {
            System.out.println("Imovel clonado de prototipo: " + casaPadrao.getTitulo());
            System.out.println("  Tipo: " + casaPadrao.getTipoImovel());
            System.out.println("  Preco: R$ " + casaPadrao.getPreco());
        }
        System.out.println();
    }

    /**
     * Demonstra o padrão Builder para criação de anúncios.
     */
    private static void demonstrarBuilder(Fachada fachada) {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 5. BUILDER - Criacao de Anuncios (RF08)                        │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        // Obtém um usuário para ser o anunciante
        List<Usuario> usuarios = fachada.listarUsuarios();
        Usuario anunciante = usuarios.isEmpty() ?
            new Usuario("temp", "Temporario", "temp@email.com", "0000") :
            usuarios.get(0);

        // Cria imóvel para o anúncio
        Imovel imovel = fachada.criarImovel("casa", "Casa Builder", 350000,
            "Casa criada via builder", "Tambauzinho", 180);

        // Cria anúncio de venda via Builder
        Anuncio anuncioVenda = fachada.criarAnuncioVenda(
            "Casa Moderna em Tambauzinho",
            "Excelente casa com acabamento de primeira, 4 quartos, suite master, piscina",
            350000,
            imovel,
            anunciante,
            Arrays.asList("img/casa_builder1.jpg", "img/casa_builder2.jpg")
        );

        System.out.println("Anuncio de VENDA criado via Builder:");
        System.out.println("  Titulo: " + anuncioVenda.getTitulo());
        System.out.println("  Tipo: " + anuncioVenda.getTipoAnuncio());
        System.out.println("  Preco: R$ " + anuncioVenda.getPreco());
        System.out.println("  Estado: " + anuncioVenda.getEstado());
        System.out.println("  Anunciante: " + anuncioVenda.getAnunciante().getNome());

        // Cria anúncio de aluguel via Builder
        Imovel sala = fachada.criarImovel("sala comercial", "Sala Comercial", 2500,
            "Sala para escritorio", "Centro", 50);

        Anuncio anuncioAluguel = fachada.criarAnuncioAluguel(
            "Sala Comercial no Centro",
            "Sala comercial pronta para uso, ar condicionado, estacionamento",
            2500,
            300,
            12,
            sala,
            anunciante
        );

        System.out.println("\nAnuncio de ALUGUEL criado via Builder:");
        System.out.println("  Titulo: " + anuncioAluguel.getTitulo());
        System.out.println("  Tipo: " + anuncioAluguel.getTipoAnuncio());
        System.out.println("  Valor Aluguel: R$ " + anuncioAluguel.getPreco());
        System.out.println("  Estado: " + anuncioAluguel.getEstado());
        System.out.println();
    }

    /**
     * Demonstra os padrões State e Observer no ciclo de vida do anúncio.
     */
    private static void demonstrarStateObserver(Fachada fachada) {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 6. STATE + OBSERVER - Ciclo de Vida e Notificacoes (RF04/RF05) │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        List<Anuncio> anuncios = fachada.listarAnuncios();
        if (anuncios.isEmpty()) {
            System.out.println("Nenhum anuncio disponivel para demonstracao.");
            return;
        }

        Anuncio anuncio = anuncios.get(0);
        System.out.println("Demonstrando transicoes de estado do anuncio: " + anuncio.getTitulo());
        System.out.println("Estado inicial: " + anuncio.getEstado());

        // Transição: Rascunho -> Moderação
        System.out.println("\n[Acao] Submetendo para moderacao...");
        anuncio.submeter();
        System.out.println("Estado apos submeter: " + anuncio.getEstado());

        // Transição: Moderação -> Ativo
        System.out.println("\n[Acao] Aprovando anuncio...");
        anuncio.aprovar();
        System.out.println("Estado apos aprovar: " + anuncio.getEstado());

        // Transição: Ativo -> Suspenso
        System.out.println("\n[Acao] Suspendendo anuncio...");
        anuncio.suspender();
        System.out.println("Estado apos suspender: " + anuncio.getEstado());

        // Transição: Suspenso -> Ativo (reativar)
        System.out.println("\n[Acao] Reativando anuncio...");
        anuncio.reativar();
        System.out.println("Estado apos reativar: " + anuncio.getEstado());

        System.out.println("\n(Observers notificados em cada transicao - verifique logs)");
        System.out.println();
    }

    /**
     * Demonstra Chain of Responsibility e Strategy na moderação.
     */
    private static void demonstrarModeracaoChain(Fachada fachada) {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 7. CHAIN OF RESPONSIBILITY + STRATEGY - Moderacao (RF03)       │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        // Cria um anúncio para teste de moderação
        List<Usuario> usuarios = fachada.listarUsuarios();
        Usuario anunciante = usuarios.isEmpty() ?
            new Usuario("mod", "Moderador", "mod@email.com", "0000") :
            usuarios.get(0);

        Imovel imovel = fachada.criarImovel("casa", "Casa Teste Mod", 250000,
            "Casa para teste de moderacao com descricao adequada e completa", "Centro", 100);

        Anuncio anuncioTeste = fachada.criarAnuncioVenda(
            "Casa para Teste de Moderacao",
            "Esta casa possui excelente estrutura, acabamento de qualidade, " +
            "localizacao privilegiada e documentacao regularizada.",
            250000,
            imovel,
            anunciante,
            Arrays.asList("img/teste1.jpg", "img/teste2.jpg")
        );

        System.out.println("Submetendo anuncio para moderacao automatica...");
        System.out.println("Titulo: " + anuncioTeste.getTitulo());

        String relatorio = fachada.submeterParaModeracao(anuncioTeste);
        System.out.println("\n--- Relatorio de Moderacao ---");
        System.out.println(relatorio);
        System.out.println("Estado final: " + anuncioTeste.getEstado());
        System.out.println();
    }

    /**
     * Demonstra o padrão Decorator na busca de anúncios.
     */
    private static void demonstrarDecoratorBusca(Fachada fachada) {
        System.out.println("┌────────────────────────────────────────────────────────────────┐");
        System.out.println("│ 8. DECORATOR - Busca com Filtros Combinados (RF06)             │");
        System.out.println("└────────────────────────────────────────────────────────────────┘");

        System.out.println("Total de anuncios no sistema: " + fachada.listarAnuncios().size());

        // Busca por localização
        System.out.println("\n[Filtro] Busca por localizacao 'Joao Pessoa':");
        List<Anuncio> porLocalizacao = fachada.buscarPorLocalizacao("Joao Pessoa");
        System.out.println("  Encontrados: " + porLocalizacao.size() + " anuncio(s)");
        for (Anuncio a : porLocalizacao) {
            if (a.getImovel() != null) {
                System.out.println("    - " + a.getTitulo() + " (" + a.getImovel().getLocalizacao() + ")");
            }
        }

        // Busca por faixa de preço
        System.out.println("\n[Filtro] Busca por preco entre R$ 100.000 e R$ 400.000:");
        List<Anuncio> porPreco = fachada.buscarPorPreco(100000, 400000);
        System.out.println("  Encontrados: " + porPreco.size() + " anuncio(s)");
        for (Anuncio a : porPreco) {
            System.out.println("    - " + a.getTitulo() + " (R$ " + a.getPreco() + ")");
        }

        // Busca combinada (múltiplos decorators)
        System.out.println("\n[Filtro Combinado] Preco R$ 100.000-500.000, tipo 'casa', 2+ quartos:");
        List<Anuncio> combinada = fachada.filtrarAnuncios(
            100000.0, 500000.0,  // faixa de preço
            null,                // qualquer localização
            "casa",              // tipo de imóvel
            2                    // mínimo de quartos
        );
        System.out.println("  Encontrados: " + combinada.size() + " anuncio(s)");
        for (Anuncio a : combinada) {
            System.out.println("    - " + a.getTitulo() + " (R$ " + a.getPreco() + ")");
        }
        System.out.println();
    }

    /**
     * Exibe um resumo final do sistema.
     */
    private static void exibirResumoFinal(Fachada fachada) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                     RESUMO DO SISTEMA                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        System.out.println("\nPadroes de Projeto Demonstrados:");
        System.out.println("  1. Singleton       - ConfiguracaoSistema");
        System.out.println("  2. Factory Method  - Criacao de diferentes tipos de imoveis");
        System.out.println("  3. Prototype       - Clonagem de imoveis pre-configurados");
        System.out.println("  4. Builder         - Construcao de anuncios de venda/aluguel");
        System.out.println("  5. State           - Ciclo de vida do anuncio");
        System.out.println("  6. Observer        - Notificacoes de mudanca de estado");
        System.out.println("  7. Strategy        - Canais de notificacao e tipos de moderacao");
        System.out.println("  8. Chain of Resp.  - Cadeia de validacao na moderacao");
        System.out.println("  9. Decorator       - Filtros combinaveis na busca");
        System.out.println(" 10. Facade          - Orquestracao de todos os padroes");

        System.out.println("\nEstatisticas finais:");
        System.out.println("  Usuarios cadastrados: " + fachada.listarUsuarios().size());
        System.out.println("  Anuncios no sistema:  " + fachada.listarAnuncios().size());
        System.out.println("  Taxa de comissao:     " + (fachada.getTaxaComissao() * 100) + "%");

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("     Sistema MyHome Classificados - Execucao concluida!");
        System.out.println("          Equipe: Janderson e Maria Eduarda - IFPB");
        System.out.println("═══════════════════════════════════════════════════════════════");
    }
}
