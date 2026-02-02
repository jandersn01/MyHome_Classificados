package padrao_FactoryMethod;

import model.imovel.Casa;
import model.imovel.Imovel;

/**
 * Factory concreta para criação de objetos Casa.
 */
public class CasaFactory extends ImovelFactory {

    private boolean quintalPadrao = false;
    private int quartosPadrao = 2;

    @Override
    public Imovel criarImovel() {
        Casa casa = new Casa();
        casa.setQuintal(quintalPadrao);
        casa.setNumeroQuartos(quartosPadrao);
        return casa;
    }

    @Override
    public Imovel criarImovel(String titulo, double preco, String descricao,
                              String localizacao, double area) {
        return new Casa(titulo, preco, descricao, localizacao, area, quintalPadrao, quartosPadrao);
    }

    /**
     * Método específico para criar casa com todos os atributos.
     */
    public Casa criarCasa(String titulo, double preco, String descricao,
                          String localizacao, double area, boolean quintal, int numeroQuartos) {
        return new Casa(titulo, preco, descricao, localizacao, area, quintal, numeroQuartos);
    }

    public void setQuintalPadrao(boolean quintal) {
        this.quintalPadrao = quintal;
    }

    public void setQuartosPadrao(int quartos) {
        this.quartosPadrao = quartos;
    }
}
