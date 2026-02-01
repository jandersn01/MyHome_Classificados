package padrao_chainOfResposability;

import java.util.List;

import model.anuncio.Anuncio;

public interface Handler {
	public Handler setNext(Handler a);
	public List<String> handle(Anuncio a);
}
