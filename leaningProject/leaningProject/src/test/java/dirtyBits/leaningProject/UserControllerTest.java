package dirtyBits.leaningProject;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dirtyBits.learningProject.config.Config;
import dirtyBits.learningProject.service.UserService;
import dirtyBits.learningProject.view.UserRegisterView;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Config.class})
@EnableAutoConfiguration
@WebAppConfiguration
public class UserControllerTest {
	
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testGetDummy() throws Exception {
		mockMvc.perform(get("/getDummy")).andDo(print()).andExpect(content().string(containsString("dummy")));
	}
	
	@Test
	public void testRegisterPimp() throws Exception {
		doNothing().when(userService).register(new UserRegisterView("userTest", "password"));
		mockMvc.perform(post("/register/user").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\"username\":\"userTest\",\"password\": \"password\"}").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isCreated()).andDo(print())
				.andExpect(content().string("{\"message\":\"User userTest created!\"}"));
	}
}
