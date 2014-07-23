package persistence;

import static util.Ferramentas.getConection;
import static util.Ferramentas.releaseDatabaseResources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.Usuario;

public class UsuarioBancoDeDadosDAO implements UsuarioDAO {

	@Override
	public void registraConta(Usuario usuario) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConection();
			String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?,?, ?)";
			statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getEmail());
			statement.setString(3, usuario.getSenha());
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			long key = keys.getLong(1);
			keys.close();
			usuario.setId(key);
		} catch (Exception e) {
			throw new RuntimeException(
					"Erro ao cadastrar usuario, tente novamente mais tarde", e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}
	}

	@Override
	public Usuario buscaPorLoginESenha(String email, String senha) {
		Usuario usuario = null;
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConection();

			String sql = "SELECT * FROM Usuario WHERE email = ? and senha = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, senha);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				usuario = new Usuario(resultSet.getString("nome"),
						resultSet.getString("email"),
						resultSet.getString("senha"), resultSet.getLong("id"));
			}

			resultSet.close();
			return usuario;
		} catch (Exception e) {
			throw new RuntimeException("Erro.", e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}

	}
}