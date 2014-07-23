package model;

import java.util.ArrayList;
import java.util.List;

import persistence.LojaBancoDeDadosDAO;
import persistence.LojaDAO;

public class Loja {

	LojaDAO banco = new LojaBancoDeDadosDAO();

	public List<Produto> listaDeProdutos() {
		return banco.buscaListaDeProdutos();
	}

	public void novoProduto(String nome, int valor, String descricao,
			String marca, Categoria categoria, Usuario usuario) {

		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setValor(valor);
		produto.setMarca(marca);
		produto.setCategoria(categoria);

		banco.adicionaProduto(produto, usuario);

	}

	public void adicionarComentario(String nome, String texto, int idDoProduto) {

		Comentario comentarios = new Comentario();
		comentarios.setNome(nome);
		comentarios.setTexto(texto);

		banco.salvarComentario(comentarios, idDoProduto);
	}

	public Produto buscaProdutoPorID(Long id) {
		return banco.buscaProdutoPorID(id);

	}

	public List<Comentario> buscaComentariosDoPostPorID(Long id) {
		return banco.listagemDeComentariosPorId(id);
	}

	public Produto adicionarProdutoAoCarrinho(Long id, Usuario usuario) {
		return banco.salvaProdutoNoCarrinho(id, usuario);

	}

	public List<Long> listaDeIdDosProdutosDoUsuario(Usuario usuario) {
		return banco.listaDeIdDosProdutosDoUsuario(usuario);
	}

	public List<Produto> produtosDoCarrinhoDoUsuario(Usuario usuario) {

		List<Long> listaDeIdDosProdutosDoUsuario = listaDeIdDosProdutosDoUsuario(usuario);

		List<Produto> listaDeProdutosDoUsuario = new ArrayList<Produto>();

		for (Long produtoId : listaDeIdDosProdutosDoUsuario) {
			listaDeProdutosDoUsuario.add(buscaProdutoPorID(produtoId));
		}

		int total = 0;
		for (Produto item : listaDeProdutosDoUsuario) {
			total = total + item.getValor();
		}
		return listaDeProdutosDoUsuario;
	}

	public int somaDosProdutosDoCarrinho(Usuario usuario) {
		List<Produto> listaDeProdutosDoUsuario = produtosDoCarrinhoDoUsuario(usuario);
		int total = 0;
		for (Produto item : listaDeProdutosDoUsuario) {
			total = total + item.getValor();
		}
		return total;
	}

	public void removerProdutoDoCarrinho(Usuario usuario) {
		banco.removerProdutoDoCarrinhoDoUsuario(usuario);
		
	}
}
