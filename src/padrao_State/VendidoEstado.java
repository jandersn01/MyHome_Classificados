package padrao_State;

import model.anuncio.Anuncio;

/**
 * Estado Vendido - Estado final (arquivado).
 * Não permite nenhuma transição.
 */
public class VendidoEstado implements EstadoAnuncio {

    @Override
    public void enviarParaModeracao(Anuncio anuncio) {
        // Estado final - nenhuma transição permitida
    }

    @Override
    public void aprovar(Anuncio anuncio) {
        // Estado final - nenhuma transição permitida
    }

    @Override
    public void reprovar(Anuncio anuncio) {
        // Estado final - nenhuma transição permitida
    }

    @Override
    public void vender(Anuncio anuncio) {
        // Já está vendido
    }

    @Override
    public void suspender(Anuncio anuncio) {
        // Estado final - nenhuma transição permitida
    }

    @Override
    public void reativar(Anuncio anuncio) {
        // Estado final - nenhuma transição permitida
    }

    @Override
    public String getNomeEstado() {
        return "Vendido";
    }
}
