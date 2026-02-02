package padrao_Decorator;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;

/**
 * Decorator concreto - Filtra por faixa de preço.
 * RF06: Usuários podem buscar por faixa de preço.
 */
public class FiltroPreco extends FiltroBase {

    private double precoMinimo;
    private double precoMaximo;

    public FiltroPreco(ComponentBusca busca, double precoMinimo, double precoMaximo) {
        super(busca);
        this.precoMinimo = precoMinimo;
        this.precoMaximo = precoMaximo;
    }

    @Override
    protected List<Anuncio> filtrar(List<Anuncio> anuncios) {
        List<Anuncio> filtrados = new ArrayList<>();

        for (Anuncio anuncio : anuncios) {
            Double preco = anuncio.getPreco();
            if (preco != null && preco >= precoMinimo && preco <= precoMaximo) {
                filtrados.add(anuncio);
            }
        }

        return filtrados;
    }

    public double getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public double getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }
}
