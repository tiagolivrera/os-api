package com.thyago.os.services;

import java.util.List;
import java.util.Optional;

import com.thyago.os.domain.Tecnico;
import com.thyago.os.dtos.TecnicoDTO;
import com.thyago.os.repositories.TecnicoRepository;
import com.thyago.os.services.exceptions.DataIntegrityViolationException;
import com.thyago.os.services.exceptions.ObjectNotFoundExcecption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TecnicoService {
    
    @Autowired
    private TecnicoRepository repository; // a camada repository entra em contato com o db, entao instancia ele para recuperar as info do tecnico

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id); // optional: dado o id, o tecnico pode existir ou n
        return obj.orElseThrow(() -> new ObjectNotFoundExcecption(
            "Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }
    
    public Tecnico create(TecnicoDTO objDTO) {
        // verifica se existe um tecnico com o mesmo cpf no banco de dados
        if(findByCPF(objDTO) != null) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
        return repository.save(newObj);
    }

    private Tecnico findByCPF(TecnicoDTO objDTO) {
        Tecnico obj = repository.findByCPF(objDTO.getCpf());
        if (obj != null) {
            return obj;
        } return null;
    }

}
