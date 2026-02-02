package padrao_Strategy;

import padrao_Singleton.ConfiguracaoSistema;

/**
 * Strategy concreta para envio de notificações por Email (SMTP).
 * RF05: Uma das opções deve ser implementada na prática.
 *
 * Implementação simulada - em produção seria integrada com JavaMail ou
 * outra biblioteca de envio de emails.
 */
public class NotificacaoEmail implements NotificacaoStrategy {

    private String smtpHost;
    private int smtpPort;
    private String usuario;
    private String senha;

    public NotificacaoEmail() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        this.smtpHost = config.getEmailSmtpHost();
        this.smtpPort = config.getEmailSmtpPort();
        this.usuario = config.getEmailUsuario();
        this.senha = config.getEmailSenha();
    }

    public NotificacaoEmail(String smtpHost, int smtpPort, String usuario, String senha) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.usuario = usuario;
        this.senha = senha;
    }

    @Override
    public String enviarMensagem(String destinatario, String mensagem) {
        // Simulação de envio de email
        // Em produção, aqui seria feita a integração com JavaMail ou API de email
        StringBuilder sb = new StringBuilder();
        sb.append("[EMAIL SIMULADO]\n");
        sb.append("Servidor: ").append(smtpHost).append(":").append(smtpPort).append("\n");
        sb.append("De: ").append(usuario != null ? usuario : "nao-configurado").append("\n");
        sb.append("Para: ").append(destinatario).append("\n");
        sb.append("Assunto: MyHome Classificados - Notificacao\n");
        sb.append("---\n");
        sb.append(mensagem).append("\n");
        sb.append("---\n");
        sb.append("(Configure email.usuario e email.senha no config.properties para envio real)");
        return sb.toString();
    }

    // Setters para configuração manual
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
