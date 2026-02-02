package padrao_chainOfResposability;

import java.util.ArrayList;
import java.util.List;

import model.anuncio.Anuncio;

/**
 * Classe que gerencia a cadeia de validadores para moderação de anúncios.
 * RF03: Todos os anúncios submetidos devem passar por etapa de moderação.
 */
public class ModeradorAnuncios {

    private Handler cadeiaValidadores;

    public ModeradorAnuncios() {
        configurarCadeiaPadrao();
    }

    /**
     * Configura a cadeia padrão de validadores.
     */
    private void configurarCadeiaPadrao() {
        // Cria os validadores
        ValidadorDescricao validadorDescricao = new ValidadorDescricao();
        ValidadorPreco validadorPreco = new ValidadorPreco();
        ValidadorFotos validadorFotos = new ValidadorFotos();
        ValidadorTermosProibidos validadorTermos = new ValidadorTermosProibidos();

        // Monta a cadeia: Descrição -> Preço -> Fotos -> Termos Proibidos
        validadorDescricao.setNext(validadorPreco);
        validadorPreco.setNext(validadorFotos);
        validadorFotos.setNext(validadorTermos);

        this.cadeiaValidadores = validadorDescricao;
    }

    /**
     * Executa a moderação do anúncio.
     * @param anuncio O anúncio a ser moderado
     * @return Lista de erros encontrados (vazia se aprovado)
     */
    public List<String> moderar(Anuncio anuncio) {
        if (cadeiaValidadores == null) {
            return new ArrayList<>();
        }
        return cadeiaValidadores.handle(anuncio);
    }

    /**
     * Verifica se o anúncio foi aprovado na moderação.
     * @param anuncio O anúncio a ser verificado
     * @return true se aprovado (sem erros), false caso contrário
     */
    public boolean isAprovado(Anuncio anuncio) {
        List<String> erros = moderar(anuncio);
        return erros.isEmpty();
    }

    /**
     * Retorna um relatório da moderação.
     * @param anuncio O anúncio moderado
     * @return Relatório formatado
     */
    public String getRelatorioModeracao(Anuncio anuncio) {
        List<String> erros = moderar(anuncio);
        StringBuilder sb = new StringBuilder();

        sb.append("=== Relatorio de Moderacao ===\n");
        sb.append("Anuncio: ").append(anuncio.getTitulo()).append("\n");
        sb.append("ID: ").append(anuncio.getId()).append("\n\n");

        if (erros.isEmpty()) {
            sb.append("Status: APROVADO\n");
            sb.append("O anuncio passou em todas as validacoes.");
        } else {
            sb.append("Status: REPROVADO\n");
            sb.append("Problemas encontrados:\n");
            for (int i = 0; i < erros.size(); i++) {
                sb.append("  ").append(i + 1).append(". ").append(erros.get(i)).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Permite configurar uma cadeia customizada de validadores.
     */
    public void setCadeiaValidadores(Handler cadeiaValidadores) {
        this.cadeiaValidadores = cadeiaValidadores;
    }
}
