package br.com.jallcard.agenda.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Sua data de nascimento está inválida")
    private LocalDate dataNascimento;

    private String grauParentesco;

    @Valid
    @NotEmpty(message = "Adicione pelo menos um telefone")
    private List<TelefoneDTO> telefones = new ArrayList<>();
}
