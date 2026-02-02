package padrao_State;

import model.anuncio.Anuncio;

public class ModeracaoEstado implements EstadoAnuncio {

    @Override
    public void enviarParaModeracao(Anuncio anuncio) {
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
    public void finalizar(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void suspender(Anuncio anuncio) {
    }

    @Override
    public void reativar(Anuncio anuncio) {
    }

    @Override
    public String getNomeEstado() {
        return "Em Moderacao";
    }
}
