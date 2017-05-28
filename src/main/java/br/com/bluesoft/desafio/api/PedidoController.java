package br.com.bluesoft.desafio.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.data.PedidoRequest;
import br.com.bluesoft.desafio.data.PedidoResponse;
import br.com.bluesoft.desafio.form.ProdutoForm;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.PedidoRealizado;
import br.com.bluesoft.desafio.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private static final Logger LOGGER = Logger.getLogger(PedidoController.class);

	private PedidoService pedidoService;

	@Value(value = "${mensagem.erro.fornecedor.naoencontrado}")
	private String mensagem;
	
	@Autowired
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public PedidoResponse recuperarPedidos() {
		LOGGER.info("\n\tPreparando para recuperar pedidos realizados");
		PedidoResponse pedidoResponse = new PedidoResponse();
		pedidoResponse.setPedidos(this.pedidoService.recuperarPedidosRealizados());
		return pedidoResponse;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<PedidoResponse> realizarPedido(@RequestBody List<PedidoRequest> pedidoRequestLista) {
		LOGGER.info("\n\tPreparando para realizar pedido");

		PedidoResponse pedidoResponse = new PedidoResponse();

		HttpStatus httpStatus = HttpStatus.OK;
		
		try {

			// Converte a lista de parâmetros recebidos do formulário
			List<PedidoRealizado> pedidoRealizadoLista = this.pedidoService
					.definirPedidoRealizado(this.definirProdutoForm(pedidoRequestLista));

			// Realiza o novo pedido
			List<Pedido> pedidoLista = this.pedidoService.agruparFornecedoresPorPedido(pedidoRealizadoLista);
			LOGGER.info("\n\tTotal de pedidos realizados = " + pedidoLista.size());
			
			if (pedidoLista.size() > 0) {
				
				pedidoResponse.setPedidos(pedidoLista);
				
			} else { // Nenhum fornecedor encontrado
				throw new Exception(this.mensagem);
			}

		} catch (Exception ex) {
			LOGGER.error("ERRO ao realizar pedido: " + ex.getMessage(), ex);
			
			httpStatus = HttpStatus.BAD_REQUEST;
			
			pedidoResponse.setMensagem(ex.getMessage());
		}

		return new ResponseEntity<PedidoResponse>(pedidoResponse, httpStatus);
	}
	
	private List<ProdutoForm> definirProdutoForm(List<PedidoRequest> pedidoRequestLista) {
		List<ProdutoForm> produtoFormLista = new ArrayList<>();
		
		for (PedidoRequest pedidoRequest : pedidoRequestLista) {
			produtoFormLista.add(new ProdutoForm(pedidoRequest.getGtin(), pedidoRequest.getQuantidade()));
		}
		
		return produtoFormLista;
	}
}
