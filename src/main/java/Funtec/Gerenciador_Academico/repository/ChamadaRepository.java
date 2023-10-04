package Funtec.Gerenciador_Academico.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import Funtec.Gerenciador_Academico.model.Chamada;


public interface ChamadaRepository extends JpaRepository<Chamada, String>{
	
	Chamada findByNaturalId(String NaturalId);
	
	//@Query( value = "SELECT c from Chamada c WHERE c.natural_id = ?1 c.cadastro = null")
	//Chamada findBynaturalId(String naturalid);
	
	@Query( value = "SELECT c from Chamada c WHERE c.cadastro = true")
	List<Chamada> findAllSortedByCadastro();
	
	@Query( value = "SELECT c from Chamada c WHERE c.cadastro = false")
	List<Chamada> findAllSortedByCadastroFalse();
	
	@Query( value = "SELECT c from Chamada c WHERE c.aluno.id = ?1")
	List<Chamada> findAllByAluno(long idaluno);
	
}
