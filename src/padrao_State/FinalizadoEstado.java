package padrao_State;

import model.anuncio.Anuncio;

/**
 * Estado Finalizado - Estado final (vendido/alugado - arquivado).
 * Nao permite nenhuma transicao.
 */
public class FinalizadoEstado implements EstadoAnuncio {

    @Override
    public void enviarParaModeracao(Anuncio anuncio) {
        // Estado final - nenhuma transicao permitida
    }

    @Override
    public void aprovar(Anuncio anuncio) {
        // Estado final - nenhuma transicao permitida
    }

    @Override
    public void reprovar(Anuncio anuncio) {
        // Estado final - nenhuma transicao permitida
    }

    @Override
    public void finalizar(Anuncio anuncio) {
        // Ja esta finalizado
    }

    @Override
    public void suspender(Anuncio anuncio) {
        // Estado final - nenhuma transicao permitida
    }

    @Override
    public void reativar(Anuncio anuncio) {
        // Estado final - nenhuma transicao permitida
    }

    @Override
    public String getNomeEstado() {
        return "Finalizado";
    }
}
