package padrao_State;

import model.anuncio.Anuncio;

/**
 * Estado Suspenso - Anúncio reprovado na moderação ou retirado pelo usuário.
 * Permite reativar (voltar para Rascunho).
 */
public class SuspensoEstado implements EstadoAnuncio {

    @Override
    public void enviarParaModeracao(Anuncio anuncio) {
        // Precisa reativar primeiro
    }

    @Override
    public void aprovar(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void reprovar(Anuncio anuncio) {
        // Já está suspenso
    }

    @Override
    public void finalizar(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void suspender(Anuncio anuncio) {
        // Já está suspenso
    }

    @Override
    public void reativar(Anuncio anuncio) {
        anuncio.setEstado(new RascunhoEstado());
        anuncio.notificarObservers("REATIVADO");
    }

    @Override
    public String getNomeEstado() {
        return "Suspenso";
    }
}
