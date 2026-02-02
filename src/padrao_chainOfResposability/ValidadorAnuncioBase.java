package padrao_chainOfResposability;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;

/**
 * Classe base abstrata para validadores de anúncios.
 * Implementa a lógica comum de encadeamento.
 */
public abstract class ValidadorAnuncioBase implements Handler {

    private Handler nextHandler;

    @Override
    public Handler setNext(Handler handler) {
        this.nextHandler = handler;
        return handler;
    }

    @Override
    public List<String> handle(Anuncio anuncio) {
        List<String> erros = new ArrayList<>();

        // Executa a validação específica deste handler
        List<String> errosLocais = validar(anuncio);
        if (errosLocais != null) {
            erros.addAll(errosLocais);
        }

        // Passa para o próximo handler na cadeia
        if (nextHandler != null) {
            List<String> errosProximo = nextHandler.handle(anuncio);
            if (errosProximo != null) {
                erros.addAll(errosProximo);
            }
        }

        return erros;
    }

    /**
     * Método abstrato que cada validador concreto deve implementar.
     * @param anuncio O anúncio a ser validado
     * @return Lista de erros encontrados (vazia se passar na validação)
     */
    protected abstract List<String> validar(Anuncio anuncio);
}
