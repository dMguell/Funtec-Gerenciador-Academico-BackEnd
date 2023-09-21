package Funtec.Gerenciador_Academico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Funtec.Gerenciador_Academico.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
    
}
