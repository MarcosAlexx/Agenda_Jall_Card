package br.com.jallcard.agenda.controller;

import br.com.jallcard.agenda.dto.UsuarioDTO;
import br.com.jallcard.agenda.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastroForm(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute("usuario") UsuarioDTO dto,
                            BindingResult result,
                            Model model) {

        if (result.hasErrors()) {
            return "cadastro";
        }

        try {
            userService.cadastrar(dto.getEmail(), dto.getUsername(), dto.getPassword());
            return "redirect:/login?cadastro=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro";
        }
    }
}
