package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private static final Logger LOGGER = Logger.getLogger(ProdutoService.class);
	
	private ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	public List<Produto> recuperarProdutos() {
		LOGGER.info("\n\tPreparando para recuperar produtos");
		return (ArrayList<Produto>) this.produtoRepository.findAll();
	}
	
	public Produto recuperarProdutoPorId(String id) {
		LOGGER.info("\n\tPreparando para recuperar produto [ " + id + " ]");
		return this.produtoRepository.findOne(id);
	}

}
