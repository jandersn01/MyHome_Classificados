package model.usuario;

import model.anuncio.Anuncio;
import padrao_Observer.AnuncioObserver;

public class Usuario implements AnuncioObserver{
	 private String id;
	    private String nome;
	    private String email;
	    private String telefone;
	    private String canalPreferido;

	    public Usuario() {
	    }

	    public Usuario(String id, String nome, String email, String telefone, String canalPreferido) {
	        this.id = id;
	        this.nome = nome;
	        this.email = email;
	        this.telefone = telefone;
	        this.canalPreferido = canalPreferido;
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getNome() {
	        return nome;
	    }

	    public void setNome(String nome) {
	        this.nome = nome;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getTelefone() {
	        return telefone;
	    }

	    public void setTelefone(String telefone) {
	        this.telefone = telefone;
	    }

	    public String getCanalPreferido() {
	        return canalPreferido;
	    }

	    public void setCanalPreferido(String canalPreferido) {
	        this.canalPreferido = canalPreferido;
	    }

	    @Override
	    public String toString() {
	        return "Usuario{" +
	                "id='" + id + '\'' +
	                ", nome='" + nome + '\'' +
	                ", email='" + email + '\'' +
	                ", canalPreferido='" + canalPreferido + '\'' +
	                '}';
	    }

		@Override
		public void atualizar(Anuncio a) {
			// TODO Auto-generated method stub
			
		}
}
