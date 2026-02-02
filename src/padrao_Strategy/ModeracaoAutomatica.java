package padrao_Strategy;

import java.util.List;

import model.anuncio.Anuncio;
import padrao_chainOfResposability.ModeradorAnuncios;

/**
 * Strategy concreta para moderação automática.
 * Usa o Chain of Responsibility para validar o anúncio.
 * RF03: A moderação pode ser automatizada dependendo de regras.
 */
public class ModeracaoAutomatica implements ModeracaoStrategy {

    private ModeradorAnuncios moderador;
    private String ultimoRelatorio;

    public ModeracaoAutomatica() {
        this.moderador = new ModeradorAnuncios();
    }

    public ModeracaoAutomatica(ModeradorAnuncios moderador) {
        this.moderador = moderador;
    }

    @Override
    public boolean executarModeracao(Anuncio anuncio) {
        List<String> erros = moderador.moderar(anuncio);
        boolean aprovado = erros.isEmpty();

        // Gera relatório
        StringBuilder sb = new StringBuilder();
        sb.append("=== Moderacao Automatica ===\n");
        sb.append("Anuncio: ").append(anuncio.getTitulo()).append("\n");
        sb.append("Resultado: ").append(aprovado ? "APROVADO" : "REPROVADO").append("\n");

        if (!aprovado) {
            sb.append("\nProblemas encontrados:\n");
            for (String erro : erros) {
                sb.append("- ").append(erro).append("\n");
            }
        }

        this.ultimoRelatorio = sb.toString();

        // Atualiza o estado do anúncio
        if (aprovado) {
            anuncio.aprovar();
        } else {
            anuncio.reprovar();
        }

        return aprovado;
    }

    @Override
    public String getRelatorio() {
        return ultimoRelatorio;
    }

    public void setModerador(ModeradorAnuncios moderador) {
        this.moderador = moderador;
    }
}
