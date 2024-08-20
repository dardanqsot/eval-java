package com.dardanqsot.eval.service;

import com.dardanqsot.eval.model.User;
import com.dardanqsot.eval.repository.UserRepository;
import com.dardanqsot.eval.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    public User dummyUser() {
        return User.builder()
                .name("user")
                .email("aaaaaaa@dominio.cl")
                .password("Qwerty123")
                .phones(Collections.emptyList())
                .build();
    }
    @Test
    public void saveProductService() {
        when(userRepository.save(dummyUser())).thenReturn(dummyUser());
        User response = userService.save(dummyUser());
        assertNotNull(response);
    }
}
