package padrao_chainOfResposability;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;
import padrao_Singleton.ConfiguracaoSistema;

/**
 * Validador que verifica se o anúncio contém termos proibidos.
 * RF03: Título e descrição não podem conter termos proibidos
 * (palavras de baixo calão, termos pejorativos, palavras inadequadas).
 */
public class ValidadorTermosProibidos extends ValidadorAnuncioBase {

    private List<String> termosProibidos;

    public ValidadorTermosProibidos() {
        this.termosProibidos = ConfiguracaoSistema.getInstance().getTermosProibidos();
    }

    public ValidadorTermosProibidos(List<String> termosProibidos) {
        this.termosProibidos = termosProibidos;
    }

    @Override
    protected List<String> validar(Anuncio anuncio) {
        List<String> erros = new ArrayList<>();

        String titulo = anuncio.getTitulo();
        String descricao = anuncio.getDescricao();

        // Verifica título
        if (titulo != null) {
            List<String> termosEncontradosTitulo = verificarTermos(titulo);
            if (!termosEncontradosTitulo.isEmpty()) {
                erros.add("O titulo contem termos proibidos: " + termosEncontradosTitulo);
            }
        }

        // Verifica descrição
        if (descricao != null) {
            List<String> termosEncontradosDescricao = verificarTermos(descricao);
            if (!termosEncontradosDescricao.isEmpty()) {
                erros.add("A descricao contem termos proibidos: " + termosEncontradosDescricao);
            }
        }

        return erros;
    }

    /**
     * Verifica se o texto contém algum termo proibido.
     * @param texto O texto a ser verificado
     * @return Lista de termos proibidos encontrados
     */
    private List<String> verificarTermos(String texto) {
        List<String> encontrados = new ArrayList<>();
        String textoLower = texto.toLowerCase();

        for (String termo : termosProibidos) {
            if (textoLower.contains(termo.toLowerCase().trim())) {
                encontrados.add(termo.trim());
            }
        }

        return encontrados;
    }

    public void setTermosProibidos(List<String> termosProibidos) {
        this.termosProibidos = termosProibidos;
    }

    public void adicionarTermo(String termo) {
        if (this.termosProibidos == null) {
            this.termosProibidos = new ArrayList<>();
        }
        this.termosProibidos.add(termo);
    }
}
