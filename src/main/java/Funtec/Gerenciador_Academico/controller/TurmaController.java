package Funtec.Gerenciador_Academico.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import Funtec.Gerenciador_Academico.model.Curso;
import Funtec.Gerenciador_Academico.model.Professor;
import Funtec.Gerenciador_Academico.model.Turma;
import Funtec.Gerenciador_Academico.repository.CursoRepository;
import Funtec.Gerenciador_Academico.repository.ProfessorRepository;
import Funtec.Gerenciador_Academico.repository.TurmaRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TurmaController {

	@Autowired
	TurmaRepository turmaRepository;

	@Autowired
	CursoRepository cursoRepository;

	@Autowired
	ProfessorRepository professorRepository;

	@GetMapping("/turmas")
	public List<Turma> getAllTurmas() {
		return turmaRepository.findAll();

	}

	@GetMapping("/turmas/{id}")
	public ResponseEntity<Turma> getTurmaById(@PathVariable Long id) {
		Turma turma = turmaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Turma não existe com este id: " + id));

		return ResponseEntity.ok(turma);
	}

	@PostMapping("/turmas/{idCurso}/{idProfessor}")
	public Turma cadastrarTurma(@PathVariable long idCurso,
			@PathVariable long idProfessor) {
		Curso curso = cursoRepository.findById(idCurso)
				.orElseThrow(() -> new ResourceNotFoundException("não foi encontrado curso com este id"));

		Professor professor = professorRepository.findById(idProfessor)
				.orElseThrow(() -> new ResourceNotFoundException("não foi encontrado professor com este id"));

		Turma turma = new Turma();
		turma.setCurso(curso);
		turma.setProfessor(professor);

		return turmaRepository.save(turma);
	}

	@PutMapping("/turmas/{id}")
	public ResponseEntity<Turma> updateCurso(@PathVariable Long id, @RequestBody Turma turmaDetails) {
		Turma turma = turmaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Turma não existe com este id: " + id));

		turma.setAlunos(turmaDetails.getAlunos());
		turma.setCurso(turmaDetails.getCurso());
		turma.setProfessor(turmaDetails.getProfessor());

		Turma turmaAtualizada = turmaRepository.save(turma);

		return ResponseEntity.ok(turmaAtualizada);

	}

	@DeleteMapping("/turmas/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteTurma(@PathVariable Long id) {
		Turma turma = turmaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Turma não existe com este id: " + id));

		turmaRepository.delete(turma);

		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deletado", Boolean.TRUE);

		return ResponseEntity.ok(response);
	}

}
