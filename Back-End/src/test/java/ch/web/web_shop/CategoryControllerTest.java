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

import ch.web.web_shop.controller.CategoryController;
import ch.web.web_shop.repository.CategoryRepository;
import ch.web.web_shop.repository.NewslatterRepository;
import ch.web.web_shop.repository.ProductRepository;



@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc

public class CategoryControllerTest {
    

	@MockBean private NewslatterRepository newslatterRepository; 
	@MockBean private ProductRepository prpductRepository; 
	@MockBean private CategoryRepository categoryRepository;
	


	@Autowired CategoryController categoryController; 


	@Autowired private MockMvc mockMvc;

	
	@Test
	public void whenCategoryControllerInjected_thenNotNull() throws Exception {
		assertThat(categoryController).isNotNull(); 
	}

	@Test
	public void whenGetAllCategories_getValidCategories() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.get("/api/category"))
		.andDo(res -> System.out.println(res.getResponse().getContentAsString())) 
		.andExpect(MockMvcResultMatchers.status().isOk())		
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
}
