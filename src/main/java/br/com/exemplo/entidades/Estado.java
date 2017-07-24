package br.com.exemplo.entidades;

import br.com.exemplo.util.Cadastravel;
import br.com.exemplo.util.Validacao;
import br.com.exemplo.util.anotacoes.CRUD;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.envers.Audited;

@Entity
@Audited
@CRUD(label="Estado")
public class Estado implements Serializable, Cadastravel {

    private static final long serialVersionUID = 1L;
    @Id
    @CRUD(visualizavel = true, label = "Código", obrigatorio = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CRUD(visualizavel = true, label = "Nome")
    private String nome;
    @CRUD(visualizavel = true, label = "UF")
    private String uf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome + " " + uf;
    }

    @Override
    public String getCaminhoPadrao() {
        return "/cadastro/estado/";
    }

    @Override
    public Validacao executaValidacao() {
        Validacao validacao = Validacao.newValidacao();
        if (this.getUf() != null && this.getUf().length() != 2) {
            validacao.addMensagem("Informação inválida", "O campo UF deve conter dois caracteres");
        }
        return validacao;
    }
}
