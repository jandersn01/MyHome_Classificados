package padrao_Decorator;

import java.util.ArrayList;
import java.util.List;
import model.anuncio.Anuncio;


public class BuscaAnuncio implements ComponentBusca {

    private List<Anuncio> repositorio;

    public BuscaAnuncio() {
        this.repositorio = new ArrayList<>();
    }

    public BuscaAnuncio(List<Anuncio> anuncios) {
        this.repositorio = anuncios != null ? anuncios : new ArrayList<>();
    }

    @Override
    public List<Anuncio> executarBusca() {
        // Retorna todos os anúncios do repositório
        return new ArrayList<>(repositorio);
    }


    public void setRepositorio(List<Anuncio> repositorio) {
        this.repositorio = repositorio != null ? repositorio : new ArrayList<>();
    }

    public void adicionarAnuncio(Anuncio anuncio) {
        if (this.repositorio == null) {
            this.repositorio = new ArrayList<>();
        }
        this.repositorio.add(anuncio);
    }

    public List<Anuncio> getRepositorio() {
        return repositorio;
    }
}
