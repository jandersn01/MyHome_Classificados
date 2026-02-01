package padrao_State;

import model.anuncio.Anuncio;

public interface EstadoAnuncio {
	
	void enviarParaModeracao(Anuncio anuncio);

    void aprovar(Anuncio anuncio);

    void reprovar(Anuncio anuncio);

    void arquivar(Anuncio anuncio);

    void vender(Anuncio anuncio);

    void suspender(Anuncio anuncio);
}
