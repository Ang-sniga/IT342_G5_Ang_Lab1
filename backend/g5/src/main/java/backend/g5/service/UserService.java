package backend.g5.service;

import backend.g5.entity.User;
import backend.g5.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public User registerUser(String email, String password, String name) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email already registered");
        }
        
        User user = new User(email, passwordEncoder.encode(password), name);
        return userRepository.save(user);
    }
    
    public User loginUser(String email, String password) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        
        if (user.isEmpty()) {
            throw new Exception("User not found");
        }
        
        User foundUser = user.get();
        if (!passwordEncoder.matches(password, foundUser.getPassword())) {
            throw new Exception("Invalid password");
        }
        
        return foundUser;
    }
    
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
