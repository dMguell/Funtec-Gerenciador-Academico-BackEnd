package Funtec.Gerenciador_Academico.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Funtec.Gerenciador_Academico.model.Chamada;
import Funtec.Gerenciador_Academico.model.ChamadaId;

public interface ChamadaRepository extends JpaRepository<Chamada, String>{
	
	Chamada findByNaturalId(String NaturalId);
	
	//@Query( value = "SELECT c from Chamada c WHERE c.natural_id = ?1 c.cadastro = null")
	//Chamada findBynaturalId(String naturalid);
	
	@Query( value = "SELECT c from Chamada c WHERE c.cadastro = true")
	List<Chamada> findAllSortedByCadastro();
	
	@Query( value = "SELECT c from Chamada c WHERE c.cadastro = false")
	List<Chamada> findAllSortedByCadastroFalse();
	
}
