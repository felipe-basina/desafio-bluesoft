package br.com.bluesoft.desafio.service;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bluesoft.desafio.model.Produto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProdutoServiceTest {

	@Autowired
	private ProdutoService produtoService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testRecuperarProdutos() {
		List<Produto> produtoLista = this.produtoService.recuperarProdutos();
		
		Assert.assertNotNull(produtoLista);
		Assert.assertTrue(!produtoLista.isEmpty());
		Assert.assertTrue(produtoLista.size() > 0);
		Assert.assertTrue(produtoLista.size() == 7);
	}

	@Test
	public void testRecuperarProdutoPorId() {
		Produto produto = this.produtoService.recuperarProdutoPorId("7891000053508");
		
		Assert.assertNotNull(produto);
	}

}
