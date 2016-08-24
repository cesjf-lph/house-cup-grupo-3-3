package classes;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Ocorrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int pontos;
     @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
        
    }

    public Date getDescricao() {
        return descricao;
    }

    public void setDescricao(Date descricao) {
        this.descricao = descricao;
    }
}
