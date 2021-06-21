package com.thyago.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.thyago.os.domain.Pessoa;
import com.thyago.os.domain.Tecnico;
import com.thyago.os.dtos.TecnicoDTO;
import com.thyago.os.repositories.PessoaRepository;
import com.thyago.os.repositories.TecnicoRepository;
import com.thyago.os.services.exceptions.DataIntegrityViolationException;
import com.thyago.os.services.exceptions.ObjectNotFoundExcecption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TecnicoService {
    
    @Autowired
    private TecnicoRepository repository; // a camada repository entra em contato com o db, entao instancia ele para recuperar as info do tecnico

    @Autowired
    private PessoaRepository pessoaRepository;

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

    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        Tecnico oldObj = findById(id);

        // nao pode atualizar o CPF usando um CPF que ja existe na base de dados
        if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
            throw new DataIntegrityViolationException("CPF já cadastrado na base de dados!");
        }

        oldObj.setNome(objDTO.getNome());
        oldObj.setCpf(objDTO.getCpf());
        oldObj.setTelefone(objDTO.getTelefone());

        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id); // se nao houver um tecnico nesse id, lanca a excecao
        if(obj.getList().size() > 0) { // se o tecnico tiver ordens de servico, nao pode deletar
            throw new DataIntegrityViolationException("O técnico possui ordens de serviço, não pode ser deletado!");
        }
        repository.deleteById(id);
    }

    // busca uma pessoa pelo CPF 
    private Pessoa findByCPF(TecnicoDTO objDTO) {
        Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
        if (obj != null) {
            return obj;
        } return null;
    }

    
}
