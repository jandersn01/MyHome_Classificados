package padrao_chainOfResposability;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;
import padrao_Singleton.ConfiguracaoSistema;

/**
 * Validador que verifica se o anúncio tem quantidade mínima de fotos.
 * RF03: Verificar se o anúncio tem ao menos uma foto.
 */
public class ValidadorFotos extends ValidadorAnuncioBase {

    private int minimoFotos;

    public ValidadorFotos() {
        this.minimoFotos = ConfiguracaoSistema.getInstance().getFotosMinimo();
    }

    public ValidadorFotos(int minimoFotos) {
        this.minimoFotos = minimoFotos;
    }

    @Override
    protected List<String> validar(Anuncio anuncio) {
        List<String> erros = new ArrayList<>();

        List<String> fotos = anuncio.getFotos();

        if (fotos == null || fotos.isEmpty()) {
            erros.add("O anuncio deve ter pelo menos " + minimoFotos + " foto(s).");
        } else if (fotos.size() < minimoFotos) {
            erros.add("O anuncio deve ter pelo menos " + minimoFotos + " foto(s). " +
                      "Atual: " + fotos.size() + " foto(s).");
        }

        // Verifica limite máximo de fotos
        int limiteMaximo = ConfiguracaoSistema.getInstance().getLimiteUploadFotos();
        if (fotos != null && fotos.size() > limiteMaximo) {
            erros.add("O anuncio pode ter no maximo " + limiteMaximo + " fotos. " +
                      "Atual: " + fotos.size() + " fotos.");
        }

        return erros;
    }

    public void setMinimoFotos(int minimoFotos) {
        this.minimoFotos = minimoFotos;
    }
}
