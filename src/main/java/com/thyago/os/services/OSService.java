package com.thyago.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.thyago.os.domain.Cliente;
import com.thyago.os.domain.OS;
import com.thyago.os.domain.Tecnico;
import com.thyago.os.domain.enuns.Prioridade;
import com.thyago.os.domain.enuns.Status;
import com.thyago.os.dtos.OSDTO;
import com.thyago.os.repositories.OSRepository;
import com.thyago.os.services.exceptions.ObjectNotFoundExcecption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OSService {

    @Autowired
    private OSRepository repository;

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private ClienteService clienteService;

    public OS findById(Integer id) {
        Optional<OS> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExcecption(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getName()));
    }

    public List<OS> findAll() {
        return repository.findAll();
    }

    public OS create(@Valid OSDTO obj) {
        return fromDTO(obj);
    }

    public OS update(@Valid OSDTO obj) {
        findById(obj.getId()); // se o OS nao existir, lanca uma excecao
        return fromDTO(obj);
    }

    private OS fromDTO(OSDTO obj) {
        OS newObj = new OS();
        newObj.setId(obj.getId());
        newObj.setObservacoes(obj.getObservacoes());
        newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        newObj.setStatus(Status.toEnum(obj.getStatus()));

        Tecnico tec = tecnicoService.findById(obj.getTecnico());
        Cliente cli = clienteService.findById(obj.getCliente());

        newObj.setTecnico(tec);
        newObj.setCliente(cli);

        if (newObj.getStatus().getCod().equals(2)) {
            newObj.setDataFechamento(LocalDateTime.now());
        }

        return repository.save(newObj);

    }

    // a ordem de servico nao pode ser deletada

}
