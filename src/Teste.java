import java.util.Arrays;
import java.util.List;

import model.anuncio.Anuncio;
import model.imovel.Imovel;
import model.usuario.Usuario;
import padrao_Facade.Fachada;
import padrao_Strategy.ModeracaoAutomatica;
import padrao_Strategy.ModeracaoManual;
import repositorio.CSVDataLoader;

/**
 * ============================================================================
 *                    CLASSE DE TESTE - SISTEMA MYHOME CLASSIFICADOS
 * ============================================================================
 *
 * Esta classe demonstra todas as funcionalidades do sistema MyHome Classificados,
 * simulando o uso real por um usuario que deseja publicar anuncios de imoveis
 * ou buscar imoveis disponiveis.
 *
 * PADROES DE PROJETO DEMONSTRADOS:
 * --------------------------------
 * 1. FACADE        - Ponto de entrada unico para o sistema (Fachada.java)
 * 2. SINGLETON     - Configuracoes centralizadas (ConfiguracaoSistema.java)
 * 3. FACTORY METHOD - Criacao de diferentes tipos de imoveis
 * 4. PROTOTYPE     - Clonagem de imoveis pre-configurados
 * 5. BUILDER       - Construcao guiada de anuncios complexos
 * 6. STATE         - Ciclo de vida do anuncio (Rascunho -> Moderacao -> Ativo -> Finalizado)
 * 7. OBSERVER      - Notificacoes automaticas de mudancas de estado
 * 8. STRATEGY      - Estrategias de moderacao (automatica/manual) e notificacao (email)
 * 9. CHAIN OF RESP.- Validacoes em cadeia durante a moderacao
 * 10. DECORATOR    - Filtros combinaveis para busca de anuncios
 *
 * REQUISITOS FUNCIONAIS ATENDIDOS:
 * --------------------------------
 * RF01 - Criacao de anuncios de diferentes tipos de imoveis
 * RF02 - Prototipos de imoveis com configuracao padrao
 * RF03 - Publicacao e moderacao de anuncios
 * RF04 - Ciclo de vida do anuncio com notificacoes
 * RF05 - Notificacao do usuario via email
 * RF06 - Busca avancada com filtros combinaveis
 * RF07 - Configuracao centralizada via arquivo properties
 * RF08 - Builder para criacao guiada de anuncios
 *
 * REQUISITOS DE EXECUCAO ATENDIDOS:
 * ---------------------------------
 * E1 - Povoamento de dados automaticamente a partir de arquivos CSV
 *
 * @author Equipe MyHome
 * @version 1.0
 */
public class Teste {

    // Separadores visuais para organizar a saida no console
    private static final String SEPARADOR_SECAO = "\n" + "=".repeat(70) + "\n";
    private static final String SEPARADOR_SUBSECAO = "-".repeat(50);

    /**
     * Metodo principal que executa todos os testes do sistema.
     */
    public static void main(String[] args) {

        System.out.println(SEPARADOR_SECAO);
        System.out.println("       SISTEMA MYHOME CLASSIFICADOS - DEMONSTRACAO COMPLETA");
        System.out.println("       Plataforma de Classificados de Imoveis");
        System.out.println(SEPARADOR_SECAO);

        // ====================================================================
        // INICIALIZACAO DO SISTEMA
        // ====================================================================
        // O padrao FACADE e usado aqui. A Fachada e o ponto de entrada unico
        // para todo o sistema, escondendo a complexidade dos subsistemas.
        // Internamente, ela inicializa:
        // - SINGLETON: ConfiguracaoSistema (configuracoes do sistema)
        // - PrototypeRegistry (prototipos de imoveis)
        // - LoggerSistema (observer para logs)
        // - ModeracaoAutomatica (strategy padrao de moderacao)
        // ====================================================================

        System.out.println("[INICIALIZACAO] Criando instancia da Fachada...");
        System.out.println("  -> Padrao FACADE: Ponto de entrada unico para o sistema");
        System.out.println("  -> Padrao SINGLETON: Carregando configuracoes centralizadas");

        Fachada fachada = new Fachada();

        System.out.println("[OK] Sistema inicializado com sucesso!\n");

        // ====================================================================
        // TESTE 0: CSVDATALOADER - POVOAMENTO AUTOMATICO (E1)
        // ====================================================================
        testarCSVDataLoader(fachada);

        // ====================================================================
        // TESTE 1: CONFIGURACOES DO SISTEMA (SINGLETON - RF07)
        // ====================================================================
        testarConfiguracoes(fachada);

        // ====================================================================
        // TESTE 2: CADASTRO DE USUARIOS
        // ====================================================================
        Usuario anunciante = testarCadastroUsuarios(fachada);

        // ====================================================================
        // TESTE 3: FACTORY METHOD - CRIACAO DE IMOVEIS (RF01)
        // ====================================================================
        Imovel casa = testarFactoryMethod(fachada);

        // ====================================================================
        // TESTE 4: PROTOTYPE - CLONAGEM DE IMOVEIS (RF02)
        // ====================================================================
        Imovel apartamentoClonado = testarPrototype(fachada);

        // ====================================================================
        // TESTE 5: BUILDER - CRIACAO DE ANUNCIOS (RF01/RF08)
        // ====================================================================
        Anuncio anuncioVenda = testarBuilder(fachada, casa, anunciante);
        Anuncio anuncioAluguel = testarBuilderAluguel(fachada, apartamentoClonado, anunciante);

        // ====================================================================
        // TESTE 6: STATE + OBSERVER + CHAIN + STRATEGY - MODERACAO (RF03/RF04/RF05)
        // ====================================================================
        testarModeracaoAutomatica(fachada, anuncioVenda);

        // ====================================================================
        // TESTE 7: MODERACAO MANUAL (STRATEGY - RF03)
        // ====================================================================
        testarModeracaoManual(fachada, anuncioAluguel);

        // ====================================================================
        // TESTE 8: CICLO DE VIDA COMPLETO DO ANUNCIO (STATE - RF04)
        // ====================================================================
        testarCicloVidaAnuncio(fachada, anuncioVenda);

        // ====================================================================
        // TESTE 9: DECORATOR - BUSCA COM FILTROS (RF06)
        // ====================================================================
        // Primeiro, vamos criar mais anuncios para ter dados para filtrar
        criarAnunciosAdicionais(fachada, anunciante);
        testarBuscaComFiltros(fachada);

        // ====================================================================
        // TESTE 10: LISTAGEM FINAL
        // ====================================================================
        testarListagemFinal(fachada, anunciante);

        // ====================================================================
        // FINALIZACAO
        // ====================================================================
        System.out.println(SEPARADOR_SECAO);
        System.out.println("       DEMONSTRACAO FINALIZADA COM SUCESSO!");
        System.out.println("       Todos os padroes de projeto foram demonstrados.");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("\nRECURSOS GERADOS:");
        System.out.println("  -> Arquivo de log: logs/sistema.log");
        System.out.println("  -> Notificacoes enviadas via Email (simulado)");
        System.out.println("\nObrigado por usar o MyHome Classificados!");
    }

    // ========================================================================
    //                          METODOS DE TESTE
    // ========================================================================

    /**
     * TESTE 0: CSVDATALOADER - Povoamento Automatico de Dados (E1)
     *
     * O CSVDataLoader carrega dados de usuarios e anuncios a partir de arquivos CSV.
     * Isso atende ao requisito E1: "Povoar os dados automaticamente a partir de
     * arquivos CSV. Isso evita digitacoes iniciais para poder testar o sistema."
     *
     * Arquivos CSV utilizados:
     * - data/usuarios.csv: id;nome;email;telefone
     * - data/anuncios.csv: titulo;descricao;preco;tipoImovel;localizacao;area;tipoAnuncio;fotos
     */
    private static void testarCSVDataLoader(Fachada fachada) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 0: CSVDATALOADER - Povoamento Automatico (E1)");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("O CSVDataLoader carrega dados de arquivos CSV automaticamente.");
        System.out.println("Isso atende ao requisito E1 do projeto.\n");

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Arquivos CSV utilizados:");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("  -> data/usuarios.csv (formato: id;nome;email;telefone)");
        System.out.println("  -> data/anuncios.csv (formato: titulo;descricao;preco;tipo;...)");

        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("Carregando dados dos arquivos CSV:");
        System.out.println(SEPARADOR_SUBSECAO);

        // Cria o CSVDataLoader passando a Fachada existente
        // Isso permite que os dados carregados sejam adicionados ao sistema
        CSVDataLoader dataLoader = new CSVDataLoader(fachada);

        // Carrega usuarios do arquivo CSV
        System.out.println("\n[CSV] Carregando usuarios de 'data/usuarios.csv'...");
        List<Usuario> usuariosCSV = dataLoader.carregarUsuarios("src/data/usuarios.csv");
        System.out.println("      Usuarios carregados: " + usuariosCSV.size());

        // Exibe os usuarios carregados
        System.out.println("\n      Lista de usuarios do CSV:");
        for (Usuario u : usuariosCSV) {
            System.out.println("        - " + u.getNome() + " (" + u.getEmail() + ")");
        }

        // Carrega anuncios do arquivo CSV
        System.out.println("\n[CSV] Carregando anuncios de 'data/anuncios.csv'...");
        List<Anuncio> anunciosCSV = dataLoader.carregarAnuncios("src/data/anuncios.csv", usuariosCSV);
        System.out.println("      Anuncios carregados: " + anunciosCSV.size());

        // Exibe os anuncios carregados
        System.out.println("\n      Lista de anuncios do CSV:");
        for (Anuncio a : anunciosCSV) {
            System.out.println("        - " + a.getTitulo());
            System.out.println("          Tipo: " + a.getImovel().getTipoImovel() +
                             " | Preco: R$ " + String.format("%,.2f", a.getPreco()) +
                             " | " + a.getTipoAnuncio());
        }

        // Adiciona os dados carregados na Fachada
        System.out.println("\n[CSV] Adicionando dados carregados ao sistema...");
        fachada.setUsuarios(usuariosCSV);
        fachada.setAnuncios(anunciosCSV);

        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("Resumo do povoamento automatico:");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("  Total de usuarios no sistema: " + fachada.listarUsuarios().size());
        System.out.println("  Total de anuncios no sistema: " + fachada.listarAnuncios().size());

        System.out.println("\n[OK] Dados carregados com sucesso do CSV!");
        System.out.println("     O sistema esta pronto para uso com dados pre-cadastrados.");
    }

    /**
     * TESTE 1: SINGLETON - Configuracoes Centralizadas (RF07)
     *
     * O padrao SINGLETON garante que existe apenas uma instancia das configuracoes
     * do sistema, acessivel globalmente. As configuracoes sao carregadas de um
     * arquivo config.properties.
     */
    private static void testarConfiguracoes(Fachada fachada) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 1: SINGLETON - Configuracoes Centralizadas (RF07)");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("O padrao SINGLETON garante uma unica instancia de configuracoes.");
        System.out.println("As configuracoes sao carregadas do arquivo config.properties.\n");

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Configuracoes do Sistema:");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println(fachada.getConfiguracoesInfo());

        System.out.println("\nTaxa de comissao padrao: " + fachada.getTaxaComissao() + "%");
    }

    /**
     * TESTE 2: Gestao de Usuarios
     *
     * Demonstra a gestao de usuarios no sistema.
     * Os usuarios ja foram carregados do CSV no teste anterior.
     * Aqui demonstramos como usar um usuario existente e cadastrar novos.
     */
    private static Usuario testarCadastroUsuarios(Fachada fachada) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 2: Gestao de Usuarios");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("Os usuarios ja foram carregados do CSV (Teste 0).");
        System.out.println("Agora vamos demonstrar a gestao de usuarios.\n");

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Usuarios disponiveis no sistema (carregados do CSV):");
        System.out.println(SEPARADOR_SUBSECAO);

        List<Usuario> usuarios = fachada.listarUsuarios();
        for (Usuario u : usuarios) {
            System.out.println("  - [" + u.getId() + "] " + u.getNome());
            System.out.println("    Email: " + u.getEmail() + " | Tel: " + u.getTelefone());
        }

        // Usa o primeiro usuario do CSV como anunciante principal
        Usuario anunciante = usuarios.get(0);
        System.out.println("\n[SELECIONADO] Anunciante principal: " + anunciante.getNome());
        System.out.println("              Este usuario sera usado para criar novos anuncios.");
        System.out.println("              -> Recebera notificacoes por EMAIL (Strategy)");

        // Demonstra cadastro de um novo usuario (alem dos do CSV)
        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("Cadastrando usuario adicional (via Fachada):");
        System.out.println(SEPARADOR_SUBSECAO);

        Usuario novoUsuario = fachada.cadastrarUsuario(
            "U006",
            "Roberto Almeida",
            "roberto.almeida@email.com",
            "(83) 99444-6666"
        );
        System.out.println("\n[OK] Novo usuario cadastrado: " + novoUsuario.getNome());
        System.out.println("     Email: " + novoUsuario.getEmail());

        System.out.println("\nTotal de usuarios no sistema: " + fachada.listarUsuarios().size());
        System.out.println("  (5 do CSV + 1 cadastrado manualmente)");

        return anunciante;
    }

    /**
     * TESTE 3: FACTORY METHOD - Criacao de Imoveis (RF01)
     *
     * O padrao FACTORY METHOD permite criar diferentes tipos de imoveis
     * (Casa, Apartamento, SalaComercial) sem expor a logica de criacao.
     * Isso torna o sistema flexivel para adicionar novos tipos de imoveis
     * no futuro sem modificar o codigo existente.
     */
    private static Imovel testarFactoryMethod(Fachada fachada) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 3: FACTORY METHOD - Criacao de Imoveis (RF01)");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("O padrao FACTORY METHOD cria imoveis sem expor a logica de criacao.");
        System.out.println("Cada tipo de imovel (Casa, Apartamento, SalaComercial) tem sua factory.\n");

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Criando diferentes tipos de imoveis:");
        System.out.println(SEPARADOR_SUBSECAO);

        // Cria uma Casa usando CasaFactory (internamente)
        Imovel casa = fachada.criarImovel(
            "casa",                          // tipo -> seleciona CasaFactory
            "Casa em Manaira",               // titulo
            450000.00,                       // preco
            "Linda casa com 3 quartos, suite master, quintal e piscina",
            "Manaira, Joao Pessoa",          // localizacao
            180.0                            // area em m2
        );
        System.out.println("\n[CASA] Criada via CasaFactory:");
        System.out.println("  Titulo: " + casa.getTitulo());
        System.out.println("  Tipo: " + casa.getTipoImovel());
        System.out.println("  Preco: R$ " + String.format("%,.2f", casa.getPreco()));
        System.out.println("  Area: " + casa.getArea() + " m2");
        System.out.println("  Localizacao: " + casa.getLocalizacao());

        // Cria um Apartamento usando ApartamentoFactory
        Imovel apartamento = fachada.criarImovel(
            "apartamento",
            "Apartamento Beira Mar",
            380000.00,
            "Apartamento com vista para o mar, 2 quartos, varanda gourmet",
            "Cabo Branco, Joao Pessoa",
            95.0
        );
        System.out.println("\n[APARTAMENTO] Criado via ApartamentoFactory:");
        System.out.println("  Titulo: " + apartamento.getTitulo());
        System.out.println("  Tipo: " + apartamento.getTipoImovel());
        System.out.println("  Preco: R$ " + String.format("%,.2f", apartamento.getPreco()));

        // Cria uma Sala Comercial usando SalaComercialFactory
        Imovel sala = fachada.criarImovel(
            "sala comercial",
            "Sala Empresarial Centro",
            2500.00,
            "Sala comercial moderna, ar condicionado, estacionamento",
            "Centro, Joao Pessoa",
            60.0
        );
        System.out.println("\n[SALA COMERCIAL] Criada via SalaComercialFactory:");
        System.out.println("  Titulo: " + sala.getTitulo());
        System.out.println("  Tipo: " + sala.getTipoImovel());
        System.out.println("  Preco: R$ " + String.format("%,.2f", sala.getPreco()));

        return casa; // Retorna a casa para usar nos proximos testes
    }

    /**
     * TESTE 4: PROTOTYPE - Clonagem de Imoveis (RF02)
     *
     * O padrao PROTOTYPE permite criar novos objetos clonando prototipos
     * pre-configurados. Isso e util quando certos tipos de imoveis devem
     * iniciar com uma configuracao padrao (ex: apartamento padrao com 2 quartos).
     */
    private static Imovel testarPrototype(Fachada fachada) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 4: PROTOTYPE - Clonagem de Imoveis (RF02)");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("O padrao PROTOTYPE clona objetos pre-configurados.");
        System.out.println("Util para criar imoveis com configuracao padrao rapidamente.\n");

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Prototipos disponiveis no registro:");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println(fachada.listarPrototipos());

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Clonando prototipos:");
        System.out.println(SEPARADOR_SUBSECAO);

        // Clona um apartamento padrao (2 quartos, 60m2)
        Imovel aptClonado = fachada.criarImovelDePrototipo("apartamento_padrao");
        System.out.println("\n[CLONE] Apartamento Padrao clonado:");
        System.out.println("  Titulo: " + aptClonado.getTitulo());
        System.out.println("  Preco: R$ " + String.format("%,.2f", aptClonado.getPreco()));
        System.out.println("  Area: " + aptClonado.getArea() + " m2");

        // Clona uma casa padrao
        Imovel casaClonada = fachada.criarImovelDePrototipo("casa_padrao");
        System.out.println("\n[CLONE] Casa Padrao clonada:");
        System.out.println("  Titulo: " + casaClonada.getTitulo());
        System.out.println("  Preco: R$ " + String.format("%,.2f", casaClonada.getPreco()));
        System.out.println("  Area: " + casaClonada.getArea() + " m2");

        // Demonstra que sao objetos diferentes (clones independentes)
        System.out.println("\n[INFO] Os clones sao objetos independentes do prototipo original.");
        System.out.println("       Alteracoes no clone NAO afetam o prototipo.");

        return aptClonado;
    }

    /**
     * TESTE 5: BUILDER - Criacao de Anuncios de Venda (RF01/RF08)
     *
     * O padrao BUILDER permite construir objetos complexos passo a passo.
     * O DiretorAnuncio coordena o processo de construcao, garantindo que
     * todas as informacoes obrigatorias sejam coletadas corretamente.
     */
    private static Anuncio testarBuilder(Fachada fachada, Imovel imovel, Usuario anunciante) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 5: BUILDER - Criacao de Anuncio de Venda (RF01/RF08)");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("O padrao BUILDER constroi objetos complexos passo a passo.");
        System.out.println("O DiretorAnuncio garante que informacoes obrigatorias sejam coletadas.\n");

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Construindo anuncio de VENDA:");
        System.out.println(SEPARADOR_SUBSECAO);

        // Lista de fotos do imovel
        List<String> fotos = Arrays.asList(
            "img/casa_frente.jpg",
            "img/casa_sala.jpg",
            "img/casa_quarto.jpg",
            "img/casa_piscina.jpg"
        );

        System.out.println("\n1. Definindo titulo: 'Casa Maravilhosa em Manaira'");
        System.out.println("2. Definindo descricao detalhada...");
        System.out.println("3. Definindo preco: R$ 450.000,00");
        System.out.println("4. Associando imovel: " + imovel.getTipoImovel());
        System.out.println("5. Associando anunciante: " + anunciante.getNome());
        System.out.println("6. Adicionando " + fotos.size() + " fotos");

        // Cria o anuncio usando o Builder (via Fachada)
        // Internamente, a Fachada usa DiretorAnuncio + AnuncioVendaBuilder
        Anuncio anuncio = fachada.criarAnuncioVenda(
            "Casa Maravilhosa em Manaira - Oportunidade Unica!",
            "Excelente casa com 3 quartos sendo 1 suite master, sala ampla, " +
            "cozinha planejada, quintal com churrasqueira e piscina. " +
            "Garagem para 2 carros. Documentacao ok, pronta para morar!",
            450000.00,
            imovel,
            anunciante,
            fotos
        );

        System.out.println("\n[OK] Anuncio criado com sucesso!");
        System.out.println("  ID: " + anuncio.getId());
        System.out.println("  Titulo: " + anuncio.getTitulo());
        System.out.println("  Tipo: " + anuncio.getTipoAnuncio());
        System.out.println("  Preco: R$ " + String.format("%,.2f", anuncio.getPreco()));
        System.out.println("  Estado inicial: " + anuncio.getEstadoAtual());
        System.out.println("  Fotos: " + anuncio.getFotos().size() + " imagens");

        System.out.println("\n[INFO] OBSERVER configurado automaticamente:");
        System.out.println("  -> LoggerSistema: registra todas as mudancas de estado");
        System.out.println("  -> NotificadorObserver: envia emails ao anunciante");

        return anuncio;
    }

    /**
     * TESTE 5B: BUILDER - Criacao de Anuncios de Aluguel
     */
    private static Anuncio testarBuilderAluguel(Fachada fachada, Imovel imovel, Usuario anunciante) {
        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("Construindo anuncio de ALUGUEL:");
        System.out.println(SEPARADOR_SUBSECAO);

        // Cria anuncio de aluguel usando AnuncioAluguelBuilder (via Fachada)
        Anuncio anuncioAluguel = fachada.criarAnuncioAluguel(
            "Apartamento para Alugar - Centro",
            "Apartamento mobiliado, 2 quartos, proximo ao comercio e transporte publico.",
            1800.00,        // valor do aluguel
            350.00,         // valor do condominio
            12,             // tempo minimo de contrato (meses)
            imovel,
            anunciante
        );

        System.out.println("\n[OK] Anuncio de ALUGUEL criado!");
        System.out.println("  ID: " + anuncioAluguel.getId());
        System.out.println("  Titulo: " + anuncioAluguel.getTitulo());
        System.out.println("  Tipo: " + anuncioAluguel.getTipoAnuncio());
        System.out.println("  Valor: R$ " + String.format("%,.2f", anuncioAluguel.getPreco()) + "/mes");
        System.out.println("  Estado: " + anuncioAluguel.getEstadoAtual());

        return anuncioAluguel;
    }

    /**
     * TESTE 6: MODERACAO AUTOMATICA (STATE + OBSERVER + CHAIN + STRATEGY)
     *
     * Este teste demonstra a integracao de varios padroes:
     * - STATE: gerencia as transicoes de estado do anuncio
     * - OBSERVER: notifica mudancas de estado (log + email)
     * - CHAIN OF RESPONSIBILITY: executa validacoes em cadeia
     * - STRATEGY: define a estrategia de moderacao (automatica)
     */
    private static void testarModeracaoAutomatica(Fachada fachada, Anuncio anuncio) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 6: MODERACAO AUTOMATICA (RF03/RF04/RF05)");
        System.out.println("         STATE + OBSERVER + CHAIN OF RESP. + STRATEGY");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("Integracao de multiplos padroes no processo de moderacao:\n");
        System.out.println("  STRATEGY: Define se moderacao e automatica ou manual");
        System.out.println("  CHAIN OF RESPONSIBILITY: Executa validacoes em sequencia");
        System.out.println("     -> ValidadorDescricao: verifica tamanho minimo");
        System.out.println("     -> ValidadorPreco: verifica se preco e valido");
        System.out.println("     -> ValidadorFotos: verifica quantidade de fotos");
        System.out.println("     -> ValidadorTermosProibidos: verifica palavras proibidas");
        System.out.println("  STATE: Gerencia transicao Rascunho -> Moderacao -> Ativo/Suspenso");
        System.out.println("  OBSERVER: Notifica mudancas (Logger + Email)");

        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("Configurando estrategia de MODERACAO AUTOMATICA:");
        System.out.println(SEPARADOR_SUBSECAO);

        // Garante que a estrategia e automatica
        fachada.setEstrategiaModeracao(new ModeracaoAutomatica());
        System.out.println("[CONFIG] Estrategia definida: ModeracaoAutomatica");

        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("Submetendo anuncio para moderacao:");
        System.out.println(SEPARADOR_SUBSECAO);

        System.out.println("\nEstado ANTES: " + anuncio.getEstadoAtual());
        System.out.println("\n[ACAO] Submetendo anuncio '" + anuncio.getTitulo().substring(0, 30) + "...'");
        System.out.println("       Aguarde... executando validacoes automaticas...\n");

        // Submete para moderacao (STATE muda + CHAIN valida + OBSERVER notifica)
        String relatorio = fachada.submeterParaModeracao(anuncio);

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("RELATORIO DE MODERACAO:");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println(relatorio);

        System.out.println("\nEstado DEPOIS: " + anuncio.getEstadoAtual());

        System.out.println("\n[INFO] Durante este processo:");
        System.out.println("  -> STATE: Rascunho -> Moderacao -> " + anuncio.getEstadoAtual());
        System.out.println("  -> OBSERVER: Log registrado em logs/sistema.log");
        System.out.println("  -> OBSERVER: Email enviado para " + anuncio.getAnunciante().getEmail());
    }

    /**
     * TESTE 7: MODERACAO MANUAL (STRATEGY - RF03)
     *
     * Demonstra a troca dinamica da estrategia de moderacao.
     * O padrao STRATEGY permite alterar o algoritmo de moderacao em tempo de execucao.
     */
    private static void testarModeracaoManual(Fachada fachada, Anuncio anuncio) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 7: STRATEGY - Moderacao Manual (RF03)");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("O padrao STRATEGY permite trocar o algoritmo de moderacao.");
        System.out.println("Vamos mudar de ModeracaoAutomatica para ModeracaoManual.\n");

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Configurando estrategia de MODERACAO MANUAL:");
        System.out.println(SEPARADOR_SUBSECAO);

        // Cria estrategia manual e define a decisao do moderador humano
        ModeracaoManual moderacaoManual = new ModeracaoManual();

        // Simula a decisao de um moderador humano: APROVAR
        moderacaoManual.definirDecisao(true, "");
        System.out.println("\n[MODERADOR] Decisao do moderador humano: APROVAR");

        // Troca a estrategia na Fachada
        fachada.setEstrategiaModeracao(moderacaoManual);
        System.out.println("[CONFIG] Estrategia alterada para: ModeracaoManual");

        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("Submetendo anuncio de aluguel para moderacao manual:");
        System.out.println(SEPARADOR_SUBSECAO);

        System.out.println("\nEstado ANTES: " + anuncio.getEstadoAtual());
        System.out.println("\n[ACAO] Submetendo anuncio '" + anuncio.getTitulo() + "'");

        // Submete para moderacao manual
        String relatorio = fachada.submeterParaModeracao(anuncio);

        System.out.println("\n" + relatorio);
        System.out.println("\nEstado DEPOIS: " + anuncio.getEstadoAtual());

        // Restaura para moderacao automatica
        fachada.setEstrategiaModeracao(new ModeracaoAutomatica());
        System.out.println("\n[CONFIG] Estrategia restaurada para: ModeracaoAutomatica");
    }

    /**
     * TESTE 8: CICLO DE VIDA COMPLETO DO ANUNCIO (STATE - RF04)
     *
     * Demonstra todas as transicoes de estado possiveis:
     * Rascunho -> Moderacao -> Ativo -> Finalizado
     *                      -> Suspenso -> Rascunho (reativar)
     */
    private static void testarCicloVidaAnuncio(Fachada fachada, Anuncio anuncio) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 8: STATE - Ciclo de Vida Completo (RF04)");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("O padrao STATE gerencia as transicoes de estado do anuncio.");
        System.out.println("Cada estado define quais operacoes sao permitidas.\n");

        System.out.println("DIAGRAMA DE ESTADOS:");
        System.out.println("  [Rascunho] --submeter--> [Moderacao] --aprovar--> [Ativo]");
        System.out.println("                              |                        |");
        System.out.println("                          reprovar                finalizar/suspender");
        System.out.println("                              v                        |");
        System.out.println("                          [Suspenso] <----suspender----+");
        System.out.println("                              |                        |");
        System.out.println("                          reativar                 finalizar");
        System.out.println("                              v                        v");
        System.out.println("                          [Rascunho]              [Finalizado]");

        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("Demonstrando transicoes de estado:");
        System.out.println(SEPARADOR_SUBSECAO);

        // O anuncio ja esta Ativo (foi aprovado no teste anterior)
        System.out.println("\nEstado atual: " + anuncio.getEstadoAtual());

        // Se o anuncio estiver ativo, vamos finalizar a venda
        if (anuncio.getEstadoAtual().equals("Ativo")) {
            System.out.println("\n[ACAO] Finalizando a venda do imovel...");
            System.out.println("       (O comprador fechou negocio!)");

            fachada.finalizarAnuncio(anuncio);

            System.out.println("\n[OK] Estado apos finalizar: " + anuncio.getEstadoAtual());
            System.out.println("     -> O anuncio foi arquivado (estado final)");
            System.out.println("     -> OBSERVER notificou: Log + Email enviados");
        }

        System.out.println("\n[INFO] Estados possiveis no ciclo de vida:");
        System.out.println("  - Rascunho: Estado inicial, aguardando submissao");
        System.out.println("  - Em Moderacao: Anuncio em analise");
        System.out.println("  - Ativo: Anuncio aprovado e visivel");
        System.out.println("  - Suspenso: Anuncio reprovado ou retirado");
        System.out.println("  - Finalizado: Venda/aluguel concluido (estado final)");
    }

    /**
     * Cria anuncios adicionais para demonstrar a busca com filtros.
     */
    private static void criarAnunciosAdicionais(Fachada fachada, Usuario anunciante) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("Criando anuncios adicionais para demonstrar filtros...");
        System.out.println(SEPARADOR_SECAO);

        // Anuncio 1: Casa barata
        Imovel casa1 = fachada.criarImovel("casa", "Casa Simples", 150000,
            "Casa 2 quartos em bairro tranquilo", "Valentina, Joao Pessoa", 70);
        Anuncio a1 = fachada.criarAnuncioVenda("Casa Popular em Valentina",
            "Casa simples com 2 quartos, sala, cozinha e quintal pequeno.",
            150000, casa1, anunciante, Arrays.asList("img/casa1.jpg", "img/casa2.jpg"));
        fachada.submeterParaModeracao(a1);
        System.out.println("[+] Casa em Valentina - R$ 150.000");

        // Anuncio 2: Apartamento caro
        Imovel apto1 = fachada.criarImovel("apartamento", "Cobertura Luxo", 950000,
            "Cobertura duplex com vista panoramica", "Altiplano, Joao Pessoa", 200);
        Anuncio a2 = fachada.criarAnuncioVenda("Cobertura Duplex no Altiplano",
            "Cobertura de alto padrao com 4 quartos, piscina privativa e vista mar.",
            950000, apto1, anunciante, Arrays.asList("img/cob1.jpg", "img/cob2.jpg", "img/cob3.jpg"));
        fachada.submeterParaModeracao(a2);
        System.out.println("[+] Cobertura no Altiplano - R$ 950.000");

        // Anuncio 3: Sala comercial
        Imovel sala1 = fachada.criarImovel("sala comercial", "Sala Centro", 180000,
            "Sala comercial no centro", "Centro, Joao Pessoa", 45);
        Anuncio a3 = fachada.criarAnuncioVenda("Sala Comercial Centro Historico",
            "Sala comercial em edificio tradicional, ideal para escritorio ou consultorio.",
            180000, sala1, anunciante, Arrays.asList("img/sala1.jpg"));
        fachada.submeterParaModeracao(a3);
        System.out.println("[+] Sala Comercial no Centro - R$ 180.000");

        // Anuncio 4: Casa em Manaira
        Imovel casa2 = fachada.criarImovel("casa", "Casa Manaira", 520000,
            "Casa grande em Manaira", "Manaira, Joao Pessoa", 200);
        Anuncio a4 = fachada.criarAnuncioVenda("Casa Espaçosa em Manaira",
            "Ampla casa com 4 quartos, piscina, churrasqueira e jardim.",
            520000, casa2, anunciante, Arrays.asList("img/casa3.jpg", "img/casa4.jpg"));
        fachada.submeterParaModeracao(a4);
        System.out.println("[+] Casa em Manaira - R$ 520.000");

        System.out.println("\n[OK] " + fachada.listarAnuncios().size() + " anuncios no sistema");
    }

    /**
     * TESTE 9: DECORATOR - Busca com Filtros Combinaveis (RF06)
     *
     * O padrao DECORATOR permite adicionar filtros dinamicamente.
     * Cada filtro "decora" a busca anterior, permitindo combinacoes flexiveis.
     */
    private static void testarBuscaComFiltros(Fachada fachada) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 9: DECORATOR - Busca com Filtros (RF06)");
        System.out.println(SEPARADOR_SECAO);

        System.out.println("O padrao DECORATOR permite combinar filtros dinamicamente.");
        System.out.println("Cada filtro envolve o anterior, criando uma cadeia de filtros.\n");

        // ----------------------------------------------------------------
        // BUSCA 1: Sem filtros (todos os anuncios ativos)
        // ----------------------------------------------------------------
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("BUSCA 1: Todos os anuncios (sem filtros)");
        System.out.println(SEPARADOR_SUBSECAO);

        List<Anuncio> todos = fachada.filtrarAnuncios(null, null, null, null, null);
        System.out.println("Encontrados: " + todos.size() + " anuncios\n");
        for (Anuncio a : todos) {
            System.out.println("  - " + a.getTitulo());
            System.out.println("    R$ " + String.format("%,.2f", a.getPreco()) +
                             " | " + a.getEstadoAtual());
        }

        // ----------------------------------------------------------------
        // BUSCA 2: Filtro por faixa de preco
        // ----------------------------------------------------------------
        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("BUSCA 2: Filtro por PRECO (R$ 100.000 a R$ 300.000)");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Decorators aplicados: BuscaAnuncio -> FiltroPreco\n");

        List<Anuncio> porPreco = fachada.filtrarAnuncios(100000.0, 300000.0, null, null, null);
        System.out.println("Encontrados: " + porPreco.size() + " anuncios\n");
        for (Anuncio a : porPreco) {
            System.out.println("  - " + a.getTitulo());
            System.out.println("    R$ " + String.format("%,.2f", a.getPreco()));
        }

        // ----------------------------------------------------------------
        // BUSCA 3: Filtro por localizacao
        // ----------------------------------------------------------------
        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("BUSCA 3: Filtro por LOCALIZACAO (Manaira)");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Decorators aplicados: BuscaAnuncio -> FiltroLocalizacao\n");

        List<Anuncio> emManaira = fachada.buscarPorLocalizacao("Manaira");
        System.out.println("Encontrados: " + emManaira.size() + " anuncios em Manaira\n");
        for (Anuncio a : emManaira) {
            System.out.println("  - " + a.getTitulo());
            System.out.println("    " + a.getImovel().getLocalizacao());
        }

        // ----------------------------------------------------------------
        // BUSCA 4: Filtro por tipo de imovel
        // ----------------------------------------------------------------
        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("BUSCA 4: Filtro por TIPO (Casa)");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Decorators aplicados: BuscaAnuncio -> FiltroTipoImovel\n");

        List<Anuncio> casas = fachada.filtrarAnuncios(null, null, null, "Casa", null);
        System.out.println("Encontrados: " + casas.size() + " casas\n");
        for (Anuncio a : casas) {
            System.out.println("  - " + a.getTitulo());
            System.out.println("    Tipo: " + a.getImovel().getTipoImovel() +
                             " | R$ " + String.format("%,.2f", a.getPreco()));
        }

        // ----------------------------------------------------------------
        // BUSCA 5: Filtros combinados (preco + tipo)
        // ----------------------------------------------------------------
        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("BUSCA 5: FILTROS COMBINADOS (Casa + ate R$ 200.000)");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Decorators: BuscaAnuncio -> FiltroPreco -> FiltroTipoImovel\n");

        List<Anuncio> casasBaratas = fachada.filtrarAnuncios(0.0, 200000.0, null, "Casa", null);
        System.out.println("Encontrados: " + casasBaratas.size() + " casas ate R$ 200.000\n");
        for (Anuncio a : casasBaratas) {
            System.out.println("  - " + a.getTitulo());
            System.out.println("    R$ " + String.format("%,.2f", a.getPreco()));
        }

        System.out.println("\n[INFO] O Decorator permite adicionar novos filtros sem");
        System.out.println("       modificar o codigo de busca existente (RF06).");
    }

    /**
     * TESTE 10: Listagem Final do Sistema
     */
    private static void testarListagemFinal(Fachada fachada, Usuario anunciante) {
        System.out.println(SEPARADOR_SECAO);
        System.out.println("TESTE 10: Listagem Final do Sistema");
        System.out.println(SEPARADOR_SECAO);

        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("Anuncios do usuario " + anunciante.getNome() + ":");
        System.out.println(SEPARADOR_SUBSECAO);

        List<Anuncio> meusAnuncios = fachada.verMeusAnuncios(anunciante);
        System.out.println("\nTotal: " + meusAnuncios.size() + " anuncios\n");

        int ativos = 0, finalizados = 0, suspensos = 0, rascunhos = 0;

        for (Anuncio a : meusAnuncios) {
            String estado = a.getEstadoAtual();
            System.out.println("  [" + estado.toUpperCase() + "] " + a.getTitulo());
            System.out.println("         R$ " + String.format("%,.2f", a.getPreco()) +
                             " - " + a.getTipoAnuncio());

            switch (estado) {
                case "Ativo": ativos++; break;
                case "Finalizado": finalizados++; break;
                case "Suspenso": suspensos++; break;
                default: rascunhos++; break;
            }
        }

        System.out.println("\n" + SEPARADOR_SUBSECAO);
        System.out.println("RESUMO POR ESTADO:");
        System.out.println(SEPARADOR_SUBSECAO);
        System.out.println("  Ativos:      " + ativos);
        System.out.println("  Finalizados: " + finalizados);
        System.out.println("  Suspensos:   " + suspensos);
        System.out.println("  Rascunhos:   " + rascunhos);
        System.out.println("  TOTAL:       " + meusAnuncios.size());
    }
}
