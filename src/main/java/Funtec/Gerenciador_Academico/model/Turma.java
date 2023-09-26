package Funtec.Gerenciador_Academico.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Turmas")
public class Turma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Professor professor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Curso curso;
	
	@OneToMany(mappedBy = "turma",
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	private List<Chamada> alunos = new ArrayList<Chamada>();

	public List<Chamada> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Chamada> alunos) {
		this.alunos = alunos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	
	
	
}
