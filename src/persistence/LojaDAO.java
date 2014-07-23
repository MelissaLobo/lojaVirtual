package persistence;

import java.util.List;

import model.Comentario;
import model.Produto;
import model.Usuario;

public interface LojaDAO {

	List<Produto> buscaListaDeProdutos();

	Produto adicionaProduto(Produto produto, Usuario usuario);

	Produto buscaProdutoPorID(Long id);

	List<Comentario> listagemDeComentariosPorId(Long id);

	Comentario salvarComentario(Comentario comentario, int idDoProduto);

	List<Long> listaDeIdDosProdutosDoUsuario(Usuario usuario);

	Produto salvaProdutoNoCarrinho(Long id, Usuario usuario);

	void removerProdutoDoCarrinhoDoUsuario(Usuario usuario);

}
