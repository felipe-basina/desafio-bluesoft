package br.com.bluesoft.desafio.integration;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bluesoft.desafio.model.Fornecedor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FornecedorWSAPITest {

	@Autowired
	private FornecedorWSAPI fornecedorWSAPI;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testListarFornecedoresPorProdutoId() {
		final String gtin = "7894900011517";

		List<Fornecedor> fornecedorLista = this.fornecedorWSAPI.listarFornecedoresPorProdutoId(gtin);

		Assert.assertTrue(!fornecedorLista.isEmpty());
		Assert.assertTrue(fornecedorLista.size() == 2);
		Assert.assertTrue(!fornecedorLista.get(0).getPrecos().isEmpty());
	}

}
