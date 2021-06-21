package com.thyago.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thyago.os.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

    // verifica os cpf's entre os tecnicos que estao no banco de dados
    @Query("SELECT obj FROM Pessoa obj WHERE obj.cpf =:cpf")
    Pessoa findByCPF(@Param("cpf") String cpf) ;
    
}