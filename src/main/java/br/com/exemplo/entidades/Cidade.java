package br.com.exemplo.entidades;

import br.com.exemplo.util.Cadastravel;
import br.com.exemplo.util.Validacao;
import br.com.exemplo.util.anotacoes.CRUD;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;


@Entity
@Audited
@CRUD(label="Cidade")
public class Cidade implements Serializable, Cadastravel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @CRUD(label = "Código", obrigatorio=false)
    private Long id;
    @CRUD(label = "Nome")
    private String nome;
    @ManyToOne
    @CRUD(label = "Estado", pesquisavel=false)
    private Estado estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public String getCaminhoPadrao() {
        return "/cadastro/cidade/";
    }

    @Override
    public Validacao executaValidacao() {
        Validacao validacao = Validacao.newValidacao();
        if (this.getNome() != null && this.getNome().length() < 3) {
            validacao.addMensagem("Informação inválida","O campo nome deve conter três ou mais caracteres");
        }
        return validacao;
    }
}
