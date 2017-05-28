package br.com.bluesoft.desafio.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.bluesoft.desafio.form.ProdutoForm;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.PedidoRealizado;
import br.com.bluesoft.desafio.model.Produto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PedidoServiceTest {

	@Autowired
	private PedidoService pedidoService;

	private static List<ProdutoForm> pedidoFormLista = new ArrayList<>();

	private static List<PedidoRealizado> pedidoRealizadoLista = new ArrayList<>();
	
	private static List<Produto> produtoLista = new ArrayList<>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ProdutoForm pedidoForm01 = new ProdutoForm("7894900011517", 2);
		ProdutoForm pedidoForm02 = new ProdutoForm("7892840222949", 5);
		ProdutoForm pedidoForm03 = new ProdutoForm("7891991010856", 7);

		pedidoFormLista.add(pedidoForm01);
		pedidoFormLista.add(pedidoForm02);
		pedidoFormLista.add(pedidoForm03);
		
		Produto produto01 = new Produto();
		produto01.setGtin("7891910000197");
		produto01.setNome("AÇÚCAR REFINADO UNIÃO 1KG");
		
		produtoLista.add(produto01);
		
		Produto produto02 = new Produto();
		produto02.setGtin("7891000100103");
		produto02.setNome("LEITE CONDENSADO MOÇA");
		
		produtoLista.add(produto02);
	}

	@Test
	public void testDefinirPedidoRealizado() {
		List<PedidoRealizado> pedidoRealizadoLista = this.pedidoService.definirPedidoRealizado(pedidoFormLista);

		Assert.assertNotNull(pedidoRealizadoLista);
		Assert.assertTrue(!pedidoFormLista.isEmpty());
		Assert.assertTrue(pedidoFormLista.size() > 0);
	}

	@Test
	public void testAgruparFornecedoresPorPedido() {
		PedidoRealizado pedidoRealizado01 = new PedidoRealizado(produtoLista.get(0), 4);
		PedidoRealizado pedidoRealizado02 = new PedidoRealizado(produtoLista.get(1), 7);		
		
		pedidoRealizadoLista.add(pedidoRealizado01);
		pedidoRealizadoLista.add(pedidoRealizado02);
		
		List<Pedido> pedidoLista = pedidoService.agruparFornecedoresPorPedido(pedidoRealizadoLista);
				
		Assert.assertNotNull(pedidoLista);
		Assert.assertTrue(!pedidoLista.isEmpty());
		Assert.assertTrue(pedidoLista.size() > 0);
	}

	@Test
	public void testAgruparFornecedoresPorPedidoNenhumFornecedorEncontrado() {
		PedidoRealizado pedidoRealizado01 = new PedidoRealizado(produtoLista.get(0), 0);
		PedidoRealizado pedidoRealizado02 = new PedidoRealizado(produtoLista.get(1), 0);
		
		pedidoRealizadoLista.add(pedidoRealizado01);
		pedidoRealizadoLista.add(pedidoRealizado02);
		
		List<Pedido> pedidoLista = pedidoService.agruparFornecedoresPorPedido(pedidoRealizadoLista);
		
		Assert.assertNotNull(pedidoLista);
		Assert.assertTrue(pedidoLista.isEmpty());
	}

}
