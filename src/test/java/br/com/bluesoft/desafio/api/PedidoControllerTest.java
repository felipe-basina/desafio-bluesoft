package br.com.bluesoft.desafio.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.bluesoft.desafio.DesafioApplication;
import br.com.bluesoft.desafio.data.PedidoRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = DesafioApplication.class)
@SpringBootTest
public class PedidoControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUpBeforeClass() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void testRecuperarPedidos() throws Exception {

		this.mockMvc.perform(get("/api/pedidos")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.pedidos", hasSize(0)))
				.andExpect(jsonPath("$.mensagem", isEmptyOrNullString()));

	}

	@Test
	public void testRealizarPedido() throws Exception {
		List<PedidoRequest> pedidoRequestLista = new ArrayList<>();
		
		PedidoRequest pedidoRequest01 = new PedidoRequest();
		pedidoRequest01.setGtin("7891000053508");
		pedidoRequest01.setQuantidade(5);
		
		PedidoRequest pedidoRequest02 = new PedidoRequest();
		pedidoRequest02.setGtin("7891000100103");
		pedidoRequest02.setQuantidade(3);
		
		PedidoRequest pedidoRequest03 = new PedidoRequest();
		pedidoRequest03.setGtin("7891910000197");
		pedidoRequest03.setQuantidade(2);
		
		pedidoRequestLista.add(pedidoRequest01);
		pedidoRequestLista.add(pedidoRequest02);
		pedidoRequestLista.add(pedidoRequest03);
		
		this.mockMvc
				.perform(post("/api/pedidos")
						.contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(pedidoRequestLista)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.pedidos", hasSize(2))) // Valor previamente identificado para o teste
				.andExpect(jsonPath("$.mensagem", isEmptyOrNullString()));
	}

	@Test
	public void testRealizarPedidoFornecedoresNaoIdentificados() throws Exception {
		List<PedidoRequest> pedidoRequestLista = new ArrayList<>();
		
		PedidoRequest pedidoRequest01 = new PedidoRequest();
		pedidoRequest01.setGtin("7891000053508");
		pedidoRequest01.setQuantidade(0);
		
		PedidoRequest pedidoRequest02 = new PedidoRequest();
		pedidoRequest02.setGtin("7891000100103");
		pedidoRequest02.setQuantidade(0);
		
		PedidoRequest pedidoRequest03 = new PedidoRequest();
		pedidoRequest03.setGtin("7891910000197");
		pedidoRequest03.setQuantidade(0);
		
		pedidoRequestLista.add(pedidoRequest01);
		pedidoRequestLista.add(pedidoRequest02);
		pedidoRequestLista.add(pedidoRequest03);
		
		this.mockMvc
				.perform(post("/api/pedidos")
						.contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(pedidoRequestLista)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.pedidos").isEmpty())
				.andExpect(jsonPath("$.mensagem")
						.value("Nenhum fornecedor encontrado para a quantidade solicitada do(s) produto(s)"));
	}

}
