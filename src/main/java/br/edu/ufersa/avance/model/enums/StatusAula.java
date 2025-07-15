package br.edu.ufersa.avance.model.enums;

public enum StatusAula {
    AGENDADA("Agendada"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada"),
    ADIADA("Adiada");

    private final String descricao;
    public String getDescricao() { return descricao; }

    StatusAula(String descricao) { this.descricao = descricao; }
}
