package padrao_Builder;

import model.anuncio.Anuncio;
import model.imovel.Imovel;
import model.usuario.Usuario;

/**
 * Builder concreto para anúncios de VENDA.
 * RF08: Criação guiada de anúncios.
 */
public class AnuncioVendaBuilder implements AnuncioBuilder {

    private Anuncio anuncio;
    private boolean aceitaFinanciamento;
    private boolean aceitaPermuta;

    public AnuncioVendaBuilder() {
        this.reset();
    }

    @Override
    public AnuncioBuilder reset() {
        this.anuncio = new Anuncio();
        this.anuncio.setTipoAnuncio("VENDA");
        this.aceitaFinanciamento = false;
        this.aceitaPermuta = false;
        return this;
    }

    @Override
    public AnuncioBuilder setTitulo(String titulo) {
        this.anuncio.setTitulo(titulo);
        return this;
    }

    @Override
    public AnuncioBuilder setDescricao(String descricao) {
        this.anuncio.setDescricao(descricao);
        return this;
    }

    @Override
    public AnuncioBuilder setPreco(double preco) {
        this.anuncio.setPreco(preco);
        return this;
    }

    @Override
    public AnuncioBuilder setImovel(Imovel imovel) {
        this.anuncio.setImovel(imovel);
        return this;
    }

    @Override
    public AnuncioBuilder setAnunciante(Usuario anunciante) {
        this.anuncio.setAnunciante(anunciante);
        return this;
    }

    @Override
    public AnuncioBuilder adicionarFoto(String pathFoto) {
        this.anuncio.adicionarFoto(pathFoto);
        return this;
    }

    /**
     * Define se aceita financiamento (específico para venda).
     */
    public AnuncioVendaBuilder setAceitaFinanciamento(boolean aceita) {
        this.aceitaFinanciamento = aceita;
        return this;
    }

    /**
     * Define se aceita permuta (específico para venda).
     */
    public AnuncioVendaBuilder setAceitaPermuta(boolean aceita) {
        this.aceitaPermuta = aceita;
        return this;
    }

    @Override
    public Anuncio build() {
        // Adiciona informações específicas de venda na descrição
        String descricaoExtra = "";
        if (aceitaFinanciamento) {
            descricaoExtra += " | Aceita Financiamento";
        }
        if (aceitaPermuta) {
            descricaoExtra += " | Aceita Permuta";
        }

        if (!descricaoExtra.isEmpty()) {
            String descricaoAtual = anuncio.getDescricao();
            if (descricaoAtual != null) {
                anuncio.setDescricao(descricaoAtual + descricaoExtra);
            } else {
                anuncio.setDescricao(descricaoExtra);
            }
        }

        Anuncio resultado = this.anuncio;
        this.reset(); // Prepara para próxima construção
        return resultado;
    }

    // Getters para verificar configurações
    public boolean isAceitaFinanciamento() {
        return aceitaFinanciamento;
    }

    public boolean isAceitaPermuta() {
        return aceitaPermuta;
    }
}
