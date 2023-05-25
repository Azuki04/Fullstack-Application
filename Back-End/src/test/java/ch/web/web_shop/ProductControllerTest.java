
package ch.web.web_shop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ch.web.web_shop.controller.ProductController;
import ch.web.web_shop.repository.CategoryRepository;
import ch.web.web_shop.repository.NewsletterRepository;
import ch.web.web_shop.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc

public class ProductControllerTest {

	@MockBean
	private NewsletterRepository newsletterRepository;
	@MockBean
	private ProductRepository prpductRepository;
	@MockBean
	private CategoryRepository categoryRepository;

	@Autowired
	ProductController productController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void whenProductControllerInjected_thenNotNull() throws Exception {
		assertThat(productController).isNotNull();
	}

	@Test
	public void whenGetRequestToGetAllProduicts_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/products"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void whenPostRequestToProductAndInValidProduct_thenCorrectResponse() throws Exception {
		String product = "{\"products\": null }";
		mockMvc.perform(MockMvcRequestBuilders.post("/products/1")
				.content(product)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.product").value("Product is mandatory"))
				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON));
	}

}
