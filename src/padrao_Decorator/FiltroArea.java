package padrao_Decorator;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;

/**
 * Decorator concreto - Filtra por área do imóvel.
 * RF06: Usuários podem buscar por área.
 */
public class FiltroArea extends FiltroBase {

    private double areaMinima;
    private double areaMaxima;

    public FiltroArea(ComponentBusca busca, double areaMinima, double areaMaxima) {
        super(busca);
        this.areaMinima = areaMinima;
        this.areaMaxima = areaMaxima;
    }

    @Override
    protected List<Anuncio> filtrar(List<Anuncio> anuncios) {
        List<Anuncio> filtrados = new ArrayList<>();

        for (Anuncio anuncio : anuncios) {
            if (anuncio.getImovel() != null) {
                double area = anuncio.getImovel().getArea();
                if (area >= areaMinima && area <= areaMaxima) {
                    filtrados.add(anuncio);
                }
            }
        }

        return filtrados;
    }

    public double getAreaMinima() {
        return areaMinima;
    }

    public void setAreaMinima(double areaMinima) {
        this.areaMinima = areaMinima;
    }

    public double getAreaMaxima() {
        return areaMaxima;
    }

    public void setAreaMaxima(double areaMaxima) {
        this.areaMaxima = areaMaxima;
    }
}
