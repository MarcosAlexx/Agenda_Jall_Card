package br.com.jallcard.agenda.entity;

import br.com.jallcard.agenda.enums.TipoTelefone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telefones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Número de telefone é obrigatório")
    private String numero;

    @Enumerated(EnumType.STRING)
    private TipoTelefone tipo;

    @ManyToOne
    @JoinColumn(name = "contato_id")
    private Contato contato;
}
