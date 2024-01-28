package org.example.edu.service;

import org.example.edu.model.User;
import org.example.edu.model.dto.UserDto;

public interface UserService {
    UserDto findUserById(Long id);
    User createUser(UserDto dto);

    UserDto findUserByUsernameAndPassword(String username, String password);
}
