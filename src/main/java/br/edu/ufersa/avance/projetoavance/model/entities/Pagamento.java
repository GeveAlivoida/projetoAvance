package br.edu.ufersa.avance.projetoAvance.model.entities;

import br.edu.ufersa.avance.projetoAvance.model.enums.StatusPagamento;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(nullable = false, name = "id_aluno")
    private Aluno aluno;

    @Column(nullable = true, name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(nullable = false, name = "data_vencimento")
    private LocalDate dataValidade;

    @Column(nullable = false, name = "mes_referencia")
    private YearMonth mesRef;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusPagamento status;

    //Getters
    public long getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public LocalDate getDataValidade() { return dataValidade; }
    public YearMonth getMesRef() { return mesRef; }
    public StatusPagamento getStatus() { return status; }

    //Setters
    public void setId(long id) {
        if(id>0) this.id = id;
        else throw new IllegalArgumentException("Id inválida!");
    }
    public void setAluno(Aluno aluno) {
        if (aluno != null) this.aluno = aluno;
        else throw new IllegalArgumentException("A pessoa a pagar não pode ser vazia!");
    }
    public void setDataPagamento(LocalDate dataPagamento) {
        if ((status == StatusPagamento.PAGO || status == StatusPagamento.ATRASADO) && dataPagamento == null)
            throw new IllegalArgumentException("Pagamentos confirmados devem ter data de pagamento!");
        else this.dataPagamento = dataPagamento;
    }
    public void setDataValidade(LocalDate dataValidade) {
        if (dataValidade != null) this.dataValidade = dataValidade;
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
    public Pagamento(Aluno aluno, LocalDate dataPagamento, LocalDate dataValidade, YearMonth mesRef,
                     StatusPagamento status){
        setAluno(aluno);
        setDataPagamento(dataPagamento);
        setDataValidade(dataValidade);
        setMesRef(mesRef);
        setStatus(status);
    }
}
