package model;

public class Usuario {
	private Long id;
	private String nome;
	private String email;
	private String senha;

	public Usuario(String nome, String email, String senha,Long id) {
		this.email = email;
		this.senha = senha;
		this.id = id;
	}

	public Usuario(String nome, String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public Usuario() {
	
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
 }
	public boolean equals(Object obj) {
		Usuario outroUsuario=(Usuario)obj;
		return id.equals(outroUsuario.id);
	}

	public String toString() {
		return "  \n nome:"+ nome + " \n email:"+ email;
	}
}
