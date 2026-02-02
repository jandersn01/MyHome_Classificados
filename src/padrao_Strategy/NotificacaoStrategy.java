package padrao_Strategy;

/**
 * Strategy - Interface para estratégias de notificação.
 * RF05: Notificações podem ser enviadas via Email, SMS.
 */
public interface NotificacaoStrategy {

    /**
     * Envia uma mensagem para o destinatário especificado.
     * @param destinatario Email, telefone ou identificador do destinatário
     * @param mensagem Conteúdo da mensagem a ser enviada
     * @return Resultado do envio (mensagem de sucesso ou erro)
     */
    String enviarMensagem(String destinatario, String mensagem);
}
