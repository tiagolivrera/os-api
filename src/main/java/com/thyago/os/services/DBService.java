package com.thyago.os.services;

import java.util.Arrays;

import com.thyago.os.domain.Cliente;
import com.thyago.os.domain.OS;
import com.thyago.os.domain.Tecnico;
import com.thyago.os.domain.enuns.Prioridade;
import com.thyago.os.domain.enuns.Status;
import com.thyago.os.repositories.ClienteRepository;
import com.thyago.os.repositories.OSRepository;
import com.thyago.os.repositories.TecnicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {

		Tecnico t1 = new Tecnico(null, "Joallyson Flores", "060.306.720-45", "(51) 34394-8430");
		Tecnico t2 = new Tecnico(null, "Eliab Honorio", "614.633.950-43", "(86) 44138-3026");
		Cliente c1 = new Cliente(null, "Thallyson Freitas", "765.525.150-90", "(71) 74715-5689");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1, t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}

}
