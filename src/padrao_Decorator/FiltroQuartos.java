package padrao_Decorator;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;
import model.imovel.Apartamento;
import model.imovel.Casa;

/**
 * Decorator concreto - Filtra por número de quartos.
 * RF06: Usuários podem buscar por número de quartos.
 */
public class FiltroQuartos extends FiltroBase {

    private int numeroQuartos;

    public FiltroQuartos(ComponentBusca busca, int numeroQuartos) {
        super(busca);
        this.numeroQuartos = numeroQuartos;
    }

    @Override
    protected List<Anuncio> filtrar(List<Anuncio> anuncios) {
        List<Anuncio> filtrados = new ArrayList<>();

        for (Anuncio anuncio : anuncios) {
            if (anuncio.getImovel() != null) {
                int quartos = obterNumeroQuartos(anuncio);
                if (quartos >= numeroQuartos) {
                    filtrados.add(anuncio);
                }
            }
        }

        return filtrados;
    }

    /**
     * Obtém o número de quartos do imóvel (se aplicável).
     */
    private int obterNumeroQuartos(Anuncio anuncio) {
        if (anuncio.getImovel() instanceof Casa) {
            return ((Casa) anuncio.getImovel()).getNumeroQuartos();
        } else if (anuncio.getImovel() instanceof Apartamento) {
            return ((Apartamento) anuncio.getImovel()).getNumeroQuartos();
        }
        return 0; 
    }

    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public void setNumeroQuartos(int numeroQuartos) {
        this.numeroQuartos = numeroQuartos;
    }
}
