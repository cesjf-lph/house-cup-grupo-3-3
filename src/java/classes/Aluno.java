package classes;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Aluno implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomeCompleto;
    private Integer idade;
    private Boolean sexo;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private String grupo;

    @Override
    public String toString() {
        return "Candidato{" + "id=" + id + ", nomeCompleto=" + nomeCompleto + ", idade=" + idade + ", sexo=" + sexo + ", grupo=" + grupo + '}';
    }

    public Aluno(Long id, String nomeCompleto, Integer idade, Boolean sexo, String grupo) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.idade = idade;
        this.sexo = sexo;
        this.grupo = grupo;
    }

    public Aluno() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    
}
