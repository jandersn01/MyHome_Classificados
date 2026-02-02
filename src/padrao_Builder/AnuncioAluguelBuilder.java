package padrao_Builder;

import model.anuncio.Anuncio;
import model.imovel.Imovel;
import model.usuario.Usuario;

/**
 * Builder concreto para anúncios de ALUGUEL.
 * RF08: Criação guiada de anúncios.
 */
public class AnuncioAluguelBuilder implements AnuncioBuilder {

    private Anuncio anuncio;
    private double valorCondominio;
    private int tempoMinimoContrato; // em meses

    public AnuncioAluguelBuilder() {
        this.reset();
    }

    @Override
    public AnuncioBuilder reset() {
        this.anuncio = new Anuncio();
        this.anuncio.setTipoAnuncio("ALUGUEL");
        this.valorCondominio = 0;
        this.tempoMinimoContrato = 12; // padrão: 12 meses
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
     * Define o valor do condomínio (específico para aluguel).
     */
    public AnuncioAluguelBuilder setValorCondominio(double valor) {
        this.valorCondominio = valor;
        return this;
    }

    /**
     * Define o tempo mínimo de contrato em meses (específico para aluguel).
     */
    public AnuncioAluguelBuilder setTempoMinimoContrato(int meses) {
        this.tempoMinimoContrato = meses;
        return this;
    }

    @Override
    public Anuncio build() {
        StringBuilder descricaoExtra = new StringBuilder();

        if (valorCondominio > 0) {
            descricaoExtra.append(" | Condominio: R$ ").append(valorCondominio);
        }

        if (tempoMinimoContrato > 0) {
            descricaoExtra.append(" | Contrato minimo: ").append(tempoMinimoContrato).append(" meses");
        }

        if (descricaoExtra.length() > 0) {
            String descricaoAtual = anuncio.getDescricao();
            if (descricaoAtual != null) {
                anuncio.setDescricao(descricaoAtual + descricaoExtra.toString());
            } else {
                anuncio.setDescricao(descricaoExtra.toString());
            }
        }

        Anuncio resultado = this.anuncio;
        this.reset(); // Prepara para próxima construção
        return resultado;
    }

    public double getValorCondominio() {
        return valorCondominio;
    }

    public int getTempoMinimoContrato() {
        return tempoMinimoContrato;
    }
}
