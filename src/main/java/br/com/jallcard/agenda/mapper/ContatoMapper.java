package br.com.jallcard.agenda.mapper;

import br.com.jallcard.agenda.dto.ContatoDTO;
import br.com.jallcard.agenda.dto.TelefoneDTO;
import br.com.jallcard.agenda.entity.Contato;
import br.com.jallcard.agenda.entity.Telefone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContatoMapper {

    public Contato toEntity(ContatoDTO dto) {
        Contato contato = new Contato();
        contato.setId(dto.getId());
        contato.setNome(dto.getNome());
        contato.setSobrenome(dto.getSobrenome());
        contato.setDataNascimento(dto.getDataNascimento());
        contato.setGrauParentesco(dto.getGrauParentesco());
        contato.setTelefones(toTelefoneEntityList(dto.getTelefones()));
        return contato;
    }

    public ContatoDTO toDTO(Contato contato) {
        ContatoDTO dto = new ContatoDTO();
        dto.setId(contato.getId());
        dto.setNome(contato.getNome());
        dto.setSobrenome(contato.getSobrenome());
        dto.setDataNascimento(contato.getDataNascimento());
        dto.setGrauParentesco(contato.getGrauParentesco());
        dto.setTelefones(toTelefoneDTOList(contato.getTelefones()));
        return dto;
    }

    private List<Telefone> toTelefoneEntityList(List<TelefoneDTO> dtos) {
        List<Telefone> telefones = new ArrayList<>();
        for (TelefoneDTO dto : dtos) {
            Telefone telefone = new Telefone();
            telefone.setNumero(dto.getNumero());
            telefone.setTipo(dto.getTipo());
            telefones.add(telefone);
        }
        return telefones;
    }

    private List<TelefoneDTO> toTelefoneDTOList(List<Telefone> telefones) {
        List<TelefoneDTO> dtos = new ArrayList<>();
        for (Telefone tel : telefones) {
            TelefoneDTO dto = new TelefoneDTO();
            dto.setNumero(tel.getNumero());
            dto.setTipo(tel.getTipo());
            dtos.add(dto);
        }
        return dtos;
    }
}
