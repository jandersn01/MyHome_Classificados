package padrao_Observer;

import model.anuncio.Anuncio;

/**
 * Observer - Interface para observadores de mudanças em anúncios.
 * RF04/RF05: Notificar sobre mudanças de estado do anúncio.
 */
public interface AnuncioObserver {

    /**
     * Método chamado quando o anúncio sofre uma alteração.
     * @param anuncio O anúncio que foi alterado
     * @param evento O tipo de evento que ocorreu (ex: "APROVADO", "VENDIDO", etc.)
     */
    void atualizar(Anuncio anuncio, String evento);
}
