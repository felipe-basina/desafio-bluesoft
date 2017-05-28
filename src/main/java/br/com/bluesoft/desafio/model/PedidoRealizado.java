package br.com.bluesoft.desafio.model;

public class PedidoRealizado {

	private Produto produto;

	private int quantidade;

	public Produto getProduto() {
		return produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public PedidoRealizado(Produto produto, int quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

}
