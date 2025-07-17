package br.edu.ufersa.avance.projetoavance.model.enums;

public enum StatusPagamento {
    PENDENTE("Pendente"),
    PAGO("Pago"),
    ATRASADO("Atrasado"),
    CANCELADO("Cancelado");

    private final String descricao;
    public String getDescricao() { return descricao; }

    StatusPagamento(String descricao) { this.descricao = descricao; }
}
