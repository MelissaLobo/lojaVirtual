package model;

import persistence.UsuarioBancoDeDadosDAO;
import persistence.UsuarioDAO;

public class GerenciadorDeUsuarios {

	UsuarioDAO banco = new UsuarioBancoDeDadosDAO();

	public void criarUmaNovaConta(String nome, String email, String senha) {

		Usuario usuario = new Usuario(nome, email, senha);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);

		banco.registraConta(usuario);
	}

	public Usuario tentaLogar(String email, String senha) {
		return banco.buscaPorLoginESenha(email, senha);
	}
}
