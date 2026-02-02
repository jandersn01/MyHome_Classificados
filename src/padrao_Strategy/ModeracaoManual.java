package padrao_Strategy;

import model.anuncio.Anuncio;

/**
 * Strategy concreta para moderação manual.
 * Simula aprovação humana - em produção seria integrado a uma interface de moderador.
 * RF03: A moderação pode ser manual.
 */
public class ModeracaoManual implements ModeracaoStrategy {

    private String ultimoRelatorio;
    private boolean decisao;
    private String motivoRejeicao;

    public ModeracaoManual() {
        this.decisao = true; // Padrão: aprovado
        this.motivoRejeicao = "";
    }

    /**
     * Define a decisão da moderação manual.
     * @param aprovado true para aprovar, false para reprovar
     * @param motivo Motivo da rejeição (se reprovado)
     */
    public void definirDecisao(boolean aprovado, String motivo) {
        this.decisao = aprovado;
        this.motivoRejeicao = motivo != null ? motivo : "";
    }

    @Override
    public boolean executarModeracao(Anuncio anuncio) {
        // Gera relatório
        StringBuilder sb = new StringBuilder();
        sb.append("=== Moderacao Manual ===\n");
        sb.append("Anuncio: ").append(anuncio.getTitulo()).append("\n");
        sb.append("Resultado: ").append(decisao ? "APROVADO" : "REPROVADO").append("\n");

        if (!decisao && !motivoRejeicao.isEmpty()) {
            sb.append("Motivo: ").append(motivoRejeicao).append("\n");
        }

        sb.append("\n(Decisao tomada por moderador humano)");

        this.ultimoRelatorio = sb.toString();

        // Atualiza o estado do anúncio
        if (decisao) {
            anuncio.aprovar();
        } else {
            anuncio.reprovar();
        }

        return decisao;
    }

    @Override
    public String getRelatorio() {
        return ultimoRelatorio;
    }

    /**
     * Simula a aguarda por aprovação humana.
     * Em produção, isso seria uma integração com sistema de filas/interface.
     */
    public String aguardarAprovacaoHumana(Anuncio anuncio) {
        StringBuilder sb = new StringBuilder();
        sb.append("Anuncio '").append(anuncio.getTitulo()).append("' ");
        sb.append("aguardando aprovacao de moderador humano.\n");
        sb.append("ID do anuncio: ").append(anuncio.getId());
        return sb.toString();
    }
}
