package Funtec.Gerenciador_Academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Funtec.Gerenciador_Academico.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>{
    
}
