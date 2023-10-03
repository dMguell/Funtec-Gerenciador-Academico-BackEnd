package Funtec.Gerenciador_Academico.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Alunos")
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "nome_aluno")
	private String nome;
	
	@Column(name = "cpf")
	private String cpf;

	@Column(name = "dt_nascimento")
	private Date dt_nascimento;
	
	@OneToMany(mappedBy = "aluno", cascade = {CascadeType.MERGE, CascadeType.DETACH})
	@JsonIgnore
	private List<Chamada> turmas = new ArrayList<Chamada>();
	
	public List<Chamada> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Chamada> turmas) {
		this.turmas = turmas;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDt_nascimento() {
		return dt_nascimento;
	}

	public void setDt_nascimento(Date dt_nascimento) {
		this.dt_nascimento = dt_nascimento;
	}

}
