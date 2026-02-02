package padrao_FactoryMethod;

import model.imovel.Imovel;
import model.imovel.SalaComercial;

/**
 * Factory concreta para criação de objetos SalaComercial.
 */
public class SalaComercialFactory extends ImovelFactory {

    private int capacidadePadrao = 10;
    private boolean estacionamentoPadrao = true;

    @Override
    public Imovel criarImovel() {
        SalaComercial sala = new SalaComercial();
        sala.setCapacidadePessoas(capacidadePadrao);
        sala.setEstacionamento(estacionamentoPadrao);
        return sala;
    }

    @Override
    public Imovel criarImovel(String titulo, double preco, String descricao,
                              String localizacao, double area) {
        return new SalaComercial(titulo, preco, descricao, localizacao, area,
                                 capacidadePadrao, estacionamentoPadrao);
    }

    /**
     * Método específico para criar sala comercial com todos os atributos.
     */
    public SalaComercial criarSalaComercial(String titulo, double preco, String descricao,
                                            String localizacao, double area,
                                            int capacidadePessoas, boolean estacionamento) {
        return new SalaComercial(titulo, preco, descricao, localizacao, area,
                                 capacidadePessoas, estacionamento);
    }

    public void setCapacidadePadrao(int capacidade) {
        this.capacidadePadrao = capacidade;
    }

    public void setEstacionamentoPadrao(boolean estacionamento) {
        this.estacionamentoPadrao = estacionamento;
    }
}
