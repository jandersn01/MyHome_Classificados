package padrao_Decorator;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;

/**
 * Decorator concreto - Filtra por tipo de imóvel.
 * RF06: Sistema deve suportar filtros específicos por tipo de imóvel.
 */
public class FiltroTipoImovel extends FiltroBase {

    private String tipoImovel;

    public FiltroTipoImovel(ComponentBusca busca, String tipoImovel) {
        super(busca);
        this.tipoImovel = tipoImovel;
    }

    @Override
    protected List<Anuncio> filtrar(List<Anuncio> anuncios) {
        List<Anuncio> filtrados = new ArrayList<>();

        for (Anuncio anuncio : anuncios) {
            if (anuncio.getImovel() != null) {
                String tipo = anuncio.getImovel().getTipoImovel();
                if (tipo != null && tipo.toLowerCase().contains(tipoImovel.toLowerCase())) {
                    filtrados.add(anuncio);
                }
            }
        }

        return filtrados;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }
}
