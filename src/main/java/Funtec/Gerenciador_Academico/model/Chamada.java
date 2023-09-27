package Funtec.Gerenciador_Academico.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Chamada")
public class Chamada {

	@EmbeddedId
	private ChamadaId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("turmaId")
	private Turma turma;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("alunoId")
	private Aluno aluno;
	
	@Column(name = "presenca")
	private Integer presenca;

	public Integer getPresenca() {
		return presenca;
	}

	public void setPresenca(Integer presenca) {
		this.presenca = presenca;
	}

	public ChamadaId getId() {
		return id;
	}

	public void setId(ChamadaId id) {
		this.id = id;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	
	
	
	
}
