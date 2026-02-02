package padrao_State;

import model.anuncio.Anuncio;

/**
 * Estado Rascunho - Estado inicial do anúncio.
 * Permite apenas enviar para moderação.
 */
public class RascunhoEstado implements EstadoAnuncio {

    @Override
    public void enviarParaModeracao(Anuncio anuncio) {
        anuncio.setEstado(new ModeracaoEstado());
        anuncio.notificarObservers("ENVIADO_MODERACAO");
    }

    @Override
    public void aprovar(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void reprovar(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void finalizar(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void suspender(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void reativar(Anuncio anuncio) {
        // Já está em rascunho
    }

    @Override
    public String getNomeEstado() {
        return "Rascunho";
    }
}
