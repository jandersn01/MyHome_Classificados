package padrao_Observer;

import model.anuncio.Anuncio;
import model.usuario.Usuario;
import padrao_Strategy.NotificacaoStrategy;

/**
 * Observer concreto que notifica usuários sobre mudanças nos anúncios.
 * Usa Strategy para definir o canal de notificação.
 * RF05: Sistema deve notificar usuários sobre eventos.
 */
public class NotificadorObserver implements AnuncioObserver {

    private Usuario usuario;
    private NotificacaoStrategy strategy;

    public NotificadorObserver(Usuario usuario, NotificacaoStrategy strategy) {
        this.usuario = usuario;
        this.strategy = strategy;
    }

    @Override
    public void atualizar(Anuncio anuncio, String evento) {
        String destinatario = usuario.getEmail();
        String mensagem = formatarMensagem(anuncio, evento);
        strategy.enviarMensagem(destinatario, mensagem);
    }

    /**
     * Formata a mensagem de notificação.
     */
    private String formatarMensagem(Anuncio anuncio, String evento) {
        StringBuilder sb = new StringBuilder();
        sb.append("MyHome Classificados - Notificacao\n\n");
        sb.append("Ola, ").append(usuario.getNome()).append("!\n\n");

        switch (evento) {
            case "ENVIADO_MODERACAO":
                sb.append("Seu anuncio '").append(anuncio.getTitulo())
                  .append("' foi enviado para moderacao.\n")
                  .append("Aguarde a analise da nossa equipe.");
                break;
            case "APROVADO":
                sb.append("Parabens! Seu anuncio '").append(anuncio.getTitulo())
                  .append("' foi APROVADO e ja esta visivel para compradores.");
                break;
            case "REPROVADO":
                sb.append("Seu anuncio '").append(anuncio.getTitulo())
                  .append("' foi REPROVADO na moderacao.\n")
                  .append("Verifique as diretrizes e tente novamente.");
                break;
            case "VENDIDO":
                sb.append("Seu anuncio '").append(anuncio.getTitulo())
                  .append("' foi marcado como VENDIDO.\n")
                  .append("Obrigado por usar o MyHome!");
                break;
            case "SUSPENSO":
                sb.append("Seu anuncio '").append(anuncio.getTitulo())
                  .append("' foi SUSPENSO.\n")
                  .append("Entre em contato para mais informacoes.");
                break;
            case "REATIVADO":
                sb.append("Seu anuncio '").append(anuncio.getTitulo())
                  .append("' foi REATIVADO e esta em modo rascunho.\n")
                  .append("Envie-o novamente para moderacao quando estiver pronto.");
                break;
            default:
                sb.append("Houve uma atualizacao no seu anuncio '")
                  .append(anuncio.getTitulo()).append("'.\n")
                  .append("Evento: ").append(evento);
        }

        sb.append("\n\n--\nEquipe MyHome Classificados");
        return sb.toString();
    }

    public void setStrategy(NotificacaoStrategy strategy) {
        this.strategy = strategy;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
