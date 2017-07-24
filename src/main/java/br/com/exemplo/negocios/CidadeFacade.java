package br.com.exemplo.negocios;

import br.com.exemplo.entidades.Cidade;
import br.com.exemplo.supers.SuperFacade;
import br.com.exemplo.util.Persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CidadeFacade extends SuperFacade<Cidade> {

    @PersistenceContext(unitName = Persistencia.UNIDADE_PERSISTENCIA)
    private EntityManager em;

    public CidadeFacade() {
        super(Cidade.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional
    @Override
    public List<Cidade> listar() {
        String hql = "from Cidade e order by e.nome asc";
        Query q = getEntityManager().createQuery(hql);

        return q.getResultList();
    }
}
