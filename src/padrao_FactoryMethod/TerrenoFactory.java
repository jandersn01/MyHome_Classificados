package padrao_FactoryMethod;

import model.imovel.Imovel;
import model.imovel.Terreno;

/**
 * Factory concreta para criação de objetos Terreno.
 */
public class TerrenoFactory extends ImovelFactory {

    private String zoneamentoPadrao = "Residencial";

    @Override
    public Imovel criarImovel() {
        Terreno terreno = new Terreno();
        terreno.setTipoZoneamento(zoneamentoPadrao);
        return terreno;
    }

    @Override
    public Imovel criarImovel(String titulo, double preco, String descricao,
                              String localizacao, double area) {
        return new Terreno(titulo, preco, descricao, localizacao, area, zoneamentoPadrao);
    }

    /**
     * Método específico para criar terreno com todos os atributos.
     */
    public Terreno criarTerreno(String titulo, double preco, String descricao,
                                String localizacao, double area, String tipoZoneamento) {
        return new Terreno(titulo, preco, descricao, localizacao, area, tipoZoneamento);
    }

    public void setZoneamentoPadrao(String zoneamento) {
        this.zoneamentoPadrao = zoneamento;
    }
}
