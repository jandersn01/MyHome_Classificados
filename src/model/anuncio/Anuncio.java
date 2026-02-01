package model.anuncio;

import model.imovel.Imovel;
import model.usuario.Usuario;
import padrao_Prototype.Prototype;
import padrao_State.EstadoAnuncio;
import padrao_Strategy.NotificacaoStrategy;

public class Anuncio implements Prototype<Anuncio>{
	private Imovel imovel;
	private Usuario anunciante;
	private String titulo;
	private Double preco;
	private String pathImagem;
	private EstadoAnuncio estado;
	private NotificacaoStrategy estrategia;
	
	//Construtorde copia implementar
	public Anuncio(Anuncio outro) {
        if (outro.imovel != null) {
            this.imovel = outro.imovel.clone();
        }}
	
	@Override
    public Anuncio clone() {
        return new Anuncio(this);
    }
        
}
