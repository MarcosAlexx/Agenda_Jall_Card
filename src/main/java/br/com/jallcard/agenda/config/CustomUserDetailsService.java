package br.com.jallcard.agenda.config;

import br.com.jallcard.agenda.entity.User;
import br.com.jallcard.agenda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        // Busca por email OU username
        User user = userRepository.findByEmailOrUsername(login, login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));

        // Retorna um UserDetails que o Spring Security entende
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
