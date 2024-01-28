package org.example.edu.web;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.edu.model.User;
import org.example.edu.model.dto.UserDto;
import org.example.edu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@Log4j2
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto dto) {
        User user = userService.createUser(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/login")
    public UserDto loginUser(@RequestBody UserDto dto) {
        return userService.findUserByUsernameAndPassword(dto.getUsername(), dto.getPassword());
    }
}
