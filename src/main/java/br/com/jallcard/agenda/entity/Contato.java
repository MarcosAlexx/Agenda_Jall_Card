package br.com.jallcard.agenda.entity;

import jakarta.persistence.*;
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

@Entity
@Table(name = "contatos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String sobrenome;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Sua data de nascimento está inválida")
    private LocalDate dataNascimento;

    private String grauParentesco;

    @OneToMany(mappedBy = "contato", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "Pelo menos um telefone é obrigatório")
    private List<Telefone> telefones = new ArrayList<>();


}
