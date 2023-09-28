package Funtec.Gerenciador_Academico.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	
	@GetMapping("/chamadas/{idTurma}/{idAluno}/{dt_chamada}")
	public ResponseEntity<Chamada> getChamadaById(@PathVariable long idTurma,
												  @PathVariable long idAluno,
												  @PathVariable String dt_chamada) throws ParseException 
	{
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss");
		Date data = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").parse(dt_chamada);
		String formatado = formatter.format(dt_chamada);
		
		System.out.println("\n\n\n DATA NAO FORMATADA: " + formatado + "\n\n\n");
		
		ChamadaId chamadaId = new ChamadaId();
		chamadaId.setTurmaId(idTurma);
		chamadaId.setAlunoId(idAluno);
		//chamadaId.setDt_chamada(dt_chamada);
		
		
		String naturalId = "" + idTurma + idAluno + formatado;
		System.out.println("\n\n\n NATURAL ID: " + naturalId + "\n\n\n");
	
		Chamada chamada = chamadaRepository.findBynaturalId(naturalId);
		
		return ResponseEntity.ok(chamada);
	}
	

	@PostMapping("/chamadas/{idTurma}/{idAluno}/{dt_chamada}")
	public Chamada cadastrarChamada(@PathVariable long idTurma,
									@PathVariable long idAluno,
									@PathVariable Date dt_chamada,
									@RequestBody Chamada chamadaBody)  
	{
		Turma turma = turmaRepository.findById(idTurma)
				.orElseThrow(() -> new ResourceNotFoundException("não foi possível encontrar a turma com este id"));

		Aluno aluno = alunoRepository.findById(idAluno)
				.orElseThrow(() -> new ResourceNotFoundException("não foi possível encontrar aluno com este id"));

		ChamadaId chamadaid = new ChamadaId();
		chamadaid.setAlunoId(aluno.getId());
		chamadaid.setTurmaId(turma.getId());
		
		//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		//Date data = formatter.parse(dt_chamada);
		
		chamadaid.setDt_chamada(dt_chamada);

		Chamada chamada = new Chamada();

		chamada.setId(chamadaid);
		chamada.setAluno(aluno);
		chamada.setTurma(turma);
		chamada.setPresenca(chamadaBody.getPresenca());
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy:HH:mm");
		
		
		String formatado = formatter.format(dt_chamada);
		String naturalId = "" + chamada.getId().getTurmaId() + chamada.getId().getAlunoId() + formatado;
		System.out.println("\n\n\n NATURAL ID: " + naturalId + "\n\n\n");
		chamada.setNaturalId(naturalId);
		

		return chamadaRepository.save(chamada);

	}
	
	/*
	@PutMapping("/chamadas/{id}")
	public ResponseEntity<Chamada> updateCurso(@PathVariable ChamadaId id, @RequestBody Chamada chamadaDetails) {

			Chamada chamada = chamadaRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Não existe chamada com este id: " + id));

		

		ChamadaId chamadaid = new ChamadaId();
		chamadaid.setAlunoId(chamadaDetails.getAluno().getId());
		chamadaid.setTurmaId(chamadaDetails.getTurma().getId());
		chamadaid.setDt_chamada(chamadaDetails.getId().getDt_chamada());
		
		chamada.setId(chamadaid);
		chamada.setAluno(chamadaDetails.getAluno());
		chamada.setTurma(chamadaDetails.getTurma());
		chamada.setPresenca(chamadaDetails.getPresenca());

		Chamada chamadaAtualizada = chamadaRepository.save(chamada);

		return ResponseEntity.ok(chamadaAtualizada);

	}
	*/
	
    /*
	@DeleteMapping("/chamadas/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteChamada(@PathVariable ChamadaId id) {
		Chamada chamada = chamadaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Chamada não existe com este id: " + id));

		chamadaRepository.delete(chamada);

		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deletado", Boolean.TRUE);

		return ResponseEntity.ok(response);
	}
	*/
}
