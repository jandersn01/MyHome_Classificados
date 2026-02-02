package padrao_Decorator;

import java.util.List;

import model.anuncio.Anuncio;

/**
 * Decorator - Interface base para busca de anúncios.
 * RF06: Filtros podem ser combinados dinamicamente.
 */
public interface ComponentBusca {

    /**
     * Executa a busca e retorna os anúncios filtrados.
     * @return Lista de anúncios que atendem aos critérios
     */
    List<Anuncio> executarBusca();
}
