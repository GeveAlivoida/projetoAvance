package br.edu.ufersa.avance.model.enums;

public enum TipoModalidade {
    INDIVIDUAL("Individual"),
    EM_GRUPO("Em grupo"),
    WORKSHOP("Workshop");

    private final String descricao;
    public String getDescricao() { return descricao; }

    TipoModalidade(String descricao) { this.descricao = descricao; }
}
