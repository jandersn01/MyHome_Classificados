package model.imovel;

public class SalaComercial extends Imovel {

    private int capacidadePessoas;
    private boolean estacionamento;

    public SalaComercial() {
        super();
    }

    public SalaComercial(String titulo, double preco, String descricao, String localizacao,
                         double area, int capacidadePessoas, boolean estacionamento) {
        super(titulo, preco, descricao, localizacao, area);
        this.capacidadePessoas = capacidadePessoas;
        this.estacionamento = estacionamento;
    }

    // Construtor de cópia
    public SalaComercial(SalaComercial outra) {
        super(outra);
        this.capacidadePessoas = outra.capacidadePessoas;
        this.estacionamento = outra.estacionamento;
    }

    public int getCapacidade() {
        return capacidadePessoas;
    }

    public void setCapacidadePessoas(int capacidadePessoas) {
        this.capacidadePessoas = capacidadePessoas;
    }

    public boolean temEstacionamento() {
        return estacionamento;
    }

    public void setEstacionamento(boolean estacionamento) {
        this.estacionamento = estacionamento;
    }

    @Override
    public String getTipoImovel() {
        return "Sala Comercial";
    }

    @Override
    public Imovel clone() {
        return new SalaComercial(this);
    }
}
