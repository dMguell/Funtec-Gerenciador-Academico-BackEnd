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
import Funtec.Gerenciador_Academico.model.Aluno;

import Funtec.Gerenciador_Academico.repository.AlunoRepository;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AlunoController {
	
	@Autowired
	AlunoRepository alunoRepository;
	
	
	@GetMapping("/alunos")
	public List<Aluno> getAllAlunos()
	{
		return alunoRepository.findAll();
	}
	
	@PostMapping("/alunos")
	public Aluno cadastrarAluno(@RequestBody Aluno aluno)
	{
		return alunoRepository.save(aluno);
	}
	
	@GetMapping("/alunos/{id}")
	public ResponseEntity<Aluno> getAlunoById(@PathVariable Long id)
	{
		Aluno aluno = alunoRepository.findById(id)
							.orElseThrow(() -> new ResourceNotFoundException("Aluno não existe com este id: " + id));
		
		return ResponseEntity.ok(aluno);
	}
	
	@PutMapping("/alunos/{id}")
	public ResponseEntity<Aluno> updateAluno(@PathVariable Long id, @RequestBody Aluno alunoDetails)
	{
		Aluno aluno = alunoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Aluno não existe com este id: " + id));
		
		
		
		aluno.setNome(alunoDetails.getNome());
		aluno.setCpf(alunoDetails.getCpf());
		aluno.setDt_nascimento(alunoDetails.getDt_nascimento());
		
		Aluno alunoAtualizado = alunoRepository.save(aluno);
		
		return ResponseEntity.ok(alunoAtualizado);
	}
	
	@DeleteMapping("/alunos/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteAluno(@PathVariable Long id)
	{
		Aluno aluno = alunoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Aluno não existe com este id: " + id));
		
		alunoRepository.delete(aluno);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deletado", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}

}
