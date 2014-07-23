package persistence;

import model.Usuario;

public interface UsuarioDAO {

	void registraConta(Usuario usuario);

	Usuario buscaPorLoginESenha(String email, String senha);

}