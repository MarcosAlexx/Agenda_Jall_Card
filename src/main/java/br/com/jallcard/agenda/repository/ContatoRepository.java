package br.com.jallcard.agenda.repository;

import br.com.jallcard.agenda.entity.Contato;
import br.com.jallcard.agenda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    List<Contato> findAllByUser(User user);

    Optional<Contato> findByIdAndUser(Long id, User user);

    List<Contato> findByUserAndNomeContainingIgnoreCase(User user, String nome);
}
