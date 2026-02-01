package padrao_chainOfResposability;

import java.util.List;

import model.anuncio.Anuncio;

public abstract class ValidadorAnuncioBase implements Handler {
	@Override
	public abstract Handler setNext(Handler a);
	@Override
	public abstract List<String> handle(Anuncio a);

	private Handler nextHandler;
	
	
}
