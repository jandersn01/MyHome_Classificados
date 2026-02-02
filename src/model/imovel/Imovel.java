package model.imovel;

import padrao_Prototype.Prototype;

public abstract class Imovel implements Prototype<Imovel>  {
	/**
	 * Classe abstrata base para todos os tipos de imoveis.
	 * Implementa ImovelPrototype para suportar clonagem (RF02).
	 */

	    protected String titulo;
	    protected double preco;
	    protected String descricao;
	    protected String localizacao;
	    protected double area;

	    public Imovel() {
	    }

	    public Imovel(String titulo, double preco, String descricao, String localizacao, double area) {
	        this.titulo = titulo;
	        this.preco = preco;
	        this.descricao = descricao;
	        this.localizacao = localizacao;
	        this.area = area;
	    }
	    
	    // Construtor de cópia para suportar Prototype
	    protected Imovel(Imovel outro) {
	        this.titulo = outro.titulo;
	        this.preco = outro.preco;
	        this.descricao = outro.descricao;
	        this.localizacao = outro.localizacao;
	        this.area = outro.area;
	    }
	    

	    public String getTitulo() {
	        return titulo;
	    }

	    public void setTitulo(String titulo) {
	        this.titulo = titulo;
	    }

	    public double getPreco() {
	        return preco;
	    }

	    public void setPreco(double preco) {
	        this.preco = preco;
	    }

	    public String getDescricao() {
	        return descricao;
	    }

	    public void setDescricao(String descricao) {
	        this.descricao = descricao;
	    }

	    public String getLocalizacao() {
	        return localizacao;
	    }

	    public void setLocalizacao(String localizacao) {
	        this.localizacao = localizacao;
	    }

	    public double getArea() {
	        return area;
	    }

	    public void setArea(double area) {
	        this.area = area;
	    }

	    public abstract String getTipoImovel();
	    
	    @Override
	    public abstract Imovel clone(); // Força as subclasses a implementar
	}
	

