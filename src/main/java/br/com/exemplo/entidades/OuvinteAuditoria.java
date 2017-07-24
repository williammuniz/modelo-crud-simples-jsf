package br.com.exemplo.entidades;

import org.hibernate.envers.RevisionListener;

/**
 *
 * @author William
 */
public class OuvinteAuditoria implements RevisionListener {

    @Override
    public void newRevision(Object revisaoAuditoria) {
        RevisaoAuditoria ra = (RevisaoAuditoria) revisaoAuditoria;
        //obter usuario
        //obter ip
    }
}
