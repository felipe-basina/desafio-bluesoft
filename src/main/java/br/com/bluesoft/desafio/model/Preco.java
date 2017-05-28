package br.com.bluesoft.desafio.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Preco {

	private double preco;

	@JsonProperty(value = "quantidade_minima")
	private int quantidadeMinima;

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(int quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}

	public Preco() {
		super();
	}

	public Preco(double preco, int quantidadeMinima) {
		this.preco = preco;
		this.quantidadeMinima = quantidadeMinima;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(preco);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantidadeMinima;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Preco other = (Preco) obj;
		if (Double.doubleToLongBits(preco) != Double.doubleToLongBits(other.preco))
			return false;
		if (quantidadeMinima != other.quantidadeMinima)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Preco [preco=");
		builder.append(preco);
		builder.append(", quantidadeMinima=");
		builder.append(quantidadeMinima);
		builder.append("]");
		return builder.toString();
	}

}
