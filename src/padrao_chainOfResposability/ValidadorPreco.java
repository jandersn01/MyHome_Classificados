package padrao_chainOfResposability;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;
import padrao_Singleton.ConfiguracaoSistema;

/**
 * Validador que verifica se o preço do anúncio é válido.
 * RF03: Verificar se o preço é condizente, evitar preços zero ou sem sentido.
 */
public class ValidadorPreco extends ValidadorAnuncioBase {

    private double precoMinimo;
    private double precoMaximo;

    public ValidadorPreco() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        this.precoMinimo = config.getPrecoMinimo();
        this.precoMaximo = config.getPrecoMaximo();
    }

    public ValidadorPreco(double precoMinimo, double precoMaximo) {
        this.precoMinimo = precoMinimo;
        this.precoMaximo = precoMaximo;
    }

    @Override
    protected List<String> validar(Anuncio anuncio) {
        List<String> erros = new ArrayList<>();

        Double preco = anuncio.getPreco();

        if (preco == null) {
            erros.add("O preco do anuncio e obrigatorio.");
        } else if (preco <= 0) {
            erros.add("O preco deve ser maior que zero.");
        } else if (preco < precoMinimo) {
            erros.add("O preco minimo permitido e R$ " + precoMinimo + ".");
        } else if (preco > precoMaximo) {
            erros.add("O preco maximo permitido e R$ " + precoMaximo + ".");
        }

        return erros;
    }

    public void setPrecoMinimo(double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public void setPrecoMaximo(double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }
}
