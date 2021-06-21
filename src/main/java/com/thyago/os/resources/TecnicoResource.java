package com.thyago.os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.thyago.os.domain.Tecnico;
import com.thyago.os.dtos.TecnicoDTO;
import com.thyago.os.services.TecnicoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/tecnicos") // localhost:8080/tecnicos/1  {id = 1..}
public class TecnicoResource {

    @Autowired
    private TecnicoService service;

    //passa a url com o id, e retorna um tecnico
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico obj = service.findById(id);
        TecnicoDTO objDTO = new TecnicoDTO(obj);
        return ResponseEntity.ok().body(objDTO);
    }    

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {

        List<TecnicoDTO> listDTO = service.findAll()
                                    .stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
        
        /*
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listDTO = new ArrayList<>();
        
        //for (Tecnico obj : list) {
        //    listDTO.add(new TecnicoDTO(obj));
        //} 

        list.forEach(obj -> listDTO.add(new TecnicoDTO(obj)));
        */        
    }

    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // para fazer a atualizacao do tecnico, e necessario chama-lo pelo seu id
    // @Valid: todos os campos do tecnico estao preenchidos adequadamente
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
        TecnicoDTO newObj = new TecnicoDTO(service.update(id, objDTO));
        return ResponseEntity.ok().body(newObj);

    }

    // deleta um tecnico
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
