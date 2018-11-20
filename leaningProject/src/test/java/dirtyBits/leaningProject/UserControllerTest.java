package dirtyBits.leaningProject;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dirtyBits.learningProject.config.Config;
import dirtyBits.learningProject.service.UserService;
import dirtyBits.learningProject.view.ErrorView;
import dirtyBits.learningProject.view.UserRegisterView;
import dirtyBits.learningProject.view.UserView;
import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Config.class })
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
				.content("{\"username\":\"userTest\",\"password\": \"password\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isCreated()).andDo(print())
				.andExpect(content().string("{\"message\":\"User userTest created!\"}"));
	}

	@Test
	public void testGetUsers() throws JsonProcessingException, Exception {
		Mockito.when(userService.getAll()).thenReturn(UtilForTests.getUserViewList());
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(new ObjectMapper().writeValueAsString(UtilForTests.getUserViewList())));
	}

	@Test
	public void testGetUserById() throws Exception {
		Mockito.when(userService.getUserById(2l)).thenReturn(new UserView(2l, "userTest"));
		mockMvc.perform(get("/user/2").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(new ObjectMapper().writeValueAsString(new UserView(2l, "userTest"))));
	}

	@Test
	public void testGetUserByIdWhenThrowsException() throws Exception {
		Mockito.when(userService.getUserById(2l)).thenThrow(new NotFoundException("Not found exception for test!"));
		mockMvc.perform(get("/user/2").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print())
				.andExpect(status().isNotFound()).andExpect(content().string(
						new ObjectMapper().writeValueAsString(new ErrorView("Not found exception for test!", ""))));
	}

	@Test
	public void testUpdateUser() throws Exception {
		doNothing().when(userService).updateUser(2l, new UserRegisterView("userTest", "password"));
		mockMvc.perform(put("/user/2").content("{\"username\":\"userTest\",\"password\": \"password\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("{\"message\":\"User userTest was updated!\"}"));
	}

	@Test
	public void testUpdateUserWhenThrowsException() throws JsonProcessingException, Exception {
		Mockito.doThrow(new NotFoundException("Not found exception for test!")).when(userService).updateUser(2l,
				new UserRegisterView("userTest", "password"));
		mockMvc.perform(put("/user/2").content("{\"username\":\"userTest\",\"password\": \"password\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().string(
						new ObjectMapper().writeValueAsString(new ErrorView("Not found exception for test!", ""))));
	}
	
	@Test
	public void testDeleteUserById() throws Exception {
		Mockito.doNothing().when(userService).deleteUserById(2l);
		mockMvc.perform(delete("/user/2").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print()).andExpect(status().isGone())
		.andExpect(content().string("{\"message\":\"User with id 2 was deleted!\"}"));
	}
}
