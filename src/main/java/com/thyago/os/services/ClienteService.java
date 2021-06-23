package com.thyago.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.thyago.os.domain.Cliente;
import com.thyago.os.domain.Pessoa;
import com.thyago.os.dtos.ClienteDTO;
import com.thyago.os.repositories.ClienteRepository;
import com.thyago.os.repositories.PessoaRepository;
import com.thyago.os.services.exceptions.DataIntegrityViolationException;
import com.thyago.os.services.exceptions.ObjectNotFoundExcecption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow( () -> new ObjectNotFoundExcecption(
            "Cliente não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()
        ));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        if(findByCPF(objDTO) != null) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }
        return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        Cliente oldObj = findById(id);

        if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getNome());
        oldObj.setTelefone(objDTO.getTelefone());
        return repository.save(oldObj);
    }

    private Pessoa findByCPF(ClienteDTO objDTO) {
        Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());

        if (obj != null) {
            return obj;
        } return null;
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);

        if(obj.getList().size() > 0) {
            throw new DataIntegrityViolationException("Pessoa possui Ordens de Serviço, não pode ser deletado!");
        }

        repository.deleteById(id);
    }    
}
