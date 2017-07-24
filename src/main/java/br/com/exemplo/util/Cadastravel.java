package br.com.exemplo.util;

/**
 *
 * @author William
 */
public interface Cadastravel {

    public String getCaminhoPadrao();
    
    public Long getId();

    public Validacao executaValidacao();
}
