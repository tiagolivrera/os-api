package com.thyago.os.config;

import com.thyago.os.services.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {  

    @Autowired
    private DBService dbService;

    @Bean // executa esse metodo toda vez que um objeto dessa classe for instanciado
    public void instanciaDB() {
        this.dbService.instanciaDB();
    }    
}
