package br.com.bluesoft.desafio.data;

import java.util.List;

import br.com.bluesoft.desafio.model.Pedido;

public class PedidoResponse {

	private List<Pedido> pedidos;
	
	private String mensagem;

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
