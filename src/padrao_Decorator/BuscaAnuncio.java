package padrao_Decorator;

import java.util.List;

import model.anuncio.Anuncio;
import repositorio.CSVDataLoader;

public class BuscaAnuncio implements ComponentBusca{

	public CSVDataLoader repositorio;
	
	@Override
	public List<Anuncio> exercutarBusca() {
		// TODO Auto-generated method stub
		return null;
	}

}
