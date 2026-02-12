package backend.g5.controller;

import backend.g5.dto.RegisterRequest;
import backend.g5.dto.LoginRequest;
import backend.g5.dto.UserResponse;
import backend.g5.entity.User;
import backend.g5.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
    
    private final UserService userService;
    
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.registerUser(request.getEmail(), request.getPassword(), request.getName());
            UserResponse response = new UserResponse(user.getUserId(), user.getEmail(), user.getName(), user.getCreatedAt());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        try {
            User user = userService.loginUser(request.getEmail(), request.getPassword());
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userEmail", user.getEmail());
            
            UserResponse response = new UserResponse(user.getUserId(), user.getEmail(), user.getName(), user.getCreatedAt());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
}
