package br.com.jallcard.agenda.controller;

import br.com.jallcard.agenda.dto.ContatoDTO;
import br.com.jallcard.agenda.dto.TelefoneDTO;
import br.com.jallcard.agenda.entity.Contato;
import br.com.jallcard.agenda.entity.User;
import br.com.jallcard.agenda.mapper.ContatoMapper;
import br.com.jallcard.agenda.service.ContatoService;
import br.com.jallcard.agenda.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/contatos")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;
    private final UserService userService;
    private final ContatoMapper contatoMapper;

    private User getUsuarioLogado(Authentication auth) {
        return userService.buscarPorEmail(auth.getName());
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String nome,
                         Authentication auth,
                         Model model) {

        User user = getUsuarioLogado(auth);
        List<Contato> contatos = (nome != null && !nome.isBlank())
                ? contatoService.buscarPorNome(user, nome)
                : contatoService.listarTodos(user);

        model.addAttribute("contatos", contatos);
        model.addAttribute("nome", nome);
        return "contatos/lista";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        ContatoDTO dto = new ContatoDTO();
        dto.setTelefones(new ArrayList<>(List.of(new TelefoneDTO())));
        model.addAttribute("contato", dto);
        return "contatos/form";
    }

    @PostMapping("/novo")
    public String salvar(@Valid @ModelAttribute("contato") ContatoDTO dto,
                         BindingResult result,
                         Authentication auth,
                         RedirectAttributes redirect) {

        if (result.hasErrors()) return "contatos/form";

        User user = getUsuarioLogado(auth);
        contatoService.salvar(contatoMapper.toEntity(dto), user);
        redirect.addFlashAttribute("mensagemSucesso", "Contato salvo com sucesso!");
        return "redirect:/contatos";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id,
                             Authentication auth,
                             Model model) {

        User user = getUsuarioLogado(auth);
        Contato contato = contatoService.buscarPorId(id, user);
        model.addAttribute("contato", contatoMapper.toDTO(contato));
        return "contatos/form";
    }

    @PostMapping("/{id}/editar")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("contato") ContatoDTO dto,
                            BindingResult result,
                            Authentication auth,
                            RedirectAttributes redirect) {

        if (result.hasErrors()) return "contatos/form";

        User user = getUsuarioLogado(auth);
        contatoService.atualizar(id, contatoMapper.toEntity(dto), user);
        redirect.addFlashAttribute("mensagemSucesso", "Contato atualizado com sucesso!");
        return "redirect:/contatos";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id,
                          Authentication auth,
                          RedirectAttributes redirect) {

        User user = getUsuarioLogado(auth);
        contatoService.deletar(id, user);
        redirect.addFlashAttribute("mensagemSucesso", "Contato removido com sucesso!");
        return "redirect:/contatos";
    }
}
