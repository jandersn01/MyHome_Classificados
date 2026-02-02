package padrao_FactoryMethod;

import model.imovel.Apartamento;
import model.imovel.Imovel;

/**
 * Factory concreta para criação de objetos Apartamento.
 */
public class ApartamentoFactory extends ImovelFactory {

    private int andarPadrao = 1;
    private boolean elevadorPadrao = true;
    private int quartosPadrao = 2;

    @Override
    public Imovel criarImovel() {
        Apartamento apt = new Apartamento();
        apt.setAndar(andarPadrao);
        apt.setElevador(elevadorPadrao);
        apt.setNumeroQuartos(quartosPadrao);
        return apt;
    }

    @Override
    public Imovel criarImovel(String titulo, double preco, String descricao,
                              String localizacao, double area) {
        return new Apartamento(titulo, preco, descricao, localizacao, area,
                               andarPadrao, elevadorPadrao, quartosPadrao);
    }

    /**
     * Método específico para criar apartamento com todos os atributos.
     */
    public Apartamento criarApartamento(String titulo, double preco, String descricao,
                                        String localizacao, double area,
                                        int andar, boolean elevador, int numeroQuartos) {
        return new Apartamento(titulo, preco, descricao, localizacao, area,
                               andar, elevador, numeroQuartos);
    }

    public void setAndarPadrao(int andar) {
        this.andarPadrao = andar;
    }

    public void setElevadorPadrao(boolean elevador) {
        this.elevadorPadrao = elevador;
    }

    public void setQuartosPadrao(int quartos) {
        this.quartosPadrao = quartos;
    }
}
