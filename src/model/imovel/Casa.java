package model.imovel;

public class Casa extends Imovel {

    private boolean quintal;
    private int numeroQuartos;

    public Casa() {
        super();
    }

    public Casa(String titulo, double preco, String descricao, String localizacao,
                double area, boolean quintal, int numeroQuartos) {
        super(titulo, preco, descricao, localizacao, area);
        this.quintal = quintal;
        this.numeroQuartos = numeroQuartos;
    }

    // Construtor de cópia
    public Casa(Casa outra) {
        super(outra);
        this.quintal = outra.quintal;
        this.numeroQuartos = outra.numeroQuartos;
    }

    public boolean temQuintal() {
        return quintal;
    }

    public void setQuintal(boolean quintal) {
        this.quintal = quintal;
    }

    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public void setNumeroQuartos(int numeroQuartos) {
        this.numeroQuartos = numeroQuartos;
    }

    @Override
    public String getTipoImovel() {
        return "Casa";
    }

    @Override
    public Imovel clone() {
        return new Casa(this);
    }
}
