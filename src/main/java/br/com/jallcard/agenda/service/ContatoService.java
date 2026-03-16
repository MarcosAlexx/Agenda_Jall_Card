package br.com.jallcard.agenda.service;

import br.com.jallcard.agenda.entity.Contato;
import br.com.jallcard.agenda.entity.Telefone;
import br.com.jallcard.agenda.entity.User;
import br.com.jallcard.agenda.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository contatoRepository;

    public List<Contato> listarTodos(User user) {
        return contatoRepository.findAllByUser(user);
    }

    public List<Contato> buscarPorNome(User user, String nome) {
        return contatoRepository.findByUserAndNomeContainingIgnoreCase(user, nome);
    }

    public Contato buscarPorId(Long id, User user) {
        return contatoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado"));
    }

    public Contato salvar(Contato contato, User user) {
        if (contato.getTelefones() == null || contato.getTelefones().isEmpty()) {
            throw new IllegalArgumentException("Adicione pelo menos um telefone");
        }

        contato.setUser(user);

        for (Telefone telefone : contato.getTelefones()) {
            telefone.setContato(contato);
        }

        return contatoRepository.save(contato);
    }

    public Contato atualizar(Long id, Contato dadosNovos, User user) {
        Contato contato = buscarPorId(id, user);

        contato.setNome(dadosNovos.getNome());
        contato.setSobrenome(dadosNovos.getSobrenome());
        contato.setDataNascimento(dadosNovos.getDataNascimento());
        contato.setGrauParentesco(dadosNovos.getGrauParentesco());

        contato.getTelefones().clear();

        for (Telefone telefone : dadosNovos.getTelefones()) {
            telefone.setContato(contato);
            contato.getTelefones().add(telefone);
        }

        if (contato.getTelefones().isEmpty()) {
            throw new IllegalArgumentException("Adicione pelo menos um telefone");
        }

        return contatoRepository.save(contato);
    }

    public void deletar(Long id, User user) {
        Contato contato = buscarPorId(id, user);
        contatoRepository.delete(contato);
    }


}
