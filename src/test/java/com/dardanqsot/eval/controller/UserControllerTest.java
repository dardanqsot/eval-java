package com.dardanqsot.eval.controller;

import com.dardanqsot.eval.dto.ResponseDto;
import com.dardanqsot.eval.dto.UserRequestDto;
import com.dardanqsot.eval.dto.UserSaveResponseDto;
import com.dardanqsot.eval.model.User;
import com.dardanqsot.eval.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserRequestDto dummyUserRequestDto() {
        return UserRequestDto.builder()
                .name("user")
                .email("aaaaaaa@dominio.cl")
                .password("Qwerty123")
                .phones(Collections.emptyList())
                .build();
    }
    public User dummyUser() {
        return User.builder()
                .name("user")
                .email("aaaaaaa@dominio.cl")
                .password("Qwerty123")
                .phones(Collections.emptyList())
                .build();
    }
    @Test
    @DisplayName("Save User 401")
    void when_call_save_user_then_return_401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
                        .content("{\n" +
                                "    \"data\": \"/v1/user\",\n" +
                                "    \"message\": \"Token no encontrado o incorrecto\"\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Save User 201")
    void when_call_save_user_then_return_201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user")
                        .content("{\n" +
                                "    \"name\": \"Juan Rodriguez\",\n" +
                                "    \"email\": \"juan@rodriguez4.cl\",\n" +
                                "    \"password\": \"Hhunter2\",\n" +
                                "    \"phones\": [\n" +
                                "        {\n" +
                                "            \"number\": \"1234567\",\n" +
                                "            \"cityCode\": \"1\",\n" +
                                "            \"countryCode\": \"57\"\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void saveProductController() {
        when(userService.save(dummyUser())).thenReturn(dummyUser());

        ResponseEntity<ResponseDto<UserSaveResponseDto>> response =
                userController.save(dummyUserRequestDto());
        assertNotNull(response);
    }
}
