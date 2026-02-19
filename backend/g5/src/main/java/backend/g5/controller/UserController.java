package backend.g5.controller;

import backend.g5.dto.UserResponse;
import backend.g5.entity.User;
import backend.g5.service.UserService;
import backend.g5.config.JwtUtil;
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
    private final JwtUtil jwtUtil;
    
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Authorization header missing or invalid");
                return ResponseEntity.status(401).body(error);
            }
            
            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Invalid token");
                return ResponseEntity.status(401).body(error);
            }
            
            Long userId = jwtUtil.getUserIdFromToken(token);
            Optional<User> user = userService.getUserById(userId);
            
            if (user.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "User not found");
                return ResponseEntity.status(404).body(error);
            }
            
            User foundUser = user.get();
            UserResponse response = new UserResponse(foundUser.getUserId(), foundUser.getEmail(), foundUser.getName(), foundUser.getCreatedAt());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
