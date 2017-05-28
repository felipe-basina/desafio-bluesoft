package br.com.bluesoft.desafio.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	private static final Logger LOGGER = Logger.getLogger(ProdutoController.class);

	private ProdutoService produtoService;

	@Autowired
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Iterable<Produto> findAll() {
		LOGGER.info("\n\tPreparando para listar produtos");
		return this.produtoService.recuperarProdutos();
	}

}
