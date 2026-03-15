package br.com.jallcard.agenda.dto;

import br.com.jallcard.agenda.enums.TipoTelefone;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {

    @NotBlank(message = "Número de telefone é obrigatório")
    private String numero;

    private TipoTelefone tipo;
}
