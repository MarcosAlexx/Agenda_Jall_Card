package br.com.jallcard.agenda.service;

import br.com.jallcard.agenda.entity.Contato;
import br.com.jallcard.agenda.entity.Telefone;
import br.com.jallcard.agenda.entity.User;
import br.com.jallcard.agenda.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @InjectMocks
    private ContatoService contatoService;

    private User user;
    private Contato contato;

    @BeforeEach
    void setUp() {
        // Cria um usuário de teste
        user = new User();
        user.setId(1L);
        user.setEmail("teste@email.com");

        // Cria um contato de teste
        contato = new Contato();
        contato.setId(1L);
        contato.setNome("Maria");
        contato.setSobrenome("Silva");
        contato.setDataNascimento(LocalDate.of(1990, 5, 15));
        contato.setUser(user);

        // Adiciona telefone
        Telefone telefone = new Telefone();
        telefone.setNumero("11999998888");
        contato.setTelefones(new ArrayList<>(List.of(telefone)));
    }

    @Test
    @DisplayName("Deve listar todos os contatos do usuário")
    void deveListarTodosContatosDoUsuario() {
        // PREPARAR
        List<Contato> contatos = List.of(contato);
        when(contatoRepository.findAllByUser(user)).thenReturn(contatos);

        // EXECUTAR
        List<Contato> resultado = contatoService.listarTodos(user);

        // VERIFICAR
        assertEquals(1, resultado.size());
        assertEquals("Maria", resultado.get(0).getNome());
    }

    @Test
    @DisplayName("Deve buscar contato por ID")
    void deveBuscarContatoPorId() {
        // PREPARAR
        when(contatoRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(contato));

        // EXECUTAR
        Contato resultado = contatoService.buscarPorId(1L, user);

        // VERIFICAR
        assertNotNull(resultado);
        assertEquals("Maria", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar erro quando contato não encontrado")
    void deveLancarErroQuandoContatoNaoEncontrado() {
        // PREPARAR
        when(contatoRepository.findByIdAndUser(99L, user)).thenReturn(Optional.empty());

        // EXECUTAR e VERIFICAR
        Exception erro = assertThrows(IllegalArgumentException.class, () -> {
            contatoService.buscarPorId(99L, user);
        });

        assertEquals("Contato não encontrado", erro.getMessage());
    }

    @Test
    @DisplayName("Deve salvar contato com sucesso")
    void deveSalvarContatoComSucesso() {
        // PREPARAR
        when(contatoRepository.save(any(Contato.class))).thenReturn(contato);

        // EXECUTAR
        Contato resultado = contatoService.salvar(contato, user);

        // VERIFICAR
        assertNotNull(resultado);
        assertEquals(user, resultado.getUser());
        verify(contatoRepository, times(1)).save(contato);
    }

    @Test
    @DisplayName("Deve lançar erro ao salvar contato sem telefone")
    void deveLancarErroAoSalvarContatoSemTelefone() {
        // PREPARAR
        contato.setTelefones(new ArrayList<>());

        // EXECUTAR e VERIFICAR
        Exception erro = assertThrows(IllegalArgumentException.class, () -> {
            contatoService.salvar(contato, user);
        });

        assertEquals("Adicione pelo menos um telefone", erro.getMessage());
    }

    @Test
    @DisplayName("Deve deletar contato com sucesso")
    void deveDeletarContatoComSucesso() {
        // PREPARAR
        when(contatoRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(contato));
        doNothing().when(contatoRepository).delete(contato);

        // EXECUTAR
        contatoService.deletar(1L, user);

        // VERIFICAR
        verify(contatoRepository, times(1)).delete(contato);
    }

    @Test
    @DisplayName("Deve buscar contatos por nome")
    void deveBuscarContatosPorNome() {
        // PREPARAR
        List<Contato> contatos = List.of(contato);
        when(contatoRepository.findByUserAndNomeContainingIgnoreCase(user, "mar")).thenReturn(contatos);

        // EXECUTAR
        List<Contato> resultado = contatoService.buscarPorNome(user, "mar");

        // VERIFICAR
        assertEquals(1, resultado.size());
        assertEquals("Maria", resultado.get(0).getNome());
    }
}
