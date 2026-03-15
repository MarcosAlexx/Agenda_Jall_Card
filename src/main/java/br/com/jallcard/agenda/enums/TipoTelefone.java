package br.com.jallcard.agenda.enums;

public enum TipoTelefone {

    CELULAR("Celular"),
    RESIDENCIAL("Residencial"),
    COMERCIAL("Comercial");

    private final String descricao;

    TipoTelefone(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
