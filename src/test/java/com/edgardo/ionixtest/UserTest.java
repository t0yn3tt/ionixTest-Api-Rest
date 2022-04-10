package com.edgardo.ionixtest;

import com.edgardo.ionixtest.controllers.UserController;

import com.edgardo.ionixtest.dto.UserDTO;
import com.edgardo.ionixtest.services.UserService;
import com.edgardo.ionixtest.utils.DummyData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@WebMvcTest(UserController.class)
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(DummyData.user1());
        mockMvc.perform(get("/api/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstname").value("Juan"))
                .andExpect(jsonPath("$.lastname").value("Perez"))
                .andExpect(jsonPath("$.email").value("juan@gmail.com"))
                .andExpect(jsonPath("$.username").value("juanjo"));
        verify(userService).getUserById(1L);
    }

    @Test
    public void createUser() throws JsonProcessingException, Exception {
        UserDTO newUser = new UserDTO(null, "Edgardo", "Martinez", "edgardo@gmail.com","edgardo");

        when(userService.createUser(any())).then(invocation -> {
            UserDTO tmpUser = invocation.getArgument(0);
            tmpUser.setId(3L);
            return tmpUser;
        });

        mockMvc.perform(post("/api/users/")
                        .with(user("edgardo").password("password").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(newUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstname").value("Edgardo"))
                .andExpect(jsonPath("$.lastname").value("Martinez"))
                .andExpect(jsonPath("$.email").value("edgardo@gmail.com"))
                .andExpect(jsonPath("$.username").value("edgardo"))
                .andExpect(status().isCreated());

        verify(userService).createUser(any());

    }

    @Test
    public void updateUser() throws JsonProcessingException, Exception {
        UserDTO newUser = new UserDTO(null, "Edgardo", "Martinez", "edgardo@gmail.com","edgardo");

        when(userService.updateUser(any(),eq(1L))).then(invocation -> {
            UserDTO tmpUser = invocation.getArgument(0);
            tmpUser.setId(1L);
            return tmpUser;
        });

        mockMvc.perform(put("/api/users/1")
                        .with(user("edgardo").password("password").roles("ADMIN"))
                        .content(objectMapper.writeValueAsString(newUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Edgardo"))
                .andExpect(jsonPath("$.lastname").value("Martinez"))
                .andExpect(jsonPath("$.email").value("edgardo@gmail.com"))
                .andExpect(jsonPath("$.username").value("edgardo"))
                .andExpect(status().isOk());

        verify(userService).updateUser(any(), eq(1L));

    }
    @Test
    public void getAllUsers() throws Exception{
        //System.out.println(objectMapper.writeValueAsString(DummyData.userResponse()));
        when(userService.getUsers(0,10, "id", "asc")).thenReturn(DummyData.userResponse());

        mockMvc.perform(get("/api/users/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.itemsPerPages").value(10))
                .andExpect(jsonPath("$.countElements").value(1))
                .andExpect(jsonPath("$.countPages").value(1))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(content().json(objectMapper.writeValueAsString(DummyData.userResponse())));

        verify(userService).getUsers(0,10, "id", "asc");
    }

    @Test
    public void deleteUser() throws Exception {

        when(userService.getUserById(1L)).thenReturn(DummyData.user1());

        mockMvc.perform(delete("/api/users/1").with(user("edgardo").password("password").roles("ADMIN")))
                .andExpect(status().isOk());

        verify(userService).deleteUser(1L);
    }
}
