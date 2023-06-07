package ch.web.web_shop.service;

import ch.web.web_shop.model.User;
import ch.web.web_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("E-Mail already exists");
        }

        // Hier kannst du weitere Validierungen, Passwort-Hashing usw. hinzufügen

        userRepository.save(user);
        return ResponseEntity.ok("Registration successful");
    }

    public ResponseEntity<String> loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // ID des eingeloggten Benutzers abrufen und in einen Text umwandeln
        String userId = String.valueOf(existingUser.getId());

        // ID zusammen mit der Erfolgsmeldung zurückgeben
        return ResponseEntity.ok(userId);
    }
    public ResponseEntity<User> getUserDetails(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
