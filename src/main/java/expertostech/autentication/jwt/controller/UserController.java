package expertostech.autentication.jwt.controller;

import expertostech.autentication.jwt.model.UserModel;
import expertostech.autentication.jwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UserModel>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity<UserModel> salvar(@RequestBody UserModel usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(repository.save(usuario));
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login,
                                                @RequestParam String password) {

        Optional<UserModel> optUsuario = repository.findByLogin(login);
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        UserModel usuario = optUsuario.get();
        boolean valid = encoder.matches(password, usuario.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }
}
