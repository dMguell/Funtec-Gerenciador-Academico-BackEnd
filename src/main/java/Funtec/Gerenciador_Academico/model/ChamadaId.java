package Funtec.Gerenciador_Academico.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ChamadaId  implements Serializable{

	@Column(name = "turma_id")
	private long turmaId;
	
	@Column(name = "aluno_id")
	private long alunoId;
	
	@Column(name = "dt_chamada")
	private Date dt_chamada;
	

	public Date getDt_chamada() {
		return dt_chamada;
	}

	public void setDt_chamada(Date dt_chamada) {
		this.dt_chamada = dt_chamada;
	}

	public long getTurmaId() {
		return turmaId;
	}

	public void setTurmaId(long turmaId) {
		this.turmaId = turmaId;
	}

	public long getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(long alunoId) {
		this.alunoId = alunoId;
	}
	
	
	
	
	
}
