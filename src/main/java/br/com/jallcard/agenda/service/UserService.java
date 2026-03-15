package br.com.jallcard.agenda.service;

import br.com.jallcard.agenda.entity.User;
import br.com.jallcard.agenda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User cadastrar(String email, String username, String senha) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username já cadastrado");
        }

        String senhaCriptografada = passwordEncoder.encode(senha);
        User user = new User(email, username, senhaCriptografada);
        return userRepository.save(user);
    }

    public User buscarPorEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public User buscarPorUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public User buscarPorEmailOuUsername(String login) {
        return userRepository.findByEmailOrUsername(login, login)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
}
