package org.example.edu.service;

import org.example.edu.model.User;
import org.example.edu.model.dto.UserDto;
import org.example.edu.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    UserDto user = UserDto.builder().firstName("Test_firstName").lastName("Test_lastName").password("123").build();

    @Test
    @Order(1)
    public void saveNewUser_OK() {
        when(userRepository.save(any(User.class))).thenReturn(User.builder().build());

        User mergerUser = userService.createUser(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        Assertions.assertNotNull(mergerUser);
    }

    @Test
    @Order(2)
    public void findUserById_OK() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        UserDto foundUser = userService.findUserById(1L);
        Assertions.assertNotNull(foundUser);
    }


    @Test
    @Order(3)
    public void findUserById_ThrowException() {
        Assertions.assertThrows(RuntimeException.class, () -> userService.findUserById(2L));
    }
}
