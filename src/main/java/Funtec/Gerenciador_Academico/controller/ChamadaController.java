package Funtec.Gerenciador_Academico.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
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

	
		DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		DateFormat formatoString = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		
		Date data = new Date();
		data = formatoData.parse(dt_chamada);
		
		String stringData = formatoString.format(data);
		
		ChamadaId chamadaId = new ChamadaId();
		chamadaId.setTurmaId(idTurma);
		chamadaId.setAlunoId(idAluno);
		chamadaId.setDt_chamada(data);
		
		
		
		String naturalId = "" + idTurma + idAluno + stringData;
		System.out.println("\n\n\n NATURAL ID: " + naturalId + "\n\n\n DATA: " + stringData + "\n\n\n");
		
		Chamada chamada = chamadaRepository.findByNaturalId(naturalId);

		
		return ResponseEntity.ok(chamada);
	}
	
	@GetMapping("/chamadas/cadastrados")
	public List<Chamada> getChamadaCadastrados()
	{
		return chamadaRepository.findAllSortedByCadastro();
	}
	
	@GetMapping("/chamadas/{idTurma}/cadastrados")
	public List<Chamada> getChamadaCadastradosByTurma(@PathVariable long idTurma)
	{
		List<Chamada> retorno = new ArrayList<Chamada>();
		List<Chamada> cadastrados = chamadaRepository.findAllSortedByCadastro();
		
		for(int i = 0; i < cadastrados.size(); i++)
		{
			if (cadastrados.get(i).getTurma().getId() == idTurma)
			{
				retorno.add(cadastrados.get(i));
			}
		}
		
		return retorno;
	}
	
	@GetMapping("/chamadas/teste")
	public List<Long> teste(@RequestParam List<Long> values)
	{
		List<Long> aqui = new ArrayList<Long>();
		values.forEach(a -> {
			aqui.add(a);
		});
		
		return aqui;
	}
	
	@PostMapping("/chamadas/{idTurma}/{dt_chamada}/")
	public List<Chamada> cadastrarListaChamada(@PathVariable long idTurma,
											   @PathVariable String dt_chamada,
											   @RequestParam List<Long> idAlunos,
											   @RequestBody List<Chamada> chamadaBody) throws ParseException
	{
		
		System.out.println("\n\n\n LISTA ID ALUNOS ");
		idAlunos.forEach(a ->{
			System.out.println("ID alunos: " + a);
		});
		
		DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		
		Date data = new Date();
		data = formatoData.parse(dt_chamada);
		
		String stringData = formatoData.format(data);
	
		
		List<String> naturalsId = new ArrayList<String>();
		List<Chamada> chamadas = new ArrayList<Chamada>();
		List<ChamadaId> chamadasId = new ArrayList<ChamadaId>();
		
		
		Turma turma = turmaRepository.findById(idTurma)
				.orElseThrow(() -> new ResourceNotFoundException("não foi possível encontrar a turma com este id"));
		
		List<Aluno> alunos = alunoRepository.findAllById(idAlunos);
		

		for (int i = 0;i < alunos.size();i++)
		{
			ChamadaId chamadaId = new ChamadaId();
			chamadaId.setTurmaId(turma.getId());
			chamadaId.setDt_chamada(data);
			chamadaId.setAlunoId(alunos.get(i).getId());
			
			naturalsId.add(i, "" + turma.getId() + alunos.get(i).getId() + stringData);
				
			chamadasId.add(i, chamadaId);
		}
		
		// CASO SEJA O PRIMEIRO CADASTRO -----------------
		if (chamadaBody.get(0).getCadastro())
		{
			for (int i = 0; i< alunos.size(); i++)
			{
				Chamada chamada = new Chamada();
				
				chamada.setId(chamadasId.get(i));
				chamada.setAluno(alunos.get(i));
				chamada.setTurma(turma);
				
				chamada.setNaturalId(naturalsId.get(i).toString() + "C");
				chamada.setCadastro(chamadaBody.get(i).getCadastro());
				
				chamadas.add(chamada);
			}
		} 
		
		// CASO SEJA O UMA CHAMADA DE ALUNOS JA CADASTRADOS -----------------
		else
		{
			for (int i = 0; i< alunos.size(); i++)
			{
				Chamada chamada = new Chamada();
				
				chamada.setId(chamadasId.get(i));
				chamada.setAluno(alunos.get(i));
				chamada.setTurma(turma);
				chamada.setNaturalId(naturalsId.get(i));
				chamada.setPresenca(chamadaBody.get(i).getPresenca());
				
				chamadas.add(chamada);
			}
		}

		
		return chamadaRepository.saveAll(chamadas);
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
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		
		
		String formatado = formatter.format(dt_chamada);
		String naturalId = "" + chamada.getId().getTurmaId() + chamada.getId().getAlunoId() + formatado;
		System.out.println("\n\n\n NATURAL ID: " + naturalId + "\n\n\n");
		chamada.setNaturalId(naturalId);
		
		

		return chamadaRepository.save(chamada);

	}
	

	@PutMapping("/chamadas/{naturalId}")
	public ResponseEntity<Chamada> updateCurso(@PathVariable String naturalId, @RequestBody Chamada chamadaDetails) {

			Chamada chamada = chamadaRepository.findByNaturalId(naturalId);

			
		ChamadaId chamadaid = new ChamadaId();
		chamadaid.setAlunoId(chamadaDetails.getAluno().getId());
		chamadaid.setTurmaId(chamadaDetails.getTurma().getId());
		chamadaid.setDt_chamada(chamadaDetails.getId().getDt_chamada());
		
		chamada.setId(chamadaid);
		chamada.setAluno(chamadaDetails.getAluno());
		chamada.setTurma(chamadaDetails.getTurma());
		chamada.setPresenca(chamadaDetails.getPresenca());
		
		
		DateFormat formatoString = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		String formatado = formatoString.format(chamada.getId().getDt_chamada());
		
		
		naturalId = "" + chamada.getId().getTurmaId() + chamada.getId().getAlunoId() + formatado;
		
		chamada.setNaturalId(naturalId);

		Chamada chamadaAtualizada = chamadaRepository.save(chamada);

		return ResponseEntity.ok(chamadaAtualizada);

	}
	
	
    
	@DeleteMapping("/chamadas/{naturalId}")
	public ResponseEntity<Map<String, Boolean>> deleteChamada(@PathVariable String naturalId) {
		
		Chamada chamada = chamadaRepository.findByNaturalId(naturalId);
		
		chamadaRepository.delete(chamada);

		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deletado", Boolean.TRUE);

		return ResponseEntity.ok(response);
	}
	
}
