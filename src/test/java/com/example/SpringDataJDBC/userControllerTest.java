package com.example.SpringDataJDBC;


import com.example.SpringDataJDBC.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class userControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void a_get_all_user() throws Exception {
    ResultActions result = mockMvc.perform(get("/users"));

    result.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.length()").value(2))
        .andReturn().getResponse().getContentAsString();
  }

  @Test
  public void b_get_user_by_username() throws Exception {
    ResultActions result = mockMvc.perform(
        get("/findUsers")
            .param("username", "@xiaoming")
    );

    result.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.id").value(0))
        .andExpect(jsonPath("$.name").value("小明"))
        .andReturn().getResponse().getContentAsString();
  }

  @Test
  public void c_delete_user() throws Exception {
    ResultActions result = mockMvc.perform(
        delete("/users/{id}", "1")
    );

    result.andExpect(status().isNoContent());
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
  public void e_update_user() throws Exception {
    User user1 = new User((long) 0, "小刚", "@xiaogang");

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
