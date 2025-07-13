package br.edu.ufersa.avance.model.entities;

import br.edu.ufersa.avance.model.enums.StatusPagamento;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.YearMonth;

@Entity
@Table(name = "Pagamentos")
public class Pagamento {
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_pagador")
    private Aluno pagador;

    @Column(nullable = false, name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(nullable = false, name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(nullable = false, name = "mes_referencia")
    private YearMonth mesRef;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusPagamento status;

    //Getters
    public long getId() { return id; }
    public Aluno getPagador() { return pagador; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public YearMonth getMesRef() { return mesRef; }
    public StatusPagamento getStatus() { return status; }

    //Setters
    public void setPagador(Aluno pagador) {
        if (pagador != null) this.pagador = pagador;
        else throw new IllegalArgumentException("A pessoa a pagar não pode ser vazia!");
    }
    public void setDataPagamento(LocalDate dataPagamento) {
        if (dataPagamento != null) this.dataPagamento = dataPagamento;
        else throw new IllegalArgumentException("A data de pagamento não pode estar vazia!");
    }
    public void setDataVencimento(LocalDate dataVencimento) {
        if (dataVencimento != null) this.dataVencimento = dataVencimento;
        else throw new IllegalArgumentException("A data de vencimento não pode estar vazia!");
    }
    public void setMesRef(YearMonth mesRef) {
        if(mesRef != null) this.mesRef = mesRef;
        else throw new IllegalArgumentException("O mês de referência não pode estar vazio!");
    }
    public void setStatus(StatusPagamento status) {
        if (status != null) this.status = status;
        else throw new IllegalArgumentException("O status do pagamento não pode estar vazio!");
    }

    //Construtores
    public Pagamento(){}
    public Pagamento(Aluno pagador, LocalDate dataPagamento, LocalDate dataVencimento, YearMonth mesRef,
                     StatusPagamento status){
        setPagador(pagador);
        setDataPagamento(dataPagamento);
        setDataVencimento(dataVencimento);
        setMesRef(mesRef);
        setStatus(status);
    }
}
