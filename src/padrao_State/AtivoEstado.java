package padrao_State;

import model.anuncio.Anuncio;

/**
 * Estado Ativo - Anúncio aprovado e visível publicamente.
 * Permite vender ou suspender.
 */
public class AtivoEstado implements EstadoAnuncio {

    @Override
    public void enviarParaModeracao(Anuncio anuncio) {
        // Já está ativo, não precisa enviar para moderação novamente
    }

    @Override
    public void aprovar(Anuncio anuncio) {
        // Já está aprovado
    }

    @Override
    public void reprovar(Anuncio anuncio) {
        // Operação não permitida neste estado
    }

    @Override
    public void vender(Anuncio anuncio) {
        anuncio.setEstado(new VendidoEstado());
        anuncio.notificarObservers("VENDIDO");
    }

    @Override
    public void suspender(Anuncio anuncio) {
        anuncio.setEstado(new SuspensoEstado());
        anuncio.notificarObservers("SUSPENSO");
    }

    @Override
    public void reativar(Anuncio anuncio) {
        // Já está ativo
    }

    @Override
    public String getNomeEstado() {
        return "Ativo";
    }
}
