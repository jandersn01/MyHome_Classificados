package padrao_State;

import model.anuncio.Anuncio;

/**
 * State - Interface que define os comportamentos de cada estado do anúncio.
 * RF04: Cada anúncio deve ter um ciclo de vida com transições de estado.
 */
public interface EstadoAnuncio {

    /**
     * Submete o anúncio para moderação.
     */
    void enviarParaModeracao(Anuncio anuncio);

    /**
     * Aprova o anúncio (transição de Moderação para Ativo).
     */
    void aprovar(Anuncio anuncio);

    /**
     * Reprova o anúncio (transição de Moderação para Suspenso).
     */
    void reprovar(Anuncio anuncio);

    /**
     * Marca o anúncio como vendido (estado final).
     */
    void vender(Anuncio anuncio);

    /**
     * Suspende o anúncio (pode voltar para Rascunho).
     */
    void suspender(Anuncio anuncio);

    /**
     * Reativa o anúncio suspenso (volta para Rascunho).
     */
    void reativar(Anuncio anuncio);

    /**
     * Retorna o nome do estado atual.
     */
    String getNomeEstado();
}
