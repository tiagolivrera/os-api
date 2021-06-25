package com.thyago.os.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends Pessoa {

    @OneToMany(mappedBy = "cliente") // mapeado por private Cliente cliente em OS.java
    private List<OS> list = new ArrayList<>();

    public Cliente() {
        super();
    }

    public Cliente(Integer id, String nome, String cpf, String telefone) {
        super(id, nome, cpf, telefone);
    }

    public List<OS> getList() {
        return list;
    }

    public void setList(List<OS> list) {
        this.list = list;
    }
}
