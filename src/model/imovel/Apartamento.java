package model.imovel;

public class Apartamento extends Imovel {

    private int andar;
    private boolean elevador;
    private int numeroQuartos;

    public Apartamento() {
        super();
    }

    public Apartamento(String titulo, double preco, String descricao, String localizacao,
                       double area, int andar, boolean elevador, int numeroQuartos) {
        super(titulo, preco, descricao, localizacao, area);
        this.andar = andar;
        this.elevador = elevador;
        this.numeroQuartos = numeroQuartos;
    }

    // Construtor de cópia
    public Apartamento(Apartamento outro) {
        super(outro);
        this.andar = outro.andar;
        this.elevador = outro.elevador;
        this.numeroQuartos = outro.numeroQuartos;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public boolean temElevador() {
        return elevador;
    }

    public void setElevador(boolean elevador) {
        this.elevador = elevador;
    }

    public int getNumeroQuartos() {
        return numeroQuartos;
    }

    public void setNumeroQuartos(int numeroQuartos) {
        this.numeroQuartos = numeroQuartos;
    }

    @Override
    public String getTipoImovel() {
        return "Apartamento";
    }

    @Override
    public Imovel clone() {
        return new Apartamento(this);
    }
}
