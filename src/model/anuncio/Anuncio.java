package model.anuncio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.imovel.Imovel;
import model.usuario.Usuario;
import padrao_Observer.AnuncioObserver;
import padrao_Prototype.Prototype;
import padrao_State.EstadoAnuncio;
import padrao_State.RascunhoEstado;

/**
 * Classe principal que representa um anúncio de imóvel.
 * Implementa Prototype para clonagem e usa State para ciclo de vida.
 */
public class Anuncio implements Prototype<Anuncio> {

    private String id;
    private String titulo;
    private String descricao;
    private Double preco;
    private Imovel imovel;
    private Usuario anunciante;
    private LocalDateTime dataCriacao;
    private List<String> fotos;
    private EstadoAnuncio estado;
    private List<AnuncioObserver> observers;
    private String tipoAnuncio; 

    // Construtor padrão
    public Anuncio() {
        this.id = gerarId();
        this.dataCriacao = LocalDateTime.now();
        this.fotos = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.estado = new RascunhoEstado();
        this.tipoAnuncio = "VENDA";
    }

    // Construtor com parâmetros básicos
    public Anuncio(String titulo, Double preco, Imovel imovel, Usuario anunciante) {
        this();
        this.titulo = titulo;
        this.preco = preco;
        this.imovel = imovel;
        this.anunciante = anunciante;
    }

    // Construtor de cópia (Prototype)
    public Anuncio(Anuncio outro) {
        this.id = gerarId(); // Novo ID para o clone
        this.titulo = outro.titulo;
        this.descricao = outro.descricao;
        this.preco = outro.preco;
        this.tipoAnuncio = outro.tipoAnuncio;
        this.dataCriacao = LocalDateTime.now();
        this.fotos = new ArrayList<>(outro.fotos);
        this.observers = new ArrayList<>();
        this.estado = new RascunhoEstado();

        if (outro.imovel != null) {
            this.imovel = outro.imovel.clone();
        }
    }

    @Override
    public Anuncio clone() {
        return new Anuncio(this);
    }

    /**
     * Gera um ID único para o anúncio.
     */
    private String gerarId() {
        return "ANU-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }


    public void submeter() {
        estado.enviarParaModeracao(this);
    }

    public void aprovar() {
        estado.aprovar(this);
    }

    public void reprovar() {
        estado.reprovar(this);
    }

    public void vender() {
        estado.vender(this);
    }

    public void suspender() {
        estado.suspender(this);
    }

    public void reativar() {
        estado.reativar(this);
    }


    public void addObserver(AnuncioObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(AnuncioObserver observer) {
        observers.remove(observer);
    }

    public void notificarObservers(String evento) {
        for (AnuncioObserver observer : observers) {
            observer.atualizar(this, evento);
        }
    }


    public void adicionarFoto(String pathFoto) {
        if (this.fotos == null) {
            this.fotos = new ArrayList<>();
        }
        this.fotos.add(pathFoto);
    }

    public void removerFoto(String pathFoto) {
        if (this.fotos != null) {
            this.fotos.remove(pathFoto);
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Usuario getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(Usuario anunciante) {
        this.anunciante = anunciante;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public EstadoAnuncio getEstado() {
        return estado;
    }

    public String getEstadoAtual() {
        return estado != null ? estado.getNomeEstado() : "null";
    }

    public void setEstado(EstadoAnuncio estado) {
        this.estado = estado;
    }

    public String getTipoAnuncio() {
        return tipoAnuncio;
    }

    public void setTipoAnuncio(String tipoAnuncio) {
        this.tipoAnuncio = tipoAnuncio;
    }

    public List<AnuncioObserver> getObservers() {
        return observers;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", preco=" + preco +
                ", tipo='" + tipoAnuncio + '\'' +
                ", estado='" + (estado != null ? estado.getNomeEstado() : "null") + '\'' +
                ", imovel=" + (imovel != null ? imovel.getTipoImovel() : "null") +
                '}';
    }
}
