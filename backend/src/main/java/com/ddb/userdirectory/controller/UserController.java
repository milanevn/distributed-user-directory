package com.ddb.userdirectory.controller;

import com.ddb.userdirectory.dto.CreateUserRequest;
import com.ddb.userdirectory.dto.UserResponse;
import com.ddb.userdirectory.dto.UserSearchResponse;
import com.ddb.userdirectory.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{username}")
    public UserSearchResponse searchUser(
            @PathVariable String username) {
        return userService.searchUserByUsername(username);
    }
}
