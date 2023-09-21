package Funtec.Gerenciador_Academico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome_curso")
    private String nome_curso;
    
    @Column(name = "descricao_curso")
    private String descricao_curso;

    @Column(name = "carga_horaria")
    private int carga_horaria;

    @Column(name = "carga_horaria_diaria")
    private  double carga_horaria_diaria;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_curso() {
        return nome_curso;
    }

    public void setNome_curso(String nome_curso) {
        this.nome_curso = nome_curso;
    }

    public String getDescricao_curso() {
        return descricao_curso;
    }

    public void setDescricao_curso(String descricao_curso) {
        this.descricao_curso = descricao_curso;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public double getCarga_horaria_diaria() {
        return carga_horaria_diaria;
    }

    public void setCarga_horaria_diaria(double carga_horaria_diaria) {
        this.carga_horaria_diaria = carga_horaria_diaria;
    }

}
