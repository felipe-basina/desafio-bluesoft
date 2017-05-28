package br.com.bluesoft.desafio.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bluesoft.desafio.model.Fornecedor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FornecedorServiceTest {

	@Autowired
	private FornecedorService fornecedorService;

	@Test
	public void testAgruparFornecedoresPorPedido() {
		List<Fornecedor> fornecedorLista = fornecedorService.listarFornecedoresPorProdutoId("7891910000197");
				
		Assert.assertNotNull(fornecedorLista);
		Assert.assertTrue(!fornecedorLista.isEmpty());
		Assert.assertTrue(fornecedorLista.size() > 0);
	}

}
