package br.edu.ufersa.avance.projetoAvance.model.enums;

public enum StatusProfessor {
    ATIVO("Ativo"),
    FERIAS("De férias"),
    INATIVO("Inativo"),
    LICENCA("De licença"),
    DEMITIDO("Demitido");

    private final String descricao;
    public String getDescricao() { return descricao; }

    StatusProfessor(String descricao) { this.descricao = descricao; }
}
