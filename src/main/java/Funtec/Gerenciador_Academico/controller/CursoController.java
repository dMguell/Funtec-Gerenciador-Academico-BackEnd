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
import Funtec.Gerenciador_Academico.repository.CursoRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CursoController {
    
	@Autowired
	CursoRepository cursoRepository;
	
	@GetMapping("/cursos")
	public List<Curso> getAllCursos()
	{
		return cursoRepository.findAll();
	}
	
	@PostMapping("/cursos")
	public Curso cadastrarCurso(@RequestBody Curso curso)
	{
		return cursoRepository.save(curso);
	}
	
	@GetMapping("/cursos/{id}")
	public ResponseEntity<Curso> getCursoById(@PathVariable Long id)
	{
		Curso curso = cursoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Curso não existe com este id: " + id));
		
		return ResponseEntity.ok(curso);
	}
	
	@PutMapping("/cursos/{id}")
	public ResponseEntity<Curso> updateCurso(@PathVariable Long id, @RequestBody Curso cursoDetails)
	{
		Curso curso = cursoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Curso não existe com este id: " + id));
		
		curso.setNome_curso(cursoDetails.getNome_curso());
		curso.setDescricao_curso(cursoDetails.getDescricao_curso());
		curso.setDt_inicio(cursoDetails.getDt_inicio());
		curso.setDt_final(cursoDetails.getDt_final());
		curso.setCarga_horaria(cursoDetails.getCarga_horaria());
		curso.setCarga_horaria_diaria(cursoDetails.getCarga_horaria_diaria());
		curso.setDias_de_curso(cursoDetails.getDias_de_curso());
		
		Curso cursoAtualizado = cursoRepository.save(curso);
		
		return ResponseEntity.ok(cursoAtualizado);
		
	}
	
	@DeleteMapping("/cursos/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCurso(@PathVariable Long id)
	{
		Curso curso = cursoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Curso não existe com este id: " + id));
		
		cursoRepository.delete(curso);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deletado", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}
	
}
