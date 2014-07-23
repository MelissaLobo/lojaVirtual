package persistence;

import static util.Ferramentas.getConection;
import static util.Ferramentas.releaseDatabaseResources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Comentario;
import model.Produto;
import model.Usuario;

public class LojaBancoDeDadosDAO implements LojaDAO {

	@Override
	public List<Produto> buscaListaDeProdutos() {

		List<Produto> produtos = new ArrayList<Produto>();
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConection();
			String sql = "SELECT * FROM produtos ORDER BY nome ASC";
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Produto produto = new Produto();
				produto.setId(resultSet.getLong("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setValor(resultSet.getInt("valor"));
				produto.setDescricao(resultSet.getString("descricao"));
				produto.setMarca(resultSet.getString("marca"));
				produto.setCategoria(Categoria.valueOf(resultSet.getString(
						"categoria").toUpperCase()));
				produtos.add(produto);
			}
			resultSet.close();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar todos os produtos", e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}
		return produtos;
	}

	@Override
	public Produto adicionaProduto(Produto produto, Usuario usuario) {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConection();
			String sql = "INSERT INTO produtos (nome, valor, descricao, marca, categoria, usuario_id ) VALUES (?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, produto.getNome());
			statement.setInt(2, produto.getValor());
			statement.setString(3, produto.getDescricao());
			statement.setString(4, produto.getMarca());
			statement.setString(5, produto.getCategoria().toString());
			statement.setLong(6, usuario.getId());

			statement.execute();

			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			long key = keys.getLong(1);
			keys.close();
			produto.setId(key);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao inserir produto", e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}
		return produto;
	}

	@Override
	public Produto buscaProdutoPorID(Long id) {
		Produto produto = new Produto();
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConection();
			String sql = "SELECT * FROM produtos where id=?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				produto = new Produto();
				produto.setId(resultSet.getLong("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setValor(resultSet.getInt("valor"));
				produto.setDescricao(resultSet.getString("descricao"));
				produto.setMarca(resultSet.getString("marca"));
				produto.setCategoria(Categoria.valueOf(resultSet.getString(
						"categoria").toUpperCase()));
			}
			resultSet.close();
		} catch (Exception e) {
			throw new RuntimeException("Erro ", e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}
		return produto;
	}

	@Override
	public List<Comentario> listagemDeComentariosPorId(Long id) {
		List<Comentario> comentarios = new ArrayList<Comentario>();
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConection();
			String sql = "SELECT * FROM comentarios where PRODUTO_ID=?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Comentario coment = new Comentario();
				coment.setNome(resultSet.getString("nome"));
				coment.setTexto(resultSet.getString("texto"));
				coment.setId(resultSet.getLong("id"));
				comentarios.add(coment);
			}
			resultSet.close();
		} catch (Exception e) {
			throw new RuntimeException(
					"Erro comentários indisponiveis no momento", e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}
		return comentarios;
	}

	@Override
	public Comentario salvarComentario(Comentario comentario, int idDoProduto) {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConection();
			String sql = "INSERT INTO comentarios (nome, texto, produto_id) VALUES (?, ?, ?)";

			statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, comentario.getNome());
			statement.setString(2, comentario.getTexto());
			statement.setLong(3, idDoProduto);
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			long key = keys.getLong(1);
			keys.close();
			comentario.setId(key);

		} catch (Exception e) {
			throw new RuntimeException("Erro ao adicionar comentario", e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}
		return comentario;
	}

	@Override
	public Produto salvaProdutoNoCarrinho(Long idDoProduto, Usuario usuario) {
		Produto produtos = new Produto();
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConection();
			String sql = "INSERT INTO carrinho (usuario_id, produto_id) VALUES(?, ?)";
			statement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, usuario.getId());
			statement.setLong(2, idDoProduto);
			statement.executeUpdate();

			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			long key = keys.getLong(1);
			keys.close();
			produtos.setId(key);

		} catch (Exception e) {
			throw new RuntimeException(
					"Erro ao adicionar produto no carrinho, tente novamente mais tarde",
					e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}
		return produtos;
	}

	@Override
	public List<Long> listaDeIdDosProdutosDoUsuario(Usuario usuario) {
		Connection connection = null;
		PreparedStatement statement = null;
		List<Long> listaDeIds = new ArrayList<>();
		try {
			connection = getConection();
			String sql = "SELECT * FROM carrinho where USUARIO_ID=?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, usuario.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				listaDeIds.add(resultSet.getLong("produto_id"));
			}
			resultSet.close();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar todos os produtos", e);
		} finally {
			releaseDatabaseResources(statement, connection);
		}
		return listaDeIds;
	}

	@Override
	public void removerProdutoDoCarrinhoDoUsuario(Usuario usuario) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConection();
			String sql = "DELETE FROM carrinho WHERE usuario_id=?";
			statement = connection.prepareStatement(sql);
			statement.setLong(1, usuario.getId());
			statement.executeUpdate();
	} catch (Exception e) {
		throw new RuntimeException("Erro ao deletar produto", e);
	} finally {
		releaseDatabaseResources(statement, connection);
	}
	}

}
