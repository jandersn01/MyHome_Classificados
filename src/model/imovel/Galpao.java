package model.imovel;

public class Galpao extends Imovel {

    private double peDireito;
    private int capacidadeCarga;

    public Galpao() {
        super();
    }

    public Galpao(String titulo, double preco, String descricao, String localizacao,
                  double area, double peDireito, int capacidadeCarga) {
        super(titulo, preco, descricao, localizacao, area);
        this.peDireito = peDireito;
        this.capacidadeCarga = capacidadeCarga;
    }

    // Construtor de cópia
    public Galpao(Galpao outro) {
        super(outro);
        this.peDireito = outro.peDireito;
        this.capacidadeCarga = outro.capacidadeCarga;
    }

    public double getPeDireito() {
        return peDireito;
    }

    public void setPeDireito(double peDireito) {
        this.peDireito = peDireito;
    }

    public int getCapacidadeCarga() {
        return capacidadeCarga;
    }

    public void setCapacidadeCarga(int capacidadeCarga) {
        this.capacidadeCarga = capacidadeCarga;
    }

    @Override
    public String getTipoImovel() {
        return "Galpao";
    }

    @Override
    public Imovel clone() {
        return new Galpao(this);
    }
}
