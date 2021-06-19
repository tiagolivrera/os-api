package com.thyago.os.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tecnico extends Pessoa {

    @JsonIgnore // nao imprimir essa lista ao receber um GET -- evita loop na informacao
    @OneToMany(mappedBy = "tecnico") // mapeado por private Tecnico tecnico em OS.java
    private List<OS> list = new ArrayList<>();

    public Tecnico() {
        super();
    }

    public Tecnico(Integer id, String nome, String cpf, String telefone) {
        super(id, nome, cpf, telefone);
    }

    public List<OS> getList() {
        return list;
    }

    public void setList(List<OS> list) {
        this.list = list;
    }    
}
