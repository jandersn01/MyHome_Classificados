package padrao_Observer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.anuncio.Anuncio;

/**
 * Observer concreto que registra logs de mudanças nos anúncios.
 * RF04: Mecanismo de Log deve reter informação sobre mudança de status.
 */
public class LoggerSistema implements AnuncioObserver {

    private String caminhoArquivo;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LoggerSistema() {
        this.caminhoArquivo = "logs/sistema.log";
    }

    public LoggerSistema(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void atualizar(Anuncio anuncio, String evento) {
        String mensagem = formatarMensagem(anuncio, evento);
        registrarLog(mensagem);
    }

    /**
     * Formata a mensagem de log.
     */
    private String formatarMensagem(Anuncio anuncio, String evento) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(LocalDateTime.now().format(FORMATTER)).append("] ");
        sb.append("EVENTO: ").append(evento).append(" | ");
        sb.append("Anuncio ID: ").append(anuncio.getId()).append(" | ");
        sb.append("Titulo: ").append(anuncio.getTitulo()).append(" | ");
        sb.append("Estado: ").append(anuncio.getEstado().getNomeEstado());
        return sb.toString();
    }

    /**
     * Registra a mensagem no arquivo de log.
     */
    private void registrarLog(String mensagem) {
        // Cria diretório de logs se não existir
        java.io.File logDir = new java.io.File("logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo, true))) {
            writer.println(mensagem);
        } catch (IOException e) {
            // Em caso de erro, imprime no console como fallback
            System.err.println("[LOG FALLBACK] " + mensagem);
        }
    }

    /**
     * Retorna o log formatado de um evento (para uso em retornos de método).
     */
    public String getLogFormatado(Anuncio anuncio, String evento) {
        return formatarMensagem(anuncio, evento);
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
}
