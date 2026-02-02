package padrao_Decorator;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;

/**
 * Decorator concreto - Filtra por localização.
 * RF06: Usuários podem buscar por localização.
 */
public class FiltroLocalizacao extends FiltroBase {

    private String localizacao;

    public FiltroLocalizacao(ComponentBusca busca, String localizacao) {
        super(busca);
        this.localizacao = localizacao;
    }

    @Override
    protected List<Anuncio> filtrar(List<Anuncio> anuncios) {
        List<Anuncio> filtrados = new ArrayList<>();

        for (Anuncio anuncio : anuncios) {
            if (anuncio.getImovel() != null) {
                String loc = anuncio.getImovel().getLocalizacao();
                if (loc != null && loc.toLowerCase().contains(localizacao.toLowerCase())) {
                    filtrados.add(anuncio);
                }
            }
        }

        return filtrados;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
