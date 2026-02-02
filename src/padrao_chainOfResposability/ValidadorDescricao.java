package padrao_chainOfResposability;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;
import padrao_Singleton.ConfiguracaoSistema;

/**
 * Validador que verifica se a descrição do anúncio tem tamanho mínimo.
 * RF03: Verificar se o anúncio tem quantidade mínima de texto na descrição.
 */
public class ValidadorDescricao extends ValidadorAnuncioBase {

    private int tamanhoMinimo;

    public ValidadorDescricao() {
        this.tamanhoMinimo = ConfiguracaoSistema.getInstance().getDescricaoTamanhoMinimo();
    }

    public ValidadorDescricao(int tamanhoMinimo) {
        this.tamanhoMinimo = tamanhoMinimo;
    }

    @Override
    protected List<String> validar(Anuncio anuncio) {
        List<String> erros = new ArrayList<>();

        String descricao = anuncio.getDescricao();

        if (descricao == null || descricao.trim().isEmpty()) {
            erros.add("A descricao do anuncio e obrigatoria.");
        } else if (descricao.trim().length() < tamanhoMinimo) {
            erros.add("A descricao deve ter no minimo " + tamanhoMinimo + " caracteres. " +
                      "Atual: " + descricao.trim().length() + " caracteres.");
        }

        return erros;
    }

    public void setTamanhoMinimo(int tamanhoMinimo) {
        this.tamanhoMinimo = tamanhoMinimo;
    }
}
