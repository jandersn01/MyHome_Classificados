package padrao_FactoryMethod;

import model.imovel.Galpao;
import model.imovel.Imovel;

/**
 * Factory concreta para criação de objetos Galpao.
 */
public class GalpaoFactory extends ImovelFactory {

    private double peDireitoPadrao = 6.0;
    private int capacidadeCargaPadrao = 5000;

    @Override
    public Imovel criarImovel() {
        Galpao galpao = new Galpao();
        galpao.setPeDireito(peDireitoPadrao);
        galpao.setCapacidadeCarga(capacidadeCargaPadrao);
        return galpao;
    }

    @Override
    public Imovel criarImovel(String titulo, double preco, String descricao,
                              String localizacao, double area) {
        return new Galpao(titulo, preco, descricao, localizacao, area,
                          peDireitoPadrao, capacidadeCargaPadrao);
    }

    /**
     * Método específico para criar galpão com todos os atributos.
     */
    public Galpao criarGalpao(String titulo, double preco, String descricao,
                              String localizacao, double area,
                              double peDireito, int capacidadeCarga) {
        return new Galpao(titulo, preco, descricao, localizacao, area,
                          peDireito, capacidadeCarga);
    }

    public void setPeDireitoPadrao(double peDireito) {
        this.peDireitoPadrao = peDireito;
    }

    public void setCapacidadeCargaPadrao(int capacidadeCarga) {
        this.capacidadeCargaPadrao = capacidadeCarga;
    }
}
