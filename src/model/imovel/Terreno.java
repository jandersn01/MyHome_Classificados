package model.imovel;

public class Terreno extends Imovel {

    private String tipoZoneamento;

    public Terreno() {
        super();
    }

    public Terreno(String titulo, double preco, String descricao, String localizacao,
                   double area, String tipoZoneamento) {
        super(titulo, preco, descricao, localizacao, area);
        this.tipoZoneamento = tipoZoneamento;
    }

    // Construtor de cópia
    public Terreno(Terreno outro) {
        super(outro);
        this.tipoZoneamento = outro.tipoZoneamento;
    }

    public String getTipoZoneamento() {
        return tipoZoneamento;
    }

    public void setTipoZoneamento(String tipoZoneamento) {
        this.tipoZoneamento = tipoZoneamento;
    }

    @Override
    public String getTipoImovel() {
        return "Terreno";
    }

    @Override
    public Imovel clone() {
        return new Terreno(this);
    }
}
