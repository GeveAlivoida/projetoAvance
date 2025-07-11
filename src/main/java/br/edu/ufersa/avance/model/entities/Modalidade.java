package br.edu.ufersa.avance.model.entities;

import br.edu.ufersa.avance.enums.TipoModalidade;

public class Modalidade {
    //Atributos
    private long id;
    private Professor professor;
    private String nome;
    private TipoModalidade tipo;
    private double valor;
    private int vagas;
    private int idadeMin;

    //Getters
    public long getId() {return id;}
    public Professor getProfessor() { return professor; }
    public String getNome() { return nome; }
    public TipoModalidade getTipo() { return tipo; }
    public double getValor() { return valor; }
    public int getVagas() { return vagas; }
    public int getIdadeMin() { return idadeMin; }

    //Setters
    public void setProfessor(Professor professor) {
        if (professor != null) this.professor = professor;
        else throw new IllegalArgumentException("O campo professor não pode estar vazio!");
    }
    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) this.nome = nome;
        else throw new IllegalArgumentException("O nome não pode estar vazio!");
    }
    public void setTipo(TipoModalidade tipo) {
        if (tipo != null) this.tipo = tipo;
        else throw new IllegalArgumentException("O tipo não pode estar vazio!");
    }
    public void setValor(double valor) {
        if (valor >= 0.0) this.valor = valor;
        else throw new IllegalArgumentException("O valor da aula não pode ser negativo!");
    }
    public void setVagas(int vagas) {
        if (vagas >= 0) this.vagas = vagas;
        else throw new IllegalArgumentException("O número de vagas não pode ser negativo!");
    }
    public void setIdadeMin(int idadeMin) {
        if (idadeMin >= 0) this.idadeMin = idadeMin;
        else throw new IllegalArgumentException("Idade não pode ser um número negativo!");
    }

    //Construtores
    public Modalidade(){}
    public Modalidade(Professor professor, String nome, TipoModalidade tipo, double valor, int vagas, int idadeMin){
        setProfessor(professor);
        setNome(nome);
        setTipo(tipo);
        setValor(valor);
        setVagas(vagas);
        setIdadeMin(idadeMin);
    }
}
