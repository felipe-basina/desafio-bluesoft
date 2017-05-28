package br.com.bluesoft.desafio.model;

public class Item {

	private Produto produto;
	
	private int quantidade;

	private double preco;

	public Produto getProduto() {
		return produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public double getTotal() {
		return (this.preco * this.quantidade);
	}

	public Item(Produto produto, int quantidade, double preco) {
		this.produto = produto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [produto=");
		builder.append(produto);
		builder.append(", quantidade=");
		builder.append(quantidade);
		builder.append(", preco=");
		builder.append(preco);
		builder.append(", total=");
		builder.append(this.getTotal());
		builder.append("]");
		return builder.toString();
	}
	
}
