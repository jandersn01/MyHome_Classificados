package padrao_Decorator;

import java.util.List;

import model.anuncio.Anuncio;

public class FiltroBase implements ComponentBusca {
	
	public ComponentBusca buscaDecorada;
	
	@Override
	public List<Anuncio> exercutarBusca() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public FiltroBase(ComponentBusca busca) {
		//TODO
	}

}
