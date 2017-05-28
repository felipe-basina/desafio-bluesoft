package br.com.bluesoft.desafio.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.bluesoft.desafio.model.Fornecedor;

@Service
public class FornecedorWSAPI {

	private static final Logger LOGGER = Logger.getLogger(FornecedorWSAPI.class);

	@Value(value = "${endpoint.externo}")
	private String endpoint;

	public List<Fornecedor> listarFornecedoresPorProdutoId(String gtin) {
		LOGGER.info("\n\tPreparando para recuperar fornecedores para produto [ " + gtin + " ]");

		List<Fornecedor> fornecedorLista = new ArrayList<>();

		try {

			final RestTemplate restTemplate = new RestTemplate();

			final String uri = this.endpoint.concat(gtin);

			final Fornecedor[] fornecedoresArray = restTemplate.getForObject(uri, Fornecedor[].class);

			fornecedorLista = Arrays.asList(fornecedoresArray);

		} catch (Exception ex) {
			LOGGER.error("ERRO ao realizar comunicacao com API externa para recuperar fornecedores: " + ex.getMessage(),
					ex);
		}

		return fornecedorLista;
	}

}
