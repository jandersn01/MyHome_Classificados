package padrao_chainOfResposability;

import java.util.List;

import model.anuncio.Anuncio;

/**
 * Chain of Responsibility - Interface para handlers de validação.
 * RF03: Todos os anúncios submetidos devem passar por etapa de moderação.
 */
public interface Handler {

    /**
     * Define o próximo handler na cadeia.
     * @param handler O próximo handler
     * @return O handler passado como parâmetro (permite encadeamento fluente)
     */
    Handler setNext(Handler handler);

    /**
     * Processa a validação do anúncio.
     * @param anuncio O anúncio a ser validado
     * @return Lista de erros encontrados (vazia se passar na validação)
     */
    List<String> handle(Anuncio anuncio);
}
