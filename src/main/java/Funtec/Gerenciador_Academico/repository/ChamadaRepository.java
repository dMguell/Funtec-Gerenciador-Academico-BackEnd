package Funtec.Gerenciador_Academico.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Funtec.Gerenciador_Academico.model.Chamada;
import Funtec.Gerenciador_Academico.model.ChamadaId;

public interface ChamadaRepository extends JpaRepository<Chamada, ChamadaId>{
	
}
