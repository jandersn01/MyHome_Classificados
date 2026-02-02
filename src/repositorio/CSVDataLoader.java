package repositorio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.anuncio.Anuncio;
import model.imovel.*;
import model.usuario.Usuario;
import padrao_Facade.Fachada;

/**
 * Classe para carregar dados de arquivos CSV.
 * E1: Povoar os dados automaticamente a partir de arquivos CSV.
 */
public class CSVDataLoader {

    private Fachada fachada;

    public CSVDataLoader() {
        this.fachada = new Fachada();
    }

    public CSVDataLoader(Fachada fachada) {
        this.fachada = fachada;
    }

    /**
     * Carrega usuários de um arquivo CSV.
     * Formato: id;nome;email;telefone
     */
    public List<Usuario> carregarUsuarios(String arquivo) {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader br = obterBufferedReader(arquivo)) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                // Pula cabeçalho
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                Usuario usuario = parsearLinhaUsuario(linha);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            // Retorna lista vazia em caso de erro
        }

        return usuarios;
    }

    /**
     * Carrega anúncios de um arquivo CSV.
     * Formato: titulo;descricao;preco;tipoImovel;localizacao;area;tipoAnuncio;fotos
     */
    public List<Anuncio> carregarAnuncios(String arquivo, List<Usuario> usuarios) {
        List<Anuncio> anuncios = new ArrayList<>();

        try (BufferedReader br = obterBufferedReader(arquivo)) {
            String linha;
            boolean primeiraLinha = true;
            int index = 0;

            while ((linha = br.readLine()) != null) {
                // Pula cabeçalho
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Atribui usuário de forma circular
                Usuario anunciante = null;
                if (!usuarios.isEmpty()) {
                    anunciante = usuarios.get(index % usuarios.size());
                }

                Anuncio anuncio = parsearLinhaAnuncio(linha, anunciante);
                if (anuncio != null) {
                    anuncios.add(anuncio);
                    index++;
                }
            }
        } catch (IOException e) {
            // Retorna lista vazia em caso de erro
        }

        return anuncios;
    }

    /**
     * Obtém BufferedReader do arquivo (tenta classpath primeiro, depois filesystem).
     */
    private BufferedReader obterBufferedReader(String arquivo) throws IOException {
        // Tenta carregar do classpath
        InputStream is = getClass().getClassLoader().getResourceAsStream(arquivo);

        if (is != null) {
            return new BufferedReader(new InputStreamReader(is));
        }

        // Se não encontrar, tenta do filesystem
        return new BufferedReader(new FileReader(arquivo));
    }

    /**
     * Parseia uma linha CSV para criar um Usuario.
     */
    private Usuario parsearLinhaUsuario(String linha) {
        try {
            String[] campos = linha.split(";");
            if (campos.length < 4) return null;

            return new Usuario(
                campos[0].trim(),  // id
                campos[1].trim(),  // nome
                campos[2].trim(),  // email
                campos[3].trim()   // telefone
            );
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parseia uma linha CSV para criar um Anuncio.
     */
    private Anuncio parsearLinhaAnuncio(String linha, Usuario anunciante) {
        try {
            String[] campos = linha.split(";");
            if (campos.length < 7) return null;

            String titulo = campos[0].trim();
            String descricao = campos[1].trim();
            double preco = Double.parseDouble(campos[2].trim());
            String tipoImovel = campos[3].trim();
            String localizacao = campos[4].trim();
            double area = Double.parseDouble(campos[5].trim());
            String tipoAnuncio = campos[6].trim();

            // Cria o imóvel baseado no tipo
            Imovel imovel = criarImovel(tipoImovel, titulo, preco, descricao, localizacao, area);

            // Cria o anúncio
            Anuncio anuncio = new Anuncio(titulo, preco, imovel, anunciante);
            anuncio.setDescricao(descricao);
            anuncio.setTipoAnuncio(tipoAnuncio);

            // Adiciona fotos se existirem
            if (campos.length > 7 && !campos[7].trim().isEmpty()) {
                String[] fotos = campos[7].split(",");
                for (String foto : fotos) {
                    anuncio.adicionarFoto(foto.trim());
                }
            } else {
                anuncio.adicionarFoto("img/default.jpg");
            }

            return anuncio;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Cria um imóvel baseado no tipo.
     */
    private Imovel criarImovel(String tipo, String titulo, double preco,
                               String descricao, String localizacao, double area) {
        switch (tipo.toLowerCase()) {
            case "casa":
                return new Casa(titulo, preco, descricao, localizacao, area, true, 3);
            case "apartamento":
                return new Apartamento(titulo, preco, descricao, localizacao, area, 1, true, 2);
            case "terreno":
                return new Terreno(titulo, preco, descricao, localizacao, area, "Residencial");
            case "sala comercial":
            case "salacomercial":
                return new SalaComercial(titulo, preco, descricao, localizacao, area, 10, true);
            case "galpao":
            case "galpão":
                return new Galpao(titulo, preco, descricao, localizacao, area, 6.0, 5000);
            default:
                return new Casa(titulo, preco, descricao, localizacao, area, false, 2);
        }
    }

    /**
     * Popula o sistema com dados dos arquivos CSV padrão.
     */
    public void popularSistema() {
        List<Usuario> usuarios = carregarUsuarios("data/usuarios.csv");
        List<Anuncio> anuncios = carregarAnuncios("data/anuncios.csv", usuarios);

        fachada.setUsuarios(usuarios);
        fachada.setAnuncios(anuncios);
    }

    /**
     * Retorna informações sobre os dados carregados.
     */
    public String getInfoDadosCarregados(List<Usuario> usuarios, List<Anuncio> anuncios) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Dados Carregados do CSV ===\n");
        sb.append("Usuarios: ").append(usuarios.size()).append("\n");
        sb.append("Anuncios: ").append(anuncios.size()).append("\n");
        return sb.toString();
    }

    public Fachada getFachada() {
        return fachada;
    }

    public void setFachada(Fachada fachada) {
        this.fachada = fachada;
    }
}
