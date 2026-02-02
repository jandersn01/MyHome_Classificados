package padrao_Strategy;

import model.anuncio.Anuncio;

/**
 * Strategy - Interface para estratégias de moderação.
 * RF03: A moderação pode ser manual ou automatizada.
 */
public interface ModeracaoStrategy {

    /**
     * Executa a moderação do anúncio.
     * @param anuncio O anúncio a ser moderado
     * @return true se aprovado, false se reprovado
     */
    boolean executarModeracao(Anuncio anuncio);

    /**
     * Retorna o relatório da última moderação.
     * @return Relatório formatado
     */
    String getRelatorio();
}
