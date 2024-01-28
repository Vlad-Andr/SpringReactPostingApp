package org.example.edu.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.example.edu.model.User;
import org.example.edu.model.dto.UserDto;
import org.example.edu.repository.UserRepository;
import org.example.edu.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        User existedUser = user.orElseThrow(() -> new RuntimeException("User was not found by id " + id));
        return UserDto.builder().firstName(existedUser.getFirstName())
                .lastName(existedUser.getLastName())
                .build();
    }

    @Override
    public User createUser(UserDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .build();
        return userRepository.save(user);
    }

    @Override
    public UserDto findUserByUsernameAndPassword(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            User existedUser = user.get();
            log.info("User logged by username: " + username);
            return UserDto.builder()
                    .username(existedUser.getUsername())
                    .firstName(existedUser.getFirstName())
                    .lastName(existedUser.getLastName())
                    .build();
        }
        throw new RuntimeException("User was not found by username " + username + " and password " + password);
    }
}
