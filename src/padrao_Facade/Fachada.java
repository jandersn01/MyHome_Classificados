package padrao_Facade;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;
import model.imovel.Imovel;
import model.usuario.Usuario;
import padrao_Builder.AnuncioAluguelBuilder;
import padrao_Builder.AnuncioVendaBuilder;
import padrao_Builder.DiretorAnuncio;
import padrao_Decorator.BuscaAnuncio;
import padrao_Decorator.ComponentBusca;
import padrao_Decorator.FiltroLocalizacao;
import padrao_Decorator.FiltroPreco;
import padrao_Decorator.FiltroQuartos;
import padrao_Decorator.FiltroTipoImovel;
import padrao_FactoryMethod.*;
import padrao_Observer.AnuncioObserver;
import padrao_Observer.LoggerSistema;
import padrao_Observer.NotificadorObserver;
import padrao_Prototype.PrototypeRegistry;
import padrao_Singleton.ConfiguracaoSistema;
import padrao_Strategy.ModeracaoAutomatica;
import padrao_Strategy.ModeracaoStrategy;
import padrao_Strategy.NotificacaoEmail;
import padrao_Strategy.NotificacaoStrategy;
import padrao_chainOfResposability.ModeradorAnuncios;

/**
 * Facade - Ponto de entrada simplificado para o sistema MyHome.
 * Orquestra todos os padrões de projeto implementados.
 */
public class Fachada {

    private List<Anuncio> anuncios;
    private List<Usuario> usuarios;
    private PrototypeRegistry prototipos;
    private ConfiguracaoSistema config;
    private LoggerSistema logger;
    private ModeracaoStrategy estrategiaModeracao;
    private DiretorAnuncio diretorAnuncio;

    public Fachada() {
        this.anuncios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.prototipos = new PrototypeRegistry();
        this.config = ConfiguracaoSistema.getInstance();
        this.logger = new LoggerSistema();
        this.estrategiaModeracao = new ModeracaoAutomatica();
        this.diretorAnuncio = new DiretorAnuncio();
    }

    // ==================== GESTÃO DE IMÓVEIS (RF01) ====================


    public Imovel criarImovel(String tipo, String titulo, double preco,
                              String descricao, String localizacao, double area) {
        ImovelFactory factory = obterFactory(tipo);
        return factory.criarImovel(titulo, preco, descricao, localizacao, area);
    }

    private ImovelFactory obterFactory(String tipo) {
        switch (tipo.toLowerCase()) {
            case "casa":
                return new CasaFactory();
            case "apartamento":
                return new ApartamentoFactory();
            case "sala comercial":
            case "salacomercial":
                return new SalaComercialFactory();
            default:
                return new CasaFactory(); // Padrao
        }
    }

    // ==================== PROTÓTIPOS (RF02) ====================


    public Imovel criarImovelDePrototipo(String chavePrototipo) {
        return prototipos.obterPrototipo(chavePrototipo);
    }


    public String listarPrototipos() {
        return prototipos.getPrototiposInfo();
    }

    // ==================== GESTÃO DE ANÚNCIOS (RF01/RF08) ====================


    public Anuncio criarAnuncioVenda(String titulo, String descricao, double preco,
                                     Imovel imovel, Usuario anunciante, List<String> fotos) {
        diretorAnuncio.setBuilder(new AnuncioVendaBuilder());
        Anuncio anuncio = diretorAnuncio.construirAnuncioCompleto(
            titulo, descricao, preco, imovel, anunciante, fotos);

        // Adiciona observers
        configurarObservers(anuncio, anunciante);

        anuncios.add(anuncio);
        return anuncio;
    }


    public Anuncio criarAnuncioAluguel(String titulo, String descricao, double valorAluguel,
                                       double valorCondominio, int tempoMinimo,
                                       Imovel imovel, Usuario anunciante) {
        Anuncio anuncio = diretorAnuncio.construirAnuncioAluguel(
            titulo, descricao, valorAluguel, valorCondominio, tempoMinimo, imovel, anunciante);

        // Adiciona observers
        configurarObservers(anuncio, anunciante);

        anuncios.add(anuncio);
        return anuncio;
    }


    private void configurarObservers(Anuncio anuncio, Usuario anunciante) {
        // Adiciona logger
        anuncio.addObserver(logger);

        // Adiciona notificador via email
        NotificacaoStrategy estrategia = new NotificacaoEmail();
        anuncio.addObserver(new NotificadorObserver(anunciante, estrategia));
    }


    public List<Anuncio> listarAnuncios() {
        return new ArrayList<>(anuncios);
    }


    public List<Anuncio> verMeusAnuncios(Usuario usuario) {
        List<Anuncio> meusAnuncios = new ArrayList<>();
        for (Anuncio anuncio : anuncios) {
            if (anuncio.getAnunciante() != null &&
                anuncio.getAnunciante().getId().equals(usuario.getId())) {
                meusAnuncios.add(anuncio);
            }
        }
        return meusAnuncios;
    }


    public boolean apagarAnuncio(String idAnuncio) {
        return anuncios.removeIf(a -> a.getId().equals(idAnuncio));
    }

    // ==================== MODERAÇÃO (RF03) ====================

    /**
     * Define a estrategia de moderacao (automatica ou manual).
     * Permite trocar dinamicamente como os anuncios serao moderados.
     */
    public void setEstrategiaModeracao(ModeracaoStrategy estrategia) {
        this.estrategiaModeracao = estrategia;
    }

    /**
     * Retorna a estrategia de moderacao atual.
     */
    public ModeracaoStrategy getEstrategiaModeracao() {
        return this.estrategiaModeracao;
    }

    public String submeterParaModeracao(Anuncio anuncio) {
        // Primeiro submete (muda estado para Moderacao)
        anuncio.submeter();

        // Executa moderacao usando a estrategia configurada
        boolean aprovado = estrategiaModeracao.executarModeracao(anuncio);

        return estrategiaModeracao.getRelatorio();
    }


    public String getRelatorioModeracao(Anuncio anuncio) {
        ModeradorAnuncios moderador = new ModeradorAnuncios();
        return moderador.getRelatorioModeracao(anuncio);
    }

    // ==================== BUSCA (RF06) ====================

 
    public List<Anuncio> filtrarAnuncios(Double precoMin, Double precoMax,
                                         String localizacao, String tipoImovel,
                                         Integer quartos) {
        // Componente base com todos os anúncios
        ComponentBusca busca = new BuscaAnuncio(anuncios);

        // Aplica decoradores conforme os filtros solicitados
        if (precoMin != null && precoMax != null) {
            busca = new FiltroPreco(busca, precoMin, precoMax);
        }

        if (localizacao != null && !localizacao.isEmpty()) {
            busca = new FiltroLocalizacao(busca, localizacao);
        }

        if (tipoImovel != null && !tipoImovel.isEmpty()) {
            busca = new FiltroTipoImovel(busca, tipoImovel);
        }

        if (quartos != null && quartos > 0) {
            busca = new FiltroQuartos(busca, quartos);
        }

        return busca.executarBusca();
    }


    public List<Anuncio> buscarPorLocalizacao(String localizacao) {
        return filtrarAnuncios(null, null, localizacao, null, null);
    }


    public List<Anuncio> buscarPorPreco(double precoMin, double precoMax) {
        return filtrarAnuncios(precoMin, precoMax, null, null, null);
    }

    // ==================== GESTÃO DE USUÁRIOS ====================


    public Usuario cadastrarUsuario(String id, String nome, String email,
                                    String telefone) {
        Usuario usuario = new Usuario(id, nome, email, telefone);
        usuarios.add(usuario);
        return usuario;
    }


    public boolean editarUsuario(String id, String novoNome, String novoEmail,
                                 String novoTelefone) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                if (novoNome != null) usuario.setNome(novoNome);
                if (novoEmail != null) usuario.setEmail(novoEmail);
                if (novoTelefone != null) usuario.setTelefone(novoTelefone);
                return true;
            }
        }
        return false;
    }


    public boolean apagarUsuario(String id) {
        return usuarios.removeIf(u -> u.getId().equals(id));
    }


    public Usuario buscarUsuario(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

 
    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // ==================== OPERAÇÕES DE NEGÓCIO ====================


    public void finalizarAnuncio(Anuncio anuncio) {
        anuncio.finalizar();
    }

    public void suspenderAnuncio(Anuncio anuncio) {
        anuncio.suspender();
    }

    public void reativarAnuncio(Anuncio anuncio) {
        anuncio.reativar();
    }

    // ==================== CONFIGURAÇÕES (RF07) ====================


    public String getConfiguracoesInfo() {
        return config.getConfiguracoesInfo();
    }


    public double getTaxaComissao() {
        return config.getTaxaComissao();
    }

    // ==================== SETTERS E DADOS ====================

 
    public void setAnuncios(List<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }


    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }


    public void adicionarAnuncio(Anuncio anuncio) {
        this.anuncios.add(anuncio);
    }

    public void adicionarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public PrototypeRegistry getPrototipos() {
        return prototipos;
    }

    public LoggerSistema getLogger() {
        return logger;
    }
}
