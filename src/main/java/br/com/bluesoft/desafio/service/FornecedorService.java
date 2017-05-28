package br.com.bluesoft.desafio.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.integration.FornecedorWSAPI;
import br.com.bluesoft.desafio.model.Fornecedor;

@Service
public class FornecedorService {

	private static final Logger LOGGER = Logger.getLogger(FornecedorService.class);

	private FornecedorWSAPI fornecedorWSAPI;

	@Autowired
	public FornecedorService(FornecedorWSAPI fornecedorWSAPI) {
		this.fornecedorWSAPI = fornecedorWSAPI;
	}

	public List<Fornecedor> listarFornecedoresPorProdutoId(String gtin) {
		LOGGER.info("\n\tListar fornecedores por produto id");
		return this.fornecedorWSAPI.listarFornecedoresPorProdutoId(gtin);
	}
	
}
