package padrao_State;

import model.anuncio.Anuncio;

/**
 * Estado Moderação - Anúncio está em revisão.
 * Permite aprovar ou reprovar.
 */
public class ModeracaoEstado implements EstadoAnuncio {

    @Override
    public void enviarParaModeracao(Anuncio anuncio) {
        // Já está em moderação
    }

    @Override
    public void aprovar(Anuncio anuncio) {
        anuncio.setEstado(new AtivoEstado());
        anuncio.notificarObservers("APROVADO");
    }

    @Override
    public void reprovar(Anuncio anuncio) {
        anuncio.setEstado(new SuspensoEstado());
        anuncio.notificarObservers("REPROVADO");
    }

    @Override
    public void vender(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void suspender(Anuncio anuncio) {
        // Operação não permitida neste estado - use reprovar
    }

    @Override
    public void reativar(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public String getNomeEstado() {
        return "Em Moderacao";
    }
}
