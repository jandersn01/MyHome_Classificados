package padrao_Decorator;

import java.util.List;

import model.anuncio.Anuncio;

/**
 * Decorator abstrato base para filtros.
 * RF06: Novos filtros podem ser adicionados dinamicamente.
 */
public abstract class FiltroBase implements ComponentBusca {

    protected ComponentBusca buscaDecorada;

    public FiltroBase(ComponentBusca busca) {
        this.buscaDecorada = busca;
    }

    @Override
    public List<Anuncio> executarBusca() {
        // Executa a busca decorada e aplica o filtro específico
        List<Anuncio> resultados = buscaDecorada.executarBusca();
        return filtrar(resultados);
    }

    /**
     * Método abstrato que aplica o filtro específico.
     * @param anuncios Lista de anúncios a filtrar
     * @return Lista filtrada
     */
    protected abstract List<Anuncio> filtrar(List<Anuncio> anuncios);
}
