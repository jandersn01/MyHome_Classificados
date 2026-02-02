package padrao_Builder;

import java.util.List;

import model.anuncio.Anuncio;
import model.imovel.Imovel;
import model.usuario.Usuario;

/**
 * Diretor - Orquestra a construção de anúncios usando os builders.
 * RF08: Processo de criação de anúncio deve ser guiado.
 */
public class DiretorAnuncio {

    private AnuncioBuilder builder;

    public DiretorAnuncio() {
        // Builder padrão é de venda
        this.builder = new AnuncioVendaBuilder();
    }

    public DiretorAnuncio(AnuncioBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(AnuncioBuilder builder) {
        this.builder = builder;
    }

    /**
     * Constrói um anúncio com informações mínimas obrigatórias.
     * RF01: Título, tipo do imóvel e preço são obrigatórios.
     */
    public Anuncio construirAnuncioMinimo(String titulo, double preco,
                                          Imovel imovel, Usuario anunciante) {
        return builder.reset()
                .setTitulo(titulo)
                .setPreco(preco)
                .setImovel(imovel)
                .setAnunciante(anunciante)
                .adicionarFoto("img/default.jpg") // Foto padrão
                .build();
    }


    public Anuncio construirAnuncioCompleto(String titulo, String descricao, double preco,
                                            Imovel imovel, Usuario anunciante, List<String> fotos) {
        builder.reset()
               .setTitulo(titulo)
               .setDescricao(descricao)
               .setPreco(preco)
               .setImovel(imovel)
               .setAnunciante(anunciante);

        // Adiciona todas as fotos
        if (fotos != null) {
            for (String foto : fotos) {
                builder.adicionarFoto(foto);
            }
        }

        return builder.build();
    }

    /**
     * Constrói um anúncio de venda com opções de financiamento.
     */
    public Anuncio construirAnuncioVendaComFinanciamento(String titulo, String descricao,
                                                         double preco, Imovel imovel,
                                                         Usuario anunciante, boolean aceitaPermuta) {
        AnuncioVendaBuilder vendaBuilder = new AnuncioVendaBuilder();
        vendaBuilder.setTitulo(titulo);
        vendaBuilder.setDescricao(descricao);
        vendaBuilder.setPreco(preco);
        vendaBuilder.setImovel(imovel);
        vendaBuilder.setAnunciante(anunciante);
        vendaBuilder.setAceitaFinanciamento(true);
        vendaBuilder.setAceitaPermuta(aceitaPermuta);
        vendaBuilder.adicionarFoto("img/default.jpg");
        return vendaBuilder.build();
    }

    /**
     * Constrói um anúncio de aluguel com informações de contrato.
     */
    public Anuncio construirAnuncioAluguel(String titulo, String descricao, double valorAluguel,
                                           double valorCondominio, int tempoMinimo,
                                           Imovel imovel, Usuario anunciante) {
        AnuncioAluguelBuilder aluguelBuilder = new AnuncioAluguelBuilder();
        aluguelBuilder.setTitulo(titulo);
        aluguelBuilder.setDescricao(descricao);
        aluguelBuilder.setPreco(valorAluguel);
        aluguelBuilder.setImovel(imovel);
        aluguelBuilder.setAnunciante(anunciante);
        aluguelBuilder.setValorCondominio(valorCondominio);
        aluguelBuilder.setTempoMinimoContrato(tempoMinimo);
        aluguelBuilder.adicionarFoto("img/default.jpg");
        return aluguelBuilder.build();
    }
}
