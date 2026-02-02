package padrao_Builder;

import model.anuncio.Anuncio;
import model.imovel.Imovel;
import model.usuario.Usuario;

/**
 * Builder - Interface para construção de anúncios.
 * RF08: Processo de criação de anúncio deve ser guiado.
 */
public interface AnuncioBuilder {

    /**
     * Reinicia o builder para criar um novo anúncio.
     */
    AnuncioBuilder reset();

    /**
     * Define o título do anúncio (obrigatório).
     */
    AnuncioBuilder setTitulo(String titulo);

    /**
     * Define a descrição do anúncio.
     */
    AnuncioBuilder setDescricao(String descricao);

    /**
     * Define o preço do anúncio (obrigatório).
     */
    AnuncioBuilder setPreco(double preco);

    /**
     * Define o imóvel do anúncio (obrigatório).
     */
    AnuncioBuilder setImovel(Imovel imovel);

    /**
     * Define o anunciante do anúncio.
     */
    AnuncioBuilder setAnunciante(Usuario anunciante);

    /**
     * Adiciona uma foto ao anúncio.
     */
    AnuncioBuilder adicionarFoto(String pathFoto);

    /**
     * Constrói e retorna o anúncio.
     */
    Anuncio build();
}
