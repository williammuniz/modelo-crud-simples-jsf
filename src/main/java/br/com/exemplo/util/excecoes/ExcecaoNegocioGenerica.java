/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.exemplo.util.excecoes;

import javax.ejb.ApplicationException;

/**
 * @author Peixe
 */
@ApplicationException(rollback = true)
public class ExcecaoNegocioGenerica extends RuntimeException {

    public ExcecaoNegocioGenerica(String message) {
        super(message);
    }

    public ExcecaoNegocioGenerica(String message, Throwable e) {
        super(message, e);
    }
}
