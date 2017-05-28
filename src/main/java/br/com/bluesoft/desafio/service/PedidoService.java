package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.bluesoft.desafio.form.ProdutoForm;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.PedidoRealizado;
import br.com.bluesoft.desafio.model.Preco;
import br.com.bluesoft.desafio.model.Produto;

@Service
public class PedidoService {

	private static final Logger LOGGER = Logger.getLogger(PedidoService.class);

	private ProdutoService produtoService;

	private FornecedorService fornecedorService;

	public static final List<Pedido> pedidos = new ArrayList<>();

	private static int pedidoIndice = 0;

	@Autowired
	public PedidoService(ProdutoService produtoService, FornecedorService fornecedorService) {
		this.produtoService = produtoService;
		this.fornecedorService = fornecedorService;
	}

	public List<Pedido> recuperarPedidosRealizados() {
		// Retorna a lista de pedidos ordenada decrescente por 'id'
		return pedidos.stream().sorted((pedido01, pedido02) -> Integer.compare(pedido02.getId(), pedido01.getId()))
				.collect(Collectors.toList());
	}

	public List<Pedido> agruparFornecedoresPorPedido(List<PedidoRealizado> pedidoRealizadoLista) {
		LOGGER.info("\n\tPreparando para confirmar pedidos e agrupar fornecedores elegiveis");

		List<Pedido> pedidos = new ArrayList<>();

		try {

			Map<Fornecedor, Pedido> fornecedorMapa = new HashMap<>();

			/*
			 * Para cada produto da lista, invoca a API externa e identifica a
			 * melhor opção para compra de acordo com o valor e a quantidade
			 * miníma
			 */
			for (PedidoRealizado pedidoRealizado : pedidoRealizadoLista) {

				// Lista de fornecedores elegíveis
				List<Fornecedor> fornecedorLista = null;

				try {

					fornecedorLista = this.fornecedorService
							.listarFornecedoresPorProdutoId(pedidoRealizado.getProduto().getGtin());

				} catch (Exception ex) {
					LOGGER.error("ERRO: " + ex.getMessage(), ex);
					continue; // Segue para o próximo produto
				}

				int quantidadeSolicitada = pedidoRealizado.getQuantidade();

				Fornecedor fornecedorIdeal = null;

				double precoIdeal = 0.0;

				for (Fornecedor fornecedor : fornecedorLista) {

					for (Preco preco : fornecedor.getPrecos()) {

						if (quantidadeSolicitada >= preco.getQuantidadeMinima()) {

							if (precoIdeal == 0.0 || precoIdeal > preco.getPreco()) {
								// Define o preço
								precoIdeal = preco.getPreco();

								// Define o fornecedor
								// Tudo bem se tiver que redefinir o valor pelo
								// mesmo fornecedor ;)
								fornecedorIdeal = fornecedor;
							}

						}

					} // fim for 'Preco'

				} // fim for 'Fornecedor'

				// Adiciona no mapa o registro referente ao fornecedor ideal
				if (precoIdeal > 0.0) {

					Pedido pedidoFuturo = fornecedorMapa.get(fornecedorIdeal);

					if (ObjectUtils.isEmpty(pedidoFuturo)) {
						fornecedorMapa.put(fornecedorIdeal, new Pedido(fornecedorIdeal));
						pedidoFuturo = fornecedorMapa.get(fornecedorIdeal);
						pedidoFuturo.setId(++PedidoService.pedidoIndice);
					}

					// Define o item
					pedidoFuturo.getItens()
							.add(new Item(pedidoRealizado.getProduto(), quantidadeSolicitada, precoIdeal));

				}

			} // fim for 'PedidoRealizado'

			// Para cada fornecedor identificado, preenche a lista de pedidos
			pedidos = fornecedorMapa.values().stream().collect(Collectors.toList());

			// Adiciona na lista em memória
			PedidoService.pedidos.addAll(pedidos);

		} catch (Exception ex) {
			LOGGER.error("ERRO ao realizar agrupamento de fornecedores elegíveis: " + ex.getMessage(), ex);
		}

		return pedidos;
	}

	public List<PedidoRealizado> definirPedidoRealizado(List<ProdutoForm> pedidoFormLista) {
		LOGGER.info("\n\tPreparando para definir lista com pedidos realizados");

		List<PedidoRealizado> pedidoRealizado = new ArrayList<>();

		try {

			for (ProdutoForm pedidoForm : pedidoFormLista) {

				Produto produto = null;

				try {

					produto = this.produtoService.recuperarProdutoPorId(pedidoForm.getProduto());
					LOGGER.info("\t Identificado " + produto);

				} catch (Exception ex) {
					LOGGER.error(
							"ERRO - Produto [ " + pedidoForm.getProduto() + " ] nao encontrado: " + ex.getMessage(),
							ex);
					continue; // Segue para o próximo registro
				}

				pedidoRealizado.add(new PedidoRealizado(produto, pedidoForm.getQuantidade()));

			} // fim for 'PedidoForm'

		} catch (Exception ex) {
			LOGGER.error("ERRO ao definir pedido realizado a partir do formulario: " + ex.getMessage(), ex);
		}

		LOGGER.info("Total de produtos: " + pedidoRealizado.size());

		return pedidoRealizado;
	}

}
