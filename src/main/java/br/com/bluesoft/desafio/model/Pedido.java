package br.com.bluesoft.desafio.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private int id;
	
	private Fornecedor fornecedor;
	
	private List<Item> itens = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public List<Item> getItens() {
		return itens;
	}

	public Pedido(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
}
