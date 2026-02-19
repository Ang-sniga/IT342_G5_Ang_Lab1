package backend.g5.controller;

import backend.g5.dto.UserResponse;
import backend.g5.entity.User;
import backend.g5.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Long)) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "User not authenticated. Please login first.");
            return ResponseEntity.status(401).body(error);
        }
        
        Long userId = (Long) authentication.getPrincipal();
        Optional<User> user = userService.getUserById(userId);
        
        if (user.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "User not found");
            return ResponseEntity.status(404).body(error);
        }
        
        User foundUser = user.get();
        UserResponse response = new UserResponse(foundUser.getUserId(), foundUser.getEmail(), foundUser.getName(), foundUser.getCreatedAt());
        return ResponseEntity.ok(response);
    }
}
