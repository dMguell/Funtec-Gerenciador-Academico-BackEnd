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
import Funtec.Gerenciador_Academico.model.Professor;
import Funtec.Gerenciador_Academico.repository.ProfessorRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProfessorController {

    @Autowired
    ProfessorRepository professorRepository;

    @GetMapping("/professores")
    public List<Professor> getAllProfessores()
    {

        return professorRepository.findAll();

    }

    @PostMapping("/professores")
    public Professor cadastrarProfessor(@RequestBody Professor professor)
    {

        return professorRepository.save(professor);

    }

    @GetMapping("/professores/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id)
	{
		Professor professor = professorRepository.findById(id)
							.orElseThrow(() -> new ResourceNotFoundException("Professor não existe com este id: " + id));
		
		return ResponseEntity.ok(professor);
	}

    @PutMapping("/professores/{id}")
	public ResponseEntity<Professor> updateProfessor(@PathVariable Long id, @RequestBody Professor professorDetails)
	{
		Professor professor = professorRepository.findById(id)
							.orElseThrow(() -> new ResourceNotFoundException("Professor não existe com este id: " + id));
		
		
		
		professor.setNome(professorDetails.getNome());
		professor.setEmail(professorDetails.getEmail());
		professor.setSenha(professorDetails.getSenha());
		
        Professor professorAtualdizado = professorRepository.save(professor);
		
		return ResponseEntity.ok(professorAtualdizado);
	}

    @DeleteMapping("/professores/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProfessor(@PathVariable Long id)
	{
		Professor professor = professorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("professor não existe com este id: " + id));
		
		professorRepository.delete(professor);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deletado", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}
    
}
