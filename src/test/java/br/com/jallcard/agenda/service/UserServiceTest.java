package br.com.jallcard.agenda.service;

import br.com.jallcard.agenda.entity.User;
import br.com.jallcard.agenda.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void deveCadastrarUsuarioComSucesso() {
        // PREPARAR
        when(userRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(userRepository.existsByUsername("joao")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("senhaCriptografada");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // EXECUTAR
        User resultado = userService.cadastrar("joao@email.com", "joao", "123456");

        // VERIFICAR
        assertNotNull(resultado);
        assertEquals("joao@email.com", resultado.getEmail());
        assertEquals("joao", resultado.getUsername());
        assertEquals("senhaCriptografada", resultado.getPassword());
    }

    @Test
    @DisplayName("Deve lançar erro quando email já existe")
    void deveLancarErroQuandoEmailJaExiste() {
        // PREPARAR
        when(userRepository.existsByEmail("joao@email.com")).thenReturn(true);

        // EXECUTAR e VERIFICAR
        Exception erro = assertThrows(IllegalArgumentException.class, () -> {
            userService.cadastrar("joao@email.com", "joao", "123456");
        });

        assertEquals("Email já cadastrado", erro.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando username já existe")
    void deveLancarErroQuandoUsernameJaExiste() {
        // PREPARAR
        when(userRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(userRepository.existsByUsername("joao")).thenReturn(true);

        // EXECUTAR e VERIFICAR
        Exception erro = assertThrows(IllegalArgumentException.class, () -> {
            userService.cadastrar("joao@email.com", "joao", "123456");
        });

        assertEquals("Username já cadastrado", erro.getMessage());
    }

    @Test
    @DisplayName("Deve buscar usuário por email")
    void deveBuscarUsuarioPorEmail() {
        // PREPARAR
        User user = new User("joao@email.com", "joao", "123456");
        when(userRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(user));

        // EXECUTAR
        User resultado = userService.buscarPorEmail("joao@email.com");

        // VERIFICAR
        assertNotNull(resultado);
        assertEquals("joao@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Deve lançar erro quando usuário não encontrado por email")
    void deveLancarErroQuandoUsuarioNaoEncontradoPorEmail() {
        // PREPARAR
        when(userRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        // EXECUTAR e VERIFICAR
        Exception erro = assertThrows(IllegalArgumentException.class, () -> {
            userService.buscarPorEmail("naoexiste@email.com");
        });

        assertEquals("Usuário não encontrado", erro.getMessage());
    }
}
