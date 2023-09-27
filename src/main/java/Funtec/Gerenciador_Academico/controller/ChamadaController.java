package Funtec.Gerenciador_Academico.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Funtec.Gerenciador_Academico.Exception.ResourceNotFoundException;
import Funtec.Gerenciador_Academico.model.Aluno;
import Funtec.Gerenciador_Academico.model.Chamada;
import Funtec.Gerenciador_Academico.model.ChamadaId;
import Funtec.Gerenciador_Academico.model.Turma;
import Funtec.Gerenciador_Academico.repository.AlunoRepository;
import Funtec.Gerenciador_Academico.repository.ChamadaRepository;
import Funtec.Gerenciador_Academico.repository.TurmaRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ChamadaController {

	@Autowired
	ChamadaRepository chamadaRepository;

	@Autowired
	TurmaRepository turmaRepository;

	@Autowired
	AlunoRepository alunoRepository;

	@GetMapping("/chamadas")
	public List<Chamada> getAllChamadas() {
		return chamadaRepository.findAll();
	}

	@PostMapping("/chamadas/{idTurma}/{idAluno}/{dt_chamada}")
	public Chamada cadastrarChamada(@PathVariable long idTurma,
			@PathVariable long idAluno,
			@PathVariable String dt_chamada,
			@RequestBody Chamada chamadaBody) throws ParseException {
		Turma turma = turmaRepository.findById(idTurma)
				.orElseThrow(() -> new ResourceNotFoundException("não foi possível encontrar a turma com este id"));

		Aluno aluno = alunoRepository.findById(idAluno)
				.orElseThrow(() -> new ResourceNotFoundException("não foi possível encontrar aluno com este id"));

		ChamadaId chamadaid = new ChamadaId();
		chamadaid.setAlunoId(aluno.getId());
		chamadaid.setTurmaId(turma.getId());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		Date data = formatter.parse(dt_chamada);
		chamadaid.setDt_chamada(data);

		Chamada chamada = new Chamada();

		chamada.setId(chamadaid);
		chamada.setAluno(aluno);
		chamada.setTurma(turma);
		chamada.setPresenca(chamadaBody.getPresenca());
		return chamadaRepository.save(chamada);

	}
}
