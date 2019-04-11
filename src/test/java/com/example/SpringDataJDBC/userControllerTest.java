package com.example.SpringDataJDBC;


import com.example.SpringDataJDBC.controller.UserController;
import com.example.SpringDataJDBC.entity.User;
import com.example.SpringDataJDBC.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class userControllerTest {

  @Autowired
  private UserController userController;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  public void getAllUser() throws Exception {
    List<User> userList = new ArrayList<>();
    User user1 = new User((long) 0, "aaa", "@aaa");
    User user2 = new User((long) 1, "bbb", "@bbb");
    userList.add(user1);
    userList.add(user2);

    when(userService.findAll()).thenReturn(userList);

    ResultActions result = mockMvc.perform(get("/users"));

    result.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.length()").value(2))
        .andReturn().getResponse().getContentAsString();
  }

  @Test
  public void getUserByUsername() throws Exception {

    User user1 = new User((long) 0, "aaa", "@aaa");
    when(userService.getUserByUsername("@aaa")).thenReturn(user1);

    ResultActions result = mockMvc.perform(
        get("/findUsers")
            .param("username", "@aaa")
    );

    result.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.id").value(0))
        .andExpect(jsonPath("$.name").value("aaa"))
        .andReturn().getResponse().getContentAsString();
  }

  @Test
  public void deleteUser() throws Exception {

    User user = new User((long) 1, "bbb", "@bbb");
    when(userService.deleteUser(1)).thenReturn(user);

    ResultActions result = mockMvc.perform(
        delete("/users/{id}", "1")
    );

    result.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("bbb"))
        .andReturn().getResponse().getContentAsString();
  }

  @Test
  public void saveUser() throws Exception {

    User user = new User((long) 2, "ccc", "@ccc");
    when(userService.saveUser(user)).thenReturn(user);

    ResultActions result = mockMvc.perform(
        post("/users")
            .content(asJsonString(new User((long) 2, "ccc", "@ccc")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)

    );

    result.andExpect(status().isOk())
        .andDo(print())
        .andReturn().getResponse().getContentAsString();
  }


  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      final String jsonContent = mapper.writeValueAsString(obj);
      return jsonContent;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void updateUser() throws Exception {
    User user = new User((long) 0, "aaa", "@aaa");
    User user1 = new User((long) 0, "a", "@aaa");
    when(userService.updateUser(user, 0)).thenReturn(user1);

    ResultActions result = mockMvc.perform(
        put("/users/{id}", 0)
            .content(asJsonString(user1))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
    );

    result.andExpect(status().isOk())
        .andDo(print())
        .andReturn().getResponse().getContentAsString();
  }

}
