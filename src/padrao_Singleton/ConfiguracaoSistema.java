package padrao_Singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Singleton - Configuração centralizada do sistema.
 * RF07: O sistema deve carregar configurações de uma fonte única e acessível globalmente.
 */
public class ConfiguracaoSistema {

    private static ConfiguracaoSistema instancia;
    private Properties propriedades;

    // Construtor privado para impedir instanciação externa
    private ConfiguracaoSistema() {
        propriedades = new Properties();
        carregarConfiguracoes();
    }

    /**
     * Retorna a instância única do Singleton (thread-safe com double-checked locking).
     */
    public static ConfiguracaoSistema getInstance() {
        if (instancia == null) {
            synchronized (ConfiguracaoSistema.class) {
                if (instancia == null) {
                    instancia = new ConfiguracaoSistema();
                }
            }
        }
        return instancia;
    }

    /**
     * Carrega as configurações do arquivo config.properties.
     */
    private void carregarConfiguracoes() {
        try {
            // Tenta carregar do classpath primeiro
            InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");

            if (input == null) {
                // Se não encontrar no classpath, tenta carregar do diretório src
                input = new FileInputStream("src/config.properties");
            }

            propriedades.load(input);
            input.close();
        } catch (IOException e) {
            // Se não conseguir carregar, usa valores padrão
            setValoresPadrao();
        }
    }

    /**
     * Define valores padrão caso o arquivo de configuração não seja encontrado.
     */
    private void setValoresPadrao() {
        propriedades.setProperty("taxa.comissao", "0.05");
        propriedades.setProperty("limite.upload.fotos", "10");
        propriedades.setProperty("termos.proibidos", "spam,fraude,golpe,falso");
        propriedades.setProperty("descricao.tamanho.minimo", "20");
        propriedades.setProperty("preco.minimo", "1000");
        propriedades.setProperty("preco.maximo", "100000000");
        propriedades.setProperty("fotos.minimo", "1");
        propriedades.setProperty("email.smtp.host", "smtp.gmail.com");
        propriedades.setProperty("email.smtp.port", "587");
        propriedades.setProperty("email.smtp.auth", "true");
        propriedades.setProperty("email.smtp.starttls", "true");
    }

    /**
     * Recarrega as configurações do arquivo.
     */
    public void recarregarConfiguracoes() {
        carregarConfiguracoes();
    }

    /**
     * Carrega configurações de um arquivo específico.
     */
    public void carregarConfiguracoes(String arquivo) {
        try (FileInputStream input = new FileInputStream(arquivo)) {
            propriedades.load(input);
        } catch (IOException e) {
            // Mantém configurações atuais
        }
    }

    // Getters para propriedades específicas

    public double getTaxaComissao() {
        return getPropriedadeDouble("taxa.comissao");
    }

    public int getLimiteUploadFotos() {
        return getPropriedadeInt("limite.upload.fotos");
    }

    public List<String> getTermosProibidos() {
        String termos = getPropriedade("termos.proibidos");
        if (termos == null || termos.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(termos.split(","));
    }

    public int getDescricaoTamanhoMinimo() {
        return getPropriedadeInt("descricao.tamanho.minimo");
    }

    public double getPrecoMinimo() {
        return getPropriedadeDouble("preco.minimo");
    }

    public double getPrecoMaximo() {
        return getPropriedadeDouble("preco.maximo");
    }

    public int getFotosMinimo() {
        return getPropriedadeInt("fotos.minimo");
    }

    public String getEmailSmtpHost() {
        return getPropriedade("email.smtp.host");
    }

    public int getEmailSmtpPort() {
        return getPropriedadeInt("email.smtp.port");
    }

    public String getEmailUsuario() {
        return getPropriedade("email.usuario");
    }

    public String getEmailSenha() {
        return getPropriedade("email.senha");
    }

    // Métodos genéricos para obter propriedades

    public String getPropriedade(String chave) {
        return propriedades.getProperty(chave);
    }

    public int getPropriedadeInt(String chave) {
        String valor = propriedades.getProperty(chave);
        if (valor == null) return 0;
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getPropriedadeDouble(String chave) {
        String valor = propriedades.getProperty(chave);
        if (valor == null) return 0.0;
        try {
            return Double.parseDouble(valor.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public boolean getPropriedadeBoolean(String chave) {
        String valor = propriedades.getProperty(chave);
        return "true".equalsIgnoreCase(valor);
    }

    /**
     * Retorna uma representação das configurações atuais (para debug/log).
     */
    public String getConfiguracoesInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Configuracoes do Sistema ===\n");
        sb.append("Taxa de Comissao: ").append(getTaxaComissao() * 100).append("%\n");
        sb.append("Limite de Fotos: ").append(getLimiteUploadFotos()).append("\n");
        sb.append("Termos Proibidos: ").append(getTermosProibidos()).append("\n");
        sb.append("Descricao Minima: ").append(getDescricaoTamanhoMinimo()).append(" caracteres\n");
        sb.append("Preco Minimo: R$ ").append(getPrecoMinimo()).append("\n");
        sb.append("Preco Maximo: R$ ").append(getPrecoMaximo()).append("\n");
        return sb.toString();
    }
}
