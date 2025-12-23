package com.dsec.collab.adaptor.http;

import com.dsec.collab.core.domain.User;
import com.dsec.collab.core.port.UserApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserApi userApi;

    public UserController(UserApi userApi) {
        this.userApi = userApi;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal Jwt jwt) {
        // function to get the current user (id from JWT token) details from the db
        try {

            // get the user's id from the JWT token
            UUID id = UUID.fromString(jwt.getClaimAsString("sub"));
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("name");

            System.out.println(jwt);

            System.out.println(id);
            System.out.println(email);
            System.out.println(name);

            // use the service to get the user from persistence
            User user = userApi.getOrCreateUser(id, email, name);
            System.out.println("hit /api/users/profile");
            // convert user to dto
            UserDTO userDTO = UserMapper.getUserDTO(user);

            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
